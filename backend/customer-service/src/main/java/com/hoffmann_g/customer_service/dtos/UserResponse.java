package com.hoffmann_g.customer_service.dtos;

import java.util.Date;

import lombok.Builder;

@Builder
public record UserResponse(

    Long id,
    String document,
    String email,
    String phoneNumber,
    String firstName,
    String lastName,
    Date dob
) {

}
