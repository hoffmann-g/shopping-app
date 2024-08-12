package com.hoffmann_g.stock_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hoffmann_g.stock_service.entities.Stock;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends MongoRepository<Stock, String>{

     Optional<Stock> findByProductId(Long productId);
     List<Stock> findByProductIdIn(List<Long> productsIds);

     void deleteByProductId(Long productId);
}
