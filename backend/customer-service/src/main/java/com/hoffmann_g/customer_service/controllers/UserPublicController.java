package com.hoffmann_g.customer_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.customer_service.config.SecurityService;
import com.hoffmann_g.customer_service.dtos.UserResponse;
import com.hoffmann_g.customer_service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserPublicController {

    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "security", fallbackMethod = "fallbackMethod")
    public UserResponse findUserByEmail(@RequestHeader("Authorization") String encodedToken){
        return userService.findByEmail(securityService.getUserEmail(encodedToken));
    }

    public String fallbackMethod(String encodedToken, Throwable throwable) {
        return "Oops! Something went wrong";
    }
    
}
