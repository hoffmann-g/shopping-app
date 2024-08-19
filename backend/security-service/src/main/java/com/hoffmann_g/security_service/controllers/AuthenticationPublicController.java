package com.hoffmann_g.security_service.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.security_service.dtos.UserLoginRequest;
import com.hoffmann_g.security_service.dtos.UserRegisterRequest;
import com.hoffmann_g.security_service.services.AuthenticationService;

import jakarta.validation.Valid;

@RequestMapping("/api/security")
@RestController
public class AuthenticationPublicController {

    private final AuthenticationService authenticationService;

    public AuthenticationPublicController(@Lazy AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@Valid @RequestBody UserLoginRequest request) {
        return authenticationService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(@Valid @RequestBody UserRegisterRequest request) {
        return authenticationService.register(request);
    }

}
