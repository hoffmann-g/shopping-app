package com.hoffmann_g.customer_service.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.customer_service.dtos.UserRequest;
import com.hoffmann_g.customer_service.dtos.UserResponse;
import com.hoffmann_g.customer_service.services.UserService;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Boolean existsByEmail(@RequestBody @NotBlank String email) {
        return userService.existsByEmail(email);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse saveUser(@RequestBody UserRequest request) {
        return userService.save(request);
    }
    
}
