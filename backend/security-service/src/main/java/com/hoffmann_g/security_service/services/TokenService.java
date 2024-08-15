package com.hoffmann_g.security_service.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.hoffmann_g.security_service.controllers.exceptions.AlgorithmGenerationException;
import com.hoffmann_g.security_service.controllers.exceptions.ResourceNotFoundException;
import com.hoffmann_g.security_service.entities.UserLogin;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final UserLoginService userLoginService;

    //@Value("${token.validation.gateway.secret}")
    private String gatewaySecret = "b";
    //@Value("${token.generation.gateway.secret")
    private String secret = "a";

    private final Algorithm tokenAlgorithm = Algorithm.HMAC256(secret);
    private final Algorithm gatewayAlgorithm = Algorithm.HMAC256(gatewaySecret); 

    public String generateToken(UserLogin userLogin){
        try {
            return JWT.create()
                              .withIssuer("security-service")
                              .withSubject(userLogin.getEmail())
                              .withExpiresAt(generateExpirationDate(120L))
                              .sign(tokenAlgorithm);
                              
        } catch (JWTCreationException e){
            throw new AlgorithmGenerationException(e.getMessage());
        }
    }

    public String validateToken(String token){
        String email = JWT.require(tokenAlgorithm)
                          .withIssuer("security-service")
                          .build()
                          .verify(token)
                          .getSubject();

        UserDetails userLogin = userLoginService.findByEmail(email).orElseThrow(()
            -> new ResourceNotFoundException("Could not find user"));

        return encryptUserLogin(userLogin); 
    }

    private String encryptUserLogin(UserDetails userLogin){
        try {
            return JWT.create()
                              .withIssuer("security-service")
                              .withSubject(userLogin.getUsername())
                              .withClaim("authorities", userLogin.getAuthorities().stream().map(x -> x.toString()).toList())
                              .withExpiresAt(generateExpirationDate(120L))
                              .sign(gatewayAlgorithm);
                              
        } catch (JWTCreationException e){
            throw new AlgorithmGenerationException(e.getMessage());
        }
    }

    private Instant generateExpirationDate(Long minutes) {
        return LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.of("-03:00"));
    }

}
