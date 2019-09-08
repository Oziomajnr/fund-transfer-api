import io.dropwizard.Configuration
import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.db.DataSourceFactory
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration

import javax.validation.Valid
import javax.validation.constraints.*

class FundsTransferConfiguration : Configuration() {
    @Valid
    @NotNull
    @JsonProperty("database")
    val dataSourceFactory = DataSourceFactory()

    @JsonProperty("config")
    val appConfig: AppConfiguration? = null

    @JsonProperty("swagger")
    val swaggerBundleConfiguration: SwaggerBundleConfiguration? = null

}
