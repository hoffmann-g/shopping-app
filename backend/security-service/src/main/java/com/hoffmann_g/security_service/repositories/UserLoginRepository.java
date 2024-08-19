package com.hoffmann_g.security_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hoffmann_g.security_service.entities.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long>{

    Optional<UserLogin> findByEmail(String email);
    
    Boolean existsByEmail(String email);
    
}
