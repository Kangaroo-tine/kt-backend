package com.example.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityWebFilterChainConfig {

    @Bean
    @Order(-1)
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         JwtAuthenticationFilter jwtFilter) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        http.authorizeExchange(ex -> ex
                .pathMatchers(
                        "/actuator/**",
                        "/swagger-ui.html", "/swagger-ui/**",
                        "/api-docs/**", "/v3/api-docs/**",
                        // 게이트웨이 앞단 경로 설계에 맞춰 공개 엔드포인트 열기
                        "/member/auth/**", "/api/member/auth/**"
                ).permitAll()
                .anyExchange().authenticated()
        );
        http.addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}