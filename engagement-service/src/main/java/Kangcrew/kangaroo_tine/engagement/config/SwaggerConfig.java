package Kangcrew.kangaroo_tine.engagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI engagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Engagement Service API")
                        .description("KangarooTine Engagement Service API Documentation")
                        .version("1.0.0"));
    }
}