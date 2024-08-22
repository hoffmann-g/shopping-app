package com.hoffmann_g.api_gateway.exceptions.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import reactor.core.publisher.Mono;

@Order(-1)
public class GlobalExceptionHandler implements WebExceptionHandler{

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);

        if (ex instanceof InvalidTokenException) {
            return handleInvalidTokenException((InvalidTokenException) ex, exchange);
        }

        return Mono.error(ex);
    }

    private Mono<Void> handleInvalidTokenException(InvalidTokenException ex, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = "Invalid token";

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        byte[] bytes = message.getBytes();
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }

}
