package com.hoffmann_g.security_service.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRegisterRequest(

    @NotBlank(message = "Email cannot be blank")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password,

    @NotBlank(message = "Document cannot be blank")
    String document,

    @NotBlank(message = "First name cannot be blank")
    String firstName,

    @NotBlank(message = "Last name cannot be blank")
    String lastName,

    @NotBlank(message = "Phone number cannot be blank")
    String phoneNumber,
    
    @NotNull(message = "Birth date cannot be blank")
    Date dob
) {

}
