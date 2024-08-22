package com.hoffmann_g.api_gateway.infra;

import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.hoffmann_g.api_gateway.exceptions.exceptions.InvalidTokenException;

import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements WebFilter {

    private final WebClient webClient;

    public SecurityFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token != null) {
            return webClient
                    .get()
                    .uri("lb://security-service/api/internal/security/validate-token/email")
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(userEmail -> {

                        var authentication = new UsernamePasswordAuthenticationToken(userEmail, null,
                                Collections.emptyList());

                        return Mono.defer(() -> chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication)));
                    }).onErrorMap(WebClientResponseException.class, ex -> {
                        throw new InvalidTokenException("Invalid login token! Please, login again");
                    });
        }

        return chain.filter(exchange);
    }

}
