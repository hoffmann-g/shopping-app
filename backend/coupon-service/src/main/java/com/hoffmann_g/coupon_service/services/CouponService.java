package com.hoffmann_g.coupon_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoffmann_g.coupon_service.controllers.exceptions.InvalidArgumentException;
import com.hoffmann_g.coupon_service.controllers.exceptions.ResourceNotFoundException;
import com.hoffmann_g.coupon_service.dtos.CouponRequest;
import com.hoffmann_g.coupon_service.dtos.CouponResponse;
import com.hoffmann_g.coupon_service.entities.Coupon;
import com.hoffmann_g.coupon_service.mappers.CouponMapper;
import com.hoffmann_g.coupon_service.repositories.CouponRepository;

@Service
public class CouponService {
    
    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;
    
    @Transactional(readOnly = true)
    public Integer getCouponDiscount(String code){
        Coupon coupon = couponRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Coupon " + code + " was not found"));
        
        if (LocalDateTime.now().isAfter(coupon.getExpiresAt())){
            throw new InvalidArgumentException("Copoun is expired");
        }

        return coupon.getDiscountPercentage();
    }

    @Transactional
    public CouponResponse createCoupon(CouponRequest request){
        Coupon coupon = couponRepository.save(couponMapper.mapToCoupon(request));

        return couponMapper.mapToCouponResponse(coupon);

    }

    public List<CouponResponse> getAllCoupons() {
        return couponRepository.findAll().stream().map(couponMapper::mapToCouponResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteCoupon(String code) {
        if (!couponRepository.existsById(code)) throw new ResourceNotFoundException("Coupon " + code + " was not found");

        couponRepository.deleteById(code);
    }

    @Transactional
    public CouponResponse useCoupon(String code) {
        Coupon coupon = couponRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Coupon " + code + " was not found"));

        coupon.setUses(coupon.getUses() - 1);

        return couponMapper.mapToCouponResponse(couponRepository.save(coupon));
    }

    public CouponResponse getCoupon(String code) {
        Coupon coupon = couponRepository.findById(code).orElseThrow(() -> new ResourceNotFoundException("Coupon " + code + " was not found"));

        return couponMapper.mapToCouponResponse(coupon);
    }

}
