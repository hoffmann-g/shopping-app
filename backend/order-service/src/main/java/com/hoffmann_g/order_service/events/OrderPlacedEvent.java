package com.hoffmann_g.order_service.events;

public record OrderPlacedEvent (
    Long orderId,
    String customerEmail,
    String paymentType,
    Long price
) {
    
}
