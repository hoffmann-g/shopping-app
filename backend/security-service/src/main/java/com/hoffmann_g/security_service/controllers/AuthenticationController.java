package com.hoffmann_g.security_service.controllers;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.security_service.dtos.UserLoginRequest;
import com.hoffmann_g.security_service.dtos.UserRegisterRequest;
import com.hoffmann_g.security_service.services.AuthenticationService;
import com.hoffmann_g.security_service.services.TokenService;

import jakarta.validation.Valid;

@RequestMapping("/api/security")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    private final TokenService tokenService;

    public AuthenticationController(@Lazy AuthenticationService authenticationService,
                                    TokenService tokenService){
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }
    
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@Valid @RequestBody UserLoginRequest request){
        return authenticationService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(@Valid @RequestBody UserRegisterRequest request){
        return authenticationService.register(request);
    }

    @GetMapping("/validate-token")
    public String validateToken(@RequestHeader("Authorization") String token){
        return tokenService.validateToken(token);
    }

}
