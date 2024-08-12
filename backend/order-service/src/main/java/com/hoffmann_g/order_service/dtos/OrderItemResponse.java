package com.hoffmann_g.order_service.dtos;

import lombok.Builder;

@Builder
public record OrderItemResponse(
    Long id,
    Long productId,
    Long price,
    Integer quantity) {

}
