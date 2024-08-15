package com.hoffmann_g.security_service.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {  

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                           .authorizeHttpRequests(auth -> auth

                                .requestMatchers(HttpMethod.POST, "/api/security/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/security/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/security/validate-token").permitAll()

                                .anyRequest().denyAll()
                                
                           );

        return httpSecurity.build();
    }
}
