package com.hoffmann_g.stock_service.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockRequest(
    @NotNull(message = "Product ID cannot be blank")
    Long productId,
    
    @Min(value = 0, message = "Stock cannot contain negative quantities")
    Integer quantity) {
    
}
