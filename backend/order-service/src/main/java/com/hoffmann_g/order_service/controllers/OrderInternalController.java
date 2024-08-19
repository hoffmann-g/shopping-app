package com.hoffmann_g.order_service.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.order_service.dtos.OrderResponse;
import com.hoffmann_g.order_service.services.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/internal/order")
public class OrderInternalController {

    private final OrderService orderSerivce;

    @GetMapping("/user/{id}")
    public List<OrderResponse> getOrdersByUser(@PathVariable Long id){
        return orderSerivce.getOrdersByUser(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@PathVariable Long id){
        return orderSerivce.getOrderById(id);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders(){
        return orderSerivce.getAllOrders();
    }
    
}
