package com.hoffmann_g.cart_service.controllers;

import org.springframework.http.HttpStatus;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.cart_service.dtos.CartItemRequest;
import com.hoffmann_g.cart_service.dtos.CartResponse;
import com.hoffmann_g.cart_service.services.CartService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{customer-id}/quantities")
    @ResponseStatus(HttpStatus.OK)
    public Map<Long, Integer> getCartQuantities(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.getCartQuantities(customerId);
    }

    @GetMapping("/{customer-id}")
    public CartResponse getCart(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.getCart(customerId);
    }

    @GetMapping("/{customer-id}/coupon")
    public String getCartCoupon(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.getCartCoupon(customerId);
    }

    @DeleteMapping("/{customer-id}")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse clearCart(@PathVariable(value = "customer-id") Long customerId) {
        return cartService.clearCart(customerId);
    }

    @PostMapping("/{customer-id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addItemToCart(@PathVariable(value = "customer-id") Long customerId, @Valid @RequestBody CartItemRequest request) {
        return cartService.addItemToCart(customerId, request);
    }

    @PutMapping("/{customer-id}/items")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse updateCartItem(@PathVariable(value = "customer-id") Long customerId, @Valid @RequestBody CartItemRequest request) {
        return cartService.updateCartItem(customerId, request);

    }

    @PostMapping("/{customer-id}/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse applyCoupon(@PathVariable(value = "customer-id") Long customerId, @RequestParam String code) {
        return cartService.applyCoupon(customerId, code);
    }

    @DeleteMapping("/delete/{customer-id}")
    public void deleteCart(@PathVariable(value = "customer-id") Long customerId) {
        cartService.deleteCart(customerId);
    }

}
