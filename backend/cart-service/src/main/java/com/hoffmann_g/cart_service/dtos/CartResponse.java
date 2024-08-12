package com.hoffmann_g.cart_service.dtos;

import java.util.List;

import lombok.Builder;

@Builder
public record CartResponse(
        String id,
        Long customerId,
        List<CartItemResponse> productList,
        Long totalPrice,
        Long discountedPrice,
        String coupon) {

}
