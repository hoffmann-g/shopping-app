package com.hoffmann_g.coupon_service.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CouponRequest(
    @NotBlank(message = "Coupon code cannot be blank")
    String code,
    
    @Min(value = 1, message = "Discount percentage cannot be negative")
    @Max(value = 100, message = "Discount percentage cannot be greater than 100")
    Integer discountPercentage,

    @NotNull(message = "Expiration time cannot be null")
    LocalDateTime expiresAt,

    @Min(value = 1, message = "Coupon uses cannot be negative")
    @Max(value = 100, message = "Coupon uses cannot be greater than 100")
    Integer uses
    ) {
    
}
