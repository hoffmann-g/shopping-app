package com.hoffmann_g.product_service.serivces;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoffmann_g.product_service.controllers.exceptions.ResourceNotFoundException;
import com.hoffmann_g.product_service.dtos.ProductRequest;
import com.hoffmann_g.product_service.dtos.ProductResponse;
import com.hoffmann_g.product_service.entities.Product;
import com.hoffmann_g.product_service.mappers.ProductMapper;
import com.hoffmann_g.product_service.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request){
        Product product = productMapper.mapToProduct(request);

        return productMapper.mapToProductResponse(productRepository.save(product));
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not get product."));

        return productMapper.mapToProductResponse(product);
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> productList = productRepository.findAll();

        return productList.stream().map(x -> productMapper.mapToProductResponse(x)).toList();
    }

    public void deleteProductById(Long id){
        if (!productRepository.existsById(id)) throw new ResourceNotFoundException("Could not delete product.");

        productRepository.deleteById(id);
    }

    public ProductResponse updateProduct(Long id, ProductRequest request){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        if (!product.getName().equals(request.name())) product.setName(request.name());
        if (!product.getDescription().equals(request.description())) product.setDescription(request.description());
        if (!product.getPrice().equals(request.price())) product.setPrice(request.price());

        return productMapper.mapToProductResponse(productRepository.save(product));
    } 

    public Map<Long, Long> getPrices(List<Long> productIds){

        List<Product> products = productRepository.findAllById(productIds);

        return products.stream().collect(Collectors.toMap(Product::getId, Product::getPrice));
    }

}
