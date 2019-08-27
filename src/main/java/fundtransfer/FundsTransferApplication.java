package fundtransfer;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import fundtransfer.resource.HomeResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.DBIHealthCheck;
import io.dropwizard.jersey.errors.LoggingExceptionMapper;
import io.dropwizard.server.DefaultServerFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

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
    }

    @Override
    public void run(final FundsTransferConfiguration configuration,
                    final Environment environment) {

        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        ((DefaultServerFactory) configuration.getServerFactory()).setRegisterDefaultExceptionMappers(false);
        environment.jersey().register(new LoggingExceptionMapper<Throwable>() {
        });

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        Injector injector = createInjector(configuration, jdbi);

        //register api resource
        environment.jersey().register(injector.getInstance(HomeResource.class));

        environment.healthChecks().register("database", new DBIHealthCheck(jdbi, "SELECT 1"));

    }

    private Injector createInjector(FundsTransferConfiguration config, DBI jdbi) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(AppConfiguration.class).toInstance(config.getAppConfig());

                // service binding

                //dao binding
            }
        });
    }

}
