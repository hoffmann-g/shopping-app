package com.hoffmann_g.product_service.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.product_service.dtos.ProductRequest;
import com.hoffmann_g.product_service.dtos.ProductResponse;
import com.hoffmann_g.product_service.serivces.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/internal/product")
public class ProductInternalController {

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request){
        return productService.createProduct(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request){
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
    }

    @GetMapping("/prices")
    public Map<Long, Long> getPrices(@NotEmpty(message = "Product id list cannot be empty")
                                            @RequestParam List<Long> productIds) {
        return productService.getPrices(productIds);
    }
    

}
