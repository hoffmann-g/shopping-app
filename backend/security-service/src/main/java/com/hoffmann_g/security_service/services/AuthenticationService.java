package com.hoffmann_g.security_service.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoffmann_g.security_service.config.CustomerService;
import com.hoffmann_g.security_service.controllers.exceptions.InvalidArgumentException;
import com.hoffmann_g.security_service.dtos.TokenResponse;
import com.hoffmann_g.security_service.dtos.UserLoginRequest;
import com.hoffmann_g.security_service.entities.UserLogin;
import com.hoffmann_g.security_service.mappers.UserLoginMapper;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserLoginService userLoginService;
    private final CustomerService customerService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserLoginMapper userLoginMapper;

    public AuthenticationService(UserLoginService userLoginService,
                                 CustomerService customerService,
                                 TokenService tokenService,
                                 @Lazy AuthenticationManager authenticationManager,
                                 UserLoginMapper userLoginMapper){
        this.userLoginService = userLoginService;
        this.customerService = customerService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userLoginMapper = userLoginMapper;
    }

    public TokenResponse login(UserLoginRequest request) {
        //if (!customerService.existsByEmail(request.email()))
        //    throw new ResourceNotFoundException("Provided user login could not be found");

        /* 
        UserLogin userLogin = userLoginService.findByEmail(request.email()).orElseThrow(()
            -> new DatabaseMismatchException("Could not find existent user's login"));

        if (!userLogin.equals(userLoginMapper.mapToUserLogin(request)))
            throw new InvalidArgumentException("Incorrect login credentials");
        */

        var userPasswordToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication auth = this.authenticationManager.authenticate(userPasswordToken);

        return new TokenResponse(tokenService.generateToken((UserLogin) auth.getPrincipal()));
    }

    public UserLogin register(UserLoginRequest request) {
        //if (userLoginService.existsByEmail(request.email()))
        //    throw new InvalidArgumentException("User already registered");

        if (userLoginService.existsByEmail(request.email()))
            throw new InvalidArgumentException("Email already registered");

        UserLogin userLogin = userLoginMapper.mapToUserLogin(request);

        userLogin.setPassword(new BCryptPasswordEncoder().encode(request.password()));

        return userLoginService.save(userLogin);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userLoginService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(""));
    }
    
}
