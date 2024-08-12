package com.hoffmann_g.product_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductRequest(
    @NotBlank(message = "Name cannot be blank")
    String name,

    @NotBlank(message = "Description cannot be blank")
    String description, 

    @Min(value = (long) 0.01, message = "Price must be greater than 0")
    Long price) {

}
