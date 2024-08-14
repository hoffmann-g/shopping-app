package com.hoffmann_g.customer_service.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(

    @NotBlank(message = "Email cannot be blank")
    String email,

    @NotBlank(message = "Document cannot be blank")
    String document,

    @NotBlank(message = "First name cannot be blank")
    String firstName,

    @NotBlank(message = "Last name cannot be blank")
    String lastName,

    @NotBlank(message = "Phone number cannot be blank")
    String phoneNumber,
    
    @NotBlank(message = "Birth date cannot be blank")
    Date dob
) {

}
