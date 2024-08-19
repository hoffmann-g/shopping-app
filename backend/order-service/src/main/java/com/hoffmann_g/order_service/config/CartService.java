package com.hoffmann_g.order_service.config;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "cart-service")
public interface CartService {

    @RequestMapping(method = RequestMethod.GET, value = "api/internal/cart/{customer-id}/quantities")
    public Map<Long, Integer> getCartQuantities(@PathVariable(value = "customer-id") Long customerId);

    @RequestMapping(method = RequestMethod.GET, value = "api/internal/cart/{customer-id}/coupon")
    public String getCartCoupon(@PathVariable(value = "customer-id") Long customerId);

    @RequestMapping(method = RequestMethod.DELETE, value = "api/internal/cart/{customer-id}")
    public void clearCart(@PathVariable(value = "customer-id") Long customerId);




    
    
}
