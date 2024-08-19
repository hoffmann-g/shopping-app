package com.hoffmann_g.cart_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.cart_service.config.SecurityService;
import com.hoffmann_g.cart_service.dtos.CartItemRequest;
import com.hoffmann_g.cart_service.dtos.CartResponse;
import com.hoffmann_g.cart_service.services.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/cart")
public class CartPublicController {

    private final CartService cartService;
    private final SecurityService securityService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCart(@RequestHeader("Authorization") String encodedToken) {
        return cartService.getCart(securityService.getUserId(encodedToken));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse clearCart(@RequestHeader("Authorization") String encodedToken) {
        return cartService.clearCart(securityService.getUserId(encodedToken));
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addItemToCart(@RequestHeader("Authorization") String encodedToken, @Valid @RequestBody CartItemRequest request) {
        return cartService.addItemToCart(securityService.getUserId(encodedToken), request);
    }

    @PutMapping("/items")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse updateCartItem(@RequestHeader("Authorization") String encodedToken, @Valid @RequestBody CartItemRequest request) {
        return cartService.updateCartItem(securityService.getUserId(encodedToken), request);
    }

    @PostMapping("/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse applyCoupon(@RequestHeader("Authorization") String encodedToken, @RequestParam String code) {
        return cartService.applyCoupon(securityService.getUserId(encodedToken), code);
    }
    
}
