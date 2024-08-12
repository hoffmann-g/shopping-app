package com.hoffmann_g.cart_service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartItem {
    
    Long productId;
    Integer quantity;
    Long price;
}
