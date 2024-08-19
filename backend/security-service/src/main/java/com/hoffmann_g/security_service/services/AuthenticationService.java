package com.hoffmann_g.security_service.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoffmann_g.security_service.config.CustomerService;
import com.hoffmann_g.security_service.controllers.exceptions.InvalidArgumentException;
import com.hoffmann_g.security_service.dtos.UserLoginRequest;
import com.hoffmann_g.security_service.dtos.UserRegisterRequest;
import com.hoffmann_g.security_service.dtos.UserRequest;
import com.hoffmann_g.security_service.entities.UserLogin;
import com.hoffmann_g.security_service.mappers.UserLoginMapper;

import jakarta.transaction.Transactional;

@Service
public class AuthenticationService implements UserDetailsService {

    private final UserLoginService userLoginService;
    
    private final CustomerService customerService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserLoginMapper userLoginMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserLoginService userLoginService,
                                 CustomerService customerService,
                                 TokenService tokenService,
                                 @Lazy AuthenticationManager authenticationManager,
                                 UserLoginMapper userLoginMapper,
                                 PasswordEncoder passwordEncoder){
        this.userLoginService = userLoginService;
        this.customerService = customerService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userLoginMapper = userLoginMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String login(UserLoginRequest request) {
        if (!userLoginService.existsByEmail(request.email()))
            throw new InvalidArgumentException("User not registered");    
        
        var userPasswordToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication auth = this.authenticationManager.authenticate(userPasswordToken);
        return tokenService.generateToken((UserLogin) auth.getPrincipal());
    }

    @Transactional
    public String register(UserRegisterRequest request) {
        if (userLoginService.existsByEmail(request.email()))
            throw new InvalidArgumentException("Email already registered");

        UserRequest user = userLoginMapper.mapToUser(request);
        Long customerId = customerService.saveUser(user);

        UserLogin userLogin = userLoginMapper.mapToUserLogin(request);
        userLogin.setPassword(passwordEncoder.encode(request.password()));
        userLogin.setCustomerId(customerId);

        userLoginService.save(userLogin);

        return "User successfulyl registed";
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userLoginService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(""));
    }
    
}
