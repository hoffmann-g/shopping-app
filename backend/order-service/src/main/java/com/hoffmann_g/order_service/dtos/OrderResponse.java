package com.hoffmann_g.order_service.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record OrderResponse(
        Long id,
        Long customerId,
        String orderStatus,
        Long totalAmount,
        String paymentType,
        LocalDateTime createdAt,
        List<OrderItemResponse> orderItemResponseList) {

}
