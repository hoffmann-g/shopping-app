package com.hoffmann_g.customer_service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoffmann_g.customer_service.controllers.exceptions.ResourceNotFoundException;
import com.hoffmann_g.customer_service.dtos.UserRequest;
import com.hoffmann_g.customer_service.dtos.UserResponse;
import com.hoffmann_g.customer_service.entities.User;
import com.hoffmann_g.customer_service.mappers.UserMapper;
import com.hoffmann_g.customer_service.repositories.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
        
        return userMapper.mapToUserResponse(user);
    }

    public List<UserResponse> findAll(){
        return userRepository.findAll().stream().map(userMapper::mapToUserResponse).toList();
    }

    public UserResponse findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User could not be found"));
        
        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    public Long save(UserRequest request) {
        return userRepository.save(userMapper.mapToUser(request)).getId();
    }
}