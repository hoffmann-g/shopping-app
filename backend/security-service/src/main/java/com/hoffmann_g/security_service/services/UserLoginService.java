package com.hoffmann_g.security_service.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hoffmann_g.security_service.entities.UserLogin;
import com.hoffmann_g.security_service.repositories.UserLoginRepository;

import jakarta.validation.constraints.NotBlank;

@Service
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;

    public UserLoginService(UserLoginRepository userLoginRepository){
        this.userLoginRepository = userLoginRepository;
    }

    public Optional<UserLogin> findByEmail(String email){
        return userLoginRepository.findByEmail(email);
    }

    public boolean existsByEmail(@NotBlank(message = "Email cannot be blank") String email) {
        return userLoginRepository.existsByEmail(email);
    }
    
    public UserLogin save(UserLogin userLogin){
        return userLoginRepository.save(userLogin);
    }
}
