package com.hoffmann_g.security_service.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.hoffmann_g.security_service.controllers.exceptions.AlgorithmGenerationException;
import com.hoffmann_g.security_service.controllers.exceptions.InvalidTokenException;
import com.hoffmann_g.security_service.entities.UserLogin;

@Service
public class TokenService {
    
    private final UserLoginService userLoginService;
    private final Algorithm tokenAlgorithm;

    public TokenService(UserLoginService userLoginService,
                        @Value("${token.generation.secret") String secret) { 
        this.userLoginService = userLoginService;
        this.tokenAlgorithm = Algorithm.HMAC256(secret);
    
    }

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

    @Transactional(readOnly = true)
    public Long validateTokenId(String token){
        try {
            String email = JWT.require(tokenAlgorithm)
                            .withIssuer("security-service")
                            .build()
                            .verify(token.replace("Bearer ", ""))
                            .getSubject();

            UserLogin userLogin = userLoginService.findByEmail(email).get();
            return userLogin.getCustomerId();
        } catch (Exception e){
            throw new InvalidTokenException("Could not decode login token: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public String validateTokenEmail(String token){
        try {
            String email = JWT.require(tokenAlgorithm)
                          .withIssuer("security-service")
                          .build()
                          .verify(token.replace("Bearer ", ""))
                          .getSubject();

            UserLogin userLogin = userLoginService.findByEmail(email).get();
            return userLogin.getEmail();
        } catch (Exception e){
            throw new InvalidTokenException("Could not decode login token: " + e.getMessage());
        }   
    }

    private Instant generateExpirationDate(Long minutes) {
        return LocalDateTime.now().plusMinutes(120).toInstant(ZoneOffset.of("-03:00"));
    }

}
