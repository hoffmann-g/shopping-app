package com.hoffmann_g.coupon_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hoffmann_g.coupon_service.entities.Coupon;

public interface CouponRepository extends MongoRepository<Coupon, String>{
    
}
