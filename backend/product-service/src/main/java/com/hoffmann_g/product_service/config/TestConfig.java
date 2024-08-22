package com.hoffmann_g.product_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.hoffmann_g.product_service.dtos.ProductRequest;
import com.hoffmann_g.product_service.serivces.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class TestConfig implements CommandLineRunner {

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        
        if (productService.getAllProducts().isEmpty()){
        
            productService.createProduct(new ProductRequest("Product A", "Etiam posuere quam ac quam. Maecenas aliquet accumsan leo. Nullam dapibus fermentum ipsum. Etiam quis quam. Integer lacinia.", 1099L));
		    productService.createProduct(new ProductRequest("Product B", "Nam quis nulla. Integer malesuada. In in enim a arcu imperdiet malesuada. Sed vel lectus. Donec odio urna, tempus", 1999L));
		    productService.createProduct(new ProductRequest("Product C", "Praesent in mauris eu tortor porttitor accumsan. Mauris suscipit, ligula sit amet pharetra semper, nibh ante cursus purus,", 2999L));

        }
    }
    
}
