package Kangcrew.kangaroo_tine.global.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
        name = "JWT Token",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    @Bean
    public OpenAPI kangarootineAPI() {
        Info info = new Info()
                .title("Kangarootine Server API")
                .description("Kangarootine API 명세서")
                .version("1.0.0");

        final String JWT_SCHEME = "JWT Token";

        SecurityScheme securityScheme = new SecurityScheme()
                .name(JWT_SCHEME)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT_SCHEME);

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info)
                .components(new Components().addSecuritySchemes(JWT_SCHEME, securityScheme))
                .security(List.of(securityRequirement));
    }
}
