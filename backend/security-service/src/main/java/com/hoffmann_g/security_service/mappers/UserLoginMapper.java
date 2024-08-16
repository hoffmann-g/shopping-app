package com.hoffmann_g.security_service.mappers;

import org.springframework.stereotype.Component;

import com.hoffmann_g.security_service.dtos.UserLoginRequest;
import com.hoffmann_g.security_service.dtos.UserRegisterRequest;
import com.hoffmann_g.security_service.dtos.UserRequest;
import com.hoffmann_g.security_service.entities.UserLogin;

@Component
public class UserLoginMapper {
    
    public UserLogin mapToUserLogin(UserLoginRequest request){
        return UserLogin.builder()
                        .email(request.email())
                        .password(request.password())
                        .build();
    }

    public UserLogin mapToUserLogin(UserRegisterRequest request){
        return UserLogin.builder()
                        .email(request.email())
                        .password(request.password())
                        .build();
    }

    public UserRequest mapToUser(UserRegisterRequest request){
        return UserRequest.builder()
                          .email(request.email())
                          .document(request.document())
                          .firstName(request.firstName())
                          .lastName(request.lastName())
                          .phoneNumber(request.phoneNumber())
                          .dob(request.dob())
                          .build();
    }

}
