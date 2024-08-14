package com.hoffmann_g.security_service.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(

    @NotBlank(message = "Email cannot be blank")
    String email,

    @NotBlank(message = "Password cannot be blank")
    String password

) {

}
