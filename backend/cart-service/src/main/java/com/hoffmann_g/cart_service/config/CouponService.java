package com.hoffmann_g.cart_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "coupon-service")
public interface CouponService {

    @RequestMapping(method = RequestMethod.GET, value = "api/coupon/{code}/discount")
    public Integer getCouponDiscount(@PathVariable String code);
    
}
