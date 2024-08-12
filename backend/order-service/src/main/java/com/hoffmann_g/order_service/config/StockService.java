package com.hoffmann_g.order_service.config;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stock-service")
public interface StockService {

    @RequestMapping(method = RequestMethod.GET, value = "api/stock/verify/cart")
    public List<Long> isCartInStock(@RequestParam Map<String, String> items);

    @RequestMapping(method = RequestMethod.PUT, value = "api/stock/reserve")
    public void reserveStock(@RequestParam Map<String, String> stockItems);

    
}
