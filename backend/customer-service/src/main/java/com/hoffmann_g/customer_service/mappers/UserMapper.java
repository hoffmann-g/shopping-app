package com.hoffmann_g.customer_service.mappers;

import org.springframework.stereotype.Component;

import com.hoffmann_g.customer_service.dtos.UserRequest;
import com.hoffmann_g.customer_service.dtos.UserResponse;
import com.hoffmann_g.customer_service.entities.User;

@Component
public class UserMapper {

    public UserResponse mapToUserResponse(User user){
        return UserResponse.builder()
                        .id(user.getId())
                        .document(user.getDocument())
                        .email(user.getEmail())
                        .phoneNumber(user.getPhoneNumber())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .dob(user.getDob())
                        .build();
    }

    public User mapToUser(UserRequest request){
        return User.builder()
                .email(request.email())
                .document(request.document())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phoneNumber(request.phoneNumber())
                .dob(request.dob())
                .build();
    }
}
