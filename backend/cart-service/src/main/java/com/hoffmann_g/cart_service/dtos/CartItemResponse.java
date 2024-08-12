package com.hoffmann_g.cart_service.dtos;

import lombok.Builder;

@Builder
public record CartItemResponse(

    Long productId,
    Integer quantity,
    Long price
) {

}
