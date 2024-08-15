package com.hoffmann_g.security_service.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.hoffmann_g.security_service.controllers.exceptions.AlgorithmGenerationException;
import com.hoffmann_g.security_service.controllers.exceptions.ResourceNotFoundException;
import com.hoffmann_g.security_service.entities.UserLogin;

@Service
public class TokenService {
    
    private final UserLoginService userLoginService;

    private final String secret = "cacildas";

    private Algorithm algorithm = Algorithm.HMAC256(secret);

    public TokenService(UserLoginService userLoginService){
        this.userLoginService = userLoginService;
    }

    public String generateToken(UserLogin userLogin){
        try {
            return JWT.create()
                              .withIssuer("security-service")
                              .withSubject(userLogin.getEmail())
                              .withExpiresAt(generateExpirationDate())
                              .sign(algorithm);
                              
        } catch (JWTCreationException e){
            throw new AlgorithmGenerationException(e.getMessage());
        }
    }

    public UserDetails validateToken(String token){
        String email = JWT.require(algorithm)
                          .withIssuer("security-service")
                          .build()
                          .verify(token)
                          .getSubject();

        return userLoginService.findByEmail(email).orElseThrow(()
            -> new ResourceNotFoundException("Could not find user"));

        
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
