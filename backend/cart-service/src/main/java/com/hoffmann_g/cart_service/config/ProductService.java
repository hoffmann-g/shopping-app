package com.hoffmann_g.cart_service.config;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductService {

    @RequestMapping(method = RequestMethod.GET, value = "/api/internal/product/prices")
    public Map<Long, Long> getPrices(@RequestParam List<Long> productIds);
    
}
