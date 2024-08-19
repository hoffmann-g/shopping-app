package com.hoffmann_g.coupon_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.coupon_service.dtos.CouponRequest;
import com.hoffmann_g.coupon_service.dtos.CouponResponse;
import com.hoffmann_g.coupon_service.services.CouponService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/internal/coupon")
public class CouponInternalController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/{code}/discount")
    @ResponseStatus(HttpStatus.OK)
    public Integer getCouponDiscount(@PathVariable String code) {
        return couponService.getCouponDiscount(code);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CouponResponse createCoupon(@Valid @RequestBody CouponRequest request) {
        return couponService.createCoupon(request);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CouponResponse> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCoupon(@PathVariable String code) {
        couponService.deleteCoupon(code);
    }

    @PutMapping("/{code}/use")
    public CouponResponse useCoupon(@PathVariable String code) {
        return couponService.useCoupon(code);
    }

    @GetMapping("/{code}")
    public CouponResponse getCoupon(@PathVariable String code) {
        return couponService.getCoupon(code);
    }

}
