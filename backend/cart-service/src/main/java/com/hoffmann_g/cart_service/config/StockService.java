package com.hoffmann_g.cart_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service")
public interface StockService {
    
    @RequestMapping(method = RequestMethod.GET, value = "/api/stock/verify/product")
    public Boolean isProductInStock(@RequestParam("productId") Long productId, @RequestParam("quantity") Integer quantity);
    
}
