package com.hoffmann_g.order_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoffmann_g.order_service.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
