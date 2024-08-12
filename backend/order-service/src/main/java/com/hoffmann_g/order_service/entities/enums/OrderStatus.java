package com.hoffmann_g.order_service.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled");

    private String type;

    public static OrderStatus enumValueOf(String type){
        for (OrderStatus o : OrderStatus.values()){
            if (o.getType().equals(type)){
                return o;
            }
        }
        throw new IllegalArgumentException("Invalid order status code");
    }
    
}
