package com.hoffmann_g.security_service.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.security_service.services.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/internal/security")
@RestController
public class AuthenticationInternalController {

    private final TokenService tokenService;

    @GetMapping("/validate-token/id")
    public Long validateTokenId(@RequestHeader("Authorization") String token) {
        return tokenService.validateTokenId(token);
    }

    @GetMapping("/validate-token/email")
    public String validateTokenEmail(@RequestHeader("Authorization") String token) {
        return tokenService.validateTokenEmail(token);
    }

}
