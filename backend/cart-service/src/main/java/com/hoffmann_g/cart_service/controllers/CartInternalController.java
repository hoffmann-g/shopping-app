package com.hoffmann_g.cart_service.controllers;

import org.springframework.http.HttpStatus;

import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.cart_service.dtos.CartResponse;
import com.hoffmann_g.cart_service.services.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/internal/cart")
public class CartInternalController {

    private final CartService cartService;

    @GetMapping("/{customer-id}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.getCart(customerId);
    }

    @GetMapping("/{customer-id}/coupon")
    @ResponseStatus(HttpStatus.OK)
    public String getCartCoupon(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.getCartCoupon(customerId);
    }

    @GetMapping("/{customer-id}/quantities")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Integer> getCartQuantities(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.getCartQuantities(customerId);
    }

    @DeleteMapping("/{customer-id}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse clearCart(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.clearCart(customerId);
    }

}
