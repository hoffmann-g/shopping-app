package com.hoffmann_g.security_service.mappers;

import org.springframework.stereotype.Component;

import com.hoffmann_g.security_service.dtos.UserLoginRequest;
import com.hoffmann_g.security_service.entities.UserLogin;

@Component
public class UserLoginMapper {
    
    public UserLogin mapToUserLogin(UserLoginRequest request){
        return UserLogin.builder()
                        .email(request.email())
                        .password(request.password())
                        .build();
    }

}
