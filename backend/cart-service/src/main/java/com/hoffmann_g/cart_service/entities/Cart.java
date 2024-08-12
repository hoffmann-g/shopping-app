package com.hoffmann_g.cart_service.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cart {

    @Id
    private String id;
    private Long customerId;
    private Long totalPrice;
    private Long discountedPrice;
    private String coupon;
    
    private List<CartItem> productList = new ArrayList<>();
    
    public Cart(Long customerId){
        this.customerId = customerId;
    }
}
