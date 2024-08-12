package com.hoffmann_g.coupon_service.mappers;

import org.springframework.stereotype.Component;

import com.hoffmann_g.coupon_service.dtos.CouponRequest;
import com.hoffmann_g.coupon_service.dtos.CouponResponse;
import com.hoffmann_g.coupon_service.entities.Coupon;

@Component
public class CouponMapper {

    public Coupon mapToCoupon(CouponRequest request){
        return Coupon.builder()
                        .code(request.code())
                        .discountPercentage(request.discountPercentage())
                        .expiresAt(request.expiresAt())
                        .build();
    }
    
    public CouponResponse mapToCouponResponse(Coupon coupon){
        return CouponResponse.builder()
                                .code(coupon.getCode())
                                .discountPercentage(coupon.getDiscountPercentage())
                                .expiresAt(coupon.getExpiresAt())
                                .build();
    }
}
