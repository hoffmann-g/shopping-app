package com.hoffmann_g.security_service.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter){
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        var http = httpSecurity.csrf(csrf -> csrf.disable())
                           .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                           .authorizeHttpRequests(auth -> auth

                                //.requestMatchers(HttpMethod.POST, "/api/security/register").permitAll()
                                //.requestMatchers(HttpMethod.POST, "/api/security/login").permitAll()
                                
                                //.requestMatchers(HttpMethod.GET, "/api/stock").permitAll()
                                
                                .requestMatchers( "/api/security/greetings").authenticated()
                                .anyRequest().permitAll()
                                
                           )
                           .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                           .build();

        return http;
    }
}
