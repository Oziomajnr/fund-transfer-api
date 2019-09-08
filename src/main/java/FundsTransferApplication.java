import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dao.AccountDao;
import dao.TransferProcessorDao;
import exception.mappers.InssuficientBalanceExceptionMapper;
import exception.mappers.AccountNotFoundExceptionMapper;
import exception.mappers.ValidationExceptionMapper;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.DBIHealthCheck;
import io.dropwizard.jersey.errors.EarlyEofExceptionMapper;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.skife.jdbi.v2.DBI;
import resource.FundTransferResource;
import service.AccountService;
import service.implementation.BankAccountService;
import service.implementation.DirectFundTransferService;
import service.FundTransferService;


public class FundsTransferApplication extends Application<FundsTransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new FundsTransferApplication().run(args);
    }

    @Override
    public String getName() {
        return "FundsTransfer";
    }

    @Override
    public void initialize(final Bootstrap<FundsTransferConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<FundsTransferConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(FundsTransferConfiguration configuration) {
                return configuration.getSwaggerBundleConfiguration();
            }
        });

        bootstrap.addBundle(new MigrationsBundle<FundsTransferConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(FundsTransferConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final FundsTransferConfiguration configuration,
                    final Environment environment) {
        ((DefaultServerFactory) configuration.getServerFactory()).setRegisterDefaultExceptionMappers(false);
        environment.jersey().register(new LoggingExceptionMapper<Throwable>() {
        });

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        Injector injector = createInjector(configuration, jdbi);

        registerExceptionMappers(environment);

        //register api resource
        environment.jersey().register(injector.getInstance(FundTransferResource.class));

        environment.healthChecks().register("database", new DBIHealthCheck(jdbi, "SELECT 1"));

    }

    private Injector createInjector(FundsTransferConfiguration config, DBI jdbi) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                // service binding
                bind(FundTransferService.class).to(DirectFundTransferService.class);
                bind(AccountService.class).to(BankAccountService.class);

                //dao binding
                bind(TransferProcessorDao.class).toInstance(jdbi.onDemand(TransferProcessorDao.class));
                bind(AccountDao.class).toInstance(jdbi.onDemand(AccountDao.class));
            }
        });
    }

    private void registerExceptionMappers(final Environment environment) {
        environment.jersey().register(new ValidationExceptionMapper() {
        });
        environment.jersey().register(new EarlyEofExceptionMapper());
        environment.jersey().register(new AccountNotFoundExceptionMapper());
        environment.jersey().register(new InssuficientBalanceExceptionMapper());
    }

}
