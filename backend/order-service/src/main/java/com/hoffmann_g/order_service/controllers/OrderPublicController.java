package com.hoffmann_g.order_service.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.order_service.config.SecurityService;
import com.hoffmann_g.order_service.dtos.OrderRequest;
import com.hoffmann_g.order_service.dtos.OrderResponse;
import com.hoffmann_g.order_service.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/order")
public class OrderPublicController {
    
    private final OrderService orderSerivce;
    private final SecurityService securityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestHeader("Authorization") String encodedToken, @Valid @RequestBody OrderRequest request){
        return orderSerivce.placeOrder(securityService.getUserId(encodedToken),
                                       securityService.getUserEmail(encodedToken),
                                       request);
    }

    @GetMapping
    public List<OrderResponse> getMyOrders(@RequestHeader("Authorization") String encodedToken){
        return orderSerivce.getOrdersByUser(securityService.getUserId(encodedToken));
    }

}
