package com.hoffmann_g.stock_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.hoffmann_g.stock_service.dtos.StockRequest;
import com.hoffmann_g.stock_service.services.StockService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private StockService stockService;

    @Override
    public void run(String... args) throws Exception {
        
        if (stockService.getAllStock().isEmpty()){
        
            stockService.createStock(new StockRequest(1L, 10));
		    stockService.createStock(new StockRequest(2L, 15));
            stockService.createStock(new StockRequest(3L, 20));
        }
    }
    
}
