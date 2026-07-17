package groupuberlightms.passengerservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI passengerServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Passenger Service API")
                .description("Passenger profiles for the Uber Light ride-sharing platform")
                .version("v1"));
    }
}
