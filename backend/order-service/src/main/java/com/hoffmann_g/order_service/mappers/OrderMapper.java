package com.hoffmann_g.order_service.mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.hoffmann_g.order_service.dtos.OrderItemResponse;
import com.hoffmann_g.order_service.dtos.OrderResponse;
import com.hoffmann_g.order_service.entities.Order;
import com.hoffmann_g.order_service.entities.OrderItem;

@Component
public class OrderMapper {

    public OrderResponse mapToOrderResponse(Order order){
        return OrderResponse.builder()
                            .id(order.getId())
                            .customerId(order.getCustomerId())
                            .orderStatus(order.getOrderStatus().toString())
                            .totalAmount(order.getTotalAmount())
                            .paymentType(order.getPaymentType())
                            .createdAt(order.getCreatedAt())
                            .orderItemResponseList(order.getOrderItemList().stream().map(this::mapToOrderItemResponse).toList())
                            .build();

    }

    public OrderItemResponse mapToOrderItemResponse(OrderItem orderLineItem){
        return OrderItemResponse.builder()
                                    .id(orderLineItem.getId())
                                    .productId(orderLineItem.getProductId())
                                    .price(orderLineItem.getPrice())
                                    .quantity(orderLineItem.getQuantity())
                                    .build();
    }

    public Map<String, String> mapToStringMap(Map<Long, Integer> items){
        Map<String, String> convertedItems = new HashMap<>();

        for(Entry<Long, Integer> entry : items.entrySet()){
            try {
                String productId = String.valueOf(entry.getKey());
                String quantity = String.valueOf(entry.getValue());

                convertedItems.put(productId, quantity);
            } catch (Exception e){
                throw new IllegalArgumentException("Could not parse " + entry);
            }
        }

        return convertedItems;
    }
    
}
