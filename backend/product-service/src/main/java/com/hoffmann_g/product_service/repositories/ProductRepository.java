package com.hoffmann_g.product_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hoffmann_g.product_service.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
