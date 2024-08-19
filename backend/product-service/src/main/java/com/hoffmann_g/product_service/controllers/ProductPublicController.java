package com.hoffmann_g.product_service.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.product_service.dtos.ProductResponse;
import com.hoffmann_g.product_service.serivces.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/product")
public class ProductPublicController {

    private final ProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProduct(@PathVariable Long id){
        return productService.getProductById(id);
    }
    
}
