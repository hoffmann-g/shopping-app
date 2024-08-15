package com.hoffmann_g.api_gateway.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

                                .pathMatchers("/api/security/login").permitAll()
                                .pathMatchers("/api/security/register").permitAll()

                                .pathMatchers("/api/product/**").permitAll()
                                
                                .anyExchange().authenticated())

                .addFilterBefore(securityFilter, SecurityWebFiltersOrder.AUTHENTICATION);
        
        return serverHttpSecurity.build();
    }
}
