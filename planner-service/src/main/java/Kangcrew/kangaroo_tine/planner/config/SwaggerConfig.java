package Kangcrew.kangaroo_tine.planner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI plannerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Planner Service API")
                        .description("KangarooTine Planner Service API Documentation")
                        .version("1.0.0"));
    }
}