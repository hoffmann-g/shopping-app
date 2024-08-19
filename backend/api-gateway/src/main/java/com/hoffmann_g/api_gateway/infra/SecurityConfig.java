package com.hoffmann_g.api_gateway.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange

                                // authentication endpoints
                                .pathMatchers(HttpMethod.POST, "/api/security/login/**").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/security/register/**").permitAll()

                                // cart endpoints
                                .pathMatchers(HttpMethod.GET, "/api/cart/**").authenticated()
                                .pathMatchers(HttpMethod.DELETE, "/api/cart/**").authenticated()
                                .pathMatchers(HttpMethod.POST, "/api/cart/items/**").authenticated()
                                .pathMatchers(HttpMethod.PUT, "/api/cart/items/**").authenticated()
                                .pathMatchers(HttpMethod.POST, "/api/cart/coupons/**").authenticated()

                                // customer endpoints
                                .pathMatchers(HttpMethod.GET, "/api/user/**").authenticated()

                                // order endpoints
                                .pathMatchers(HttpMethod.POST, "/api/order/**").authenticated()
                                .pathMatchers(HttpMethod.GET, "/api/order/**").authenticated()

                                // product endpoints
                                .pathMatchers(HttpMethod.GET, "/api/product/**").permitAll()

                                // eureka
                                .pathMatchers("/eureka/**").permitAll()
                                
                                .anyExchange().denyAll())

                .addFilterBefore(securityFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        
        return serverHttpSecurity.build();
    }
}
