package com.hoffmann_g.stock_service;

import org.springframework.boot.SpringApplication;

public class TestStockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(StockServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
