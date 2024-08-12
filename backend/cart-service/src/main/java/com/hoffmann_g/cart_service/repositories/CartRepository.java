package com.hoffmann_g.cart_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hoffmann_g.cart_service.entities.Cart;
import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
    
    Optional<Cart> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);

    Boolean existsByCustomerId(Long customerId);
}
