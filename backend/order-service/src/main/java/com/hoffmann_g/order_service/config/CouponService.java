package com.hoffmann_g.order_service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "coupon-service")
public interface CouponService {

    @RequestMapping(method = RequestMethod.GET, value = "/api/internal/coupon/{couponCode}/discount")
    Integer getCouponDiscount(@PathVariable("couponCode") String couponCode);

}
