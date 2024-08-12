package com.hoffmann_g.cart_service.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CartItemRequest(

    @NotNull(message = "Product ID cannot be null")
    Long productId,

    @Min(value = 1, message = "Cannot add less than 1 item to the cart")
    @Max(value = 10, message = "Cannot add more than 10 items to the cart")
    Integer quantity
    
) {
    
}
