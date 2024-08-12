package com.hoffmann_g.cart_service.mappers;

import org.springframework.stereotype.Component;

import com.hoffmann_g.cart_service.dtos.CartItemRequest;
import com.hoffmann_g.cart_service.dtos.CartItemResponse;
import com.hoffmann_g.cart_service.dtos.CartResponse;
import com.hoffmann_g.cart_service.entities.Cart;
import com.hoffmann_g.cart_service.entities.CartItem;

@Component
public class CartMapper {

    public CartResponse mapToCartResponse(Cart cart) {
        return CartResponse.builder()
                .id(cart.getId())
                .customerId(cart.getCustomerId())
                .productList(cart.getProductList().stream().map(this::mapToCartItemResponse).toList())
                .totalPrice(cart.getTotalPrice())
                .discountedPrice(cart.getDiscountedPrice())
                .coupon(cart.getCoupon())
                .build();
    }

    public CartItemResponse mapToCartItemResponse(CartItem cartItem){
        return CartItemResponse.builder()
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getPrice())
                .build();
    }

    public CartItem mapToCartItem(CartItemRequest request){
        return CartItem.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .build();

    }
}
