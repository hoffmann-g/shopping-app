package com.hoffmann_g.customer_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoffmann_g.customer_service.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
    
}
