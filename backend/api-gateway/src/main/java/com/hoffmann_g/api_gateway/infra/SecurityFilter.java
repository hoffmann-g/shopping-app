package com.hoffmann_g.api_gateway.infra;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hoffmann_g.api_gateway.dtos.UserResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SecurityFilter implements WebFilter {

    private final WebClient webClient;

    //@Value("${token-validation-gateway-secret}")
    private String gatewaySecret;
    private Algorithm gatewayAlgorithm = Algorithm.HMAC256(gatewaySecret); 

    public SecurityFilter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token != null) {         
            return webClient
                .get()
                .uri("lb://security-service/api/security/validate-token")
                .header("Authorization", token.replace("Bearer ", ""))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(encryptedResponse -> {
                    UserResponse user = decryptUserLogin(encryptedResponse);
                    var authentication = new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        user.getAuthorities()
                    );
                    return Mono.defer(() -> 
                        chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                    );
                });
        }

        return chain.filter(exchange);
    }

    private UserResponse decryptUserLogin(String token) {
        JWTVerifier verifier = JWT.require(gatewayAlgorithm)
                    .withIssuer("security-service")
                    .build();

        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();
        List<String> authorities = decodedJWT.getClaim("authorities").asList(String.class);

        List<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UserResponse(username, grantedAuthorities);
    }
}
