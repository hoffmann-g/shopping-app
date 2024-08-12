package com.hoffmann_g.product_service.mappers;

import org.springframework.stereotype.Component;

import com.hoffmann_g.product_service.dtos.ProductRequest;
import com.hoffmann_g.product_service.dtos.ProductResponse;
import com.hoffmann_g.product_service.entities.Product;

@Component
public class ProductMapper {

    public ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                                 .id(product.getId())
                                 .name(product.getName())
                                 .description(product.getDescription())
                                 .price(product.getPrice())
                                 .build();
    }

    public Product mapToProduct(ProductRequest request){
        return Product.builder()
                    .name(request.name())
                    .description(request.description())
                    .price(request.price())
                    .build();
    }
    
}
