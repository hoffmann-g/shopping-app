package com.hoffmann_g.coupon_service.dtos;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record CouponResponse(String code, Integer discountPercentage, LocalDateTime expiresAt, Integer uses) {
    
}
