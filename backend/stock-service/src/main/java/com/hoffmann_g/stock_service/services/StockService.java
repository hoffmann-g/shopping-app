package com.hoffmann_g.stock_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoffmann_g.stock_service.controller.exceptions.ProductAlreadyExistsException;
import com.hoffmann_g.stock_service.controller.exceptions.ResourceNotFoundException;
import com.hoffmann_g.stock_service.controller.exceptions.InvalidArgumentException;
import com.hoffmann_g.stock_service.controller.exceptions.InsufficientStockException;
import com.hoffmann_g.stock_service.dtos.StockRequest;
import com.hoffmann_g.stock_service.dtos.StockResponse;
import com.hoffmann_g.stock_service.entities.Stock;
import com.hoffmann_g.stock_service.mappers.StockMapper;
import com.hoffmann_g.stock_service.repositories.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Transactional(readOnly = true)
    public List<Long> isCartInStock(Map<Long, Integer> items){
        List<Long> productsInStock = new ArrayList<>();

        List<Stock> stockList = stockRepository.findByProductIdIn(List.copyOf(items.keySet()));
        
        for(Stock s : stockList){
            if (s.getAvaliableStock() >= items.get(s.getProductId())) productsInStock.add(s.getProductId());
        }

        return productsInStock;
    }

    @Transactional(readOnly = true)
    public Boolean isProductInStock(Long productId, Integer quantity){
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() -> new ResourceNotFoundException("Could not find product in stock"));

        Integer avaliableQuantity = stock.getAvaliableStock();

        return avaliableQuantity != null & avaliableQuantity >= quantity;
    }

    @Transactional
    public StockResponse createStock(StockRequest request) {
        if (stockRepository.findByProductId(request.productId()).isPresent()) throw new ProductAlreadyExistsException("Product ID already registered");

        Stock stock = stockRepository.save(stockMapper.mapToStock(request));
        
        return stockMapper.mapToStockResponse(stock);
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getAllStock() {
        List<Stock> stockList = stockRepository.findAll();

        return stockList.stream().map(x -> stockMapper.mapToStockResponse(x)).toList();
    }

    @Transactional
    public List<StockResponse> reserveStock(Map<Long, Integer> stockItems) {
        List<Stock> stockList = new ArrayList<>();

        for (Entry<Long, Integer> stockItem : stockItems.entrySet()){
            Long productId = stockItem.getKey();
            Integer quantity = stockItem.getValue();

            if (quantity < 0) throw new InvalidArgumentException("Cannot reduce negative quantities");

            Stock stock = stockRepository.findByProductId(productId).orElseThrow(() -> 
                new ResourceNotFoundException("Could not find product in stock"));

            if (stock.getAvaliableStock() < quantity){
                throw new InsufficientStockException("Insufficient stock to reduce the requested quantity");
            }

            stock.setReservedStock(stock.getReservedStock() + quantity);
            stock.setAvaliableStock(stock.getAvaliableStock() - quantity);

            stockList.add(stockRepository.save(stock));
        }

        return stockList.stream().map(stockMapper::mapToStockResponse).toList();
    }

    @Transactional
    public List<StockResponse> unreserveStock(Map<Long, Integer> stockItems) {
        List<Stock> stockList = new ArrayList<>();

        for (Entry<Long, Integer> stockItem : stockItems.entrySet()){
            Long productId = stockItem.getKey();
            Integer quantity = stockItem.getValue();

            if (quantity < 0) throw new InvalidArgumentException("Cannot reduce negative quantities");

            Stock stock = stockRepository.findByProductId(productId).orElseThrow(() -> 
                new ResourceNotFoundException("Could not find product in stock"));
    
            if (stock.getAvaliableStock() < quantity){
                throw new InsufficientStockException("Insufficient stock to reduce the requested quantity");
            }
    
            stock.setReservedStock(stock.getReservedStock() - quantity);
            stock.setAvaliableStock(stock.getAvaliableStock() + quantity);
    
            stockList.add(stockRepository.save(stock));
        }
        
        return stockList.stream().map(stockMapper::mapToStockResponse).toList();
    }

    @Transactional
    public List<StockResponse> decreaseReservedStock(Map<Long, Integer> stockItems) {
        List<Stock> stockList = new ArrayList<>();

        for (Entry<Long, Integer> stockItem : stockItems.entrySet()){
            Long productId = stockItem.getKey();
            Integer quantity = stockItem.getValue();

            if (quantity < 0) throw new InvalidArgumentException("Cannot reduce negative quantities");

            Stock stock = stockRepository.findByProductId(productId).orElseThrow(() -> 
                new ResourceNotFoundException("Could not find product in stock"));
    
            if (stock.getReservedStock() < quantity){
                throw new InsufficientStockException("Insufficient stock to reduce the requested quantity");
            }
    
            if ((stock.getReservedStock() - quantity == 0) && stock.getAvaliableStock() == 0){
                stockRepository.deleteByProductId(productId);
                
            } else {
                stock.setReservedStock(stock.getReservedStock() - quantity);
                stockList.add(stockRepository.save(stock));
            }
        }

        return stockList.stream().map(stockMapper::mapToStockResponse).toList();
    }

    @Transactional
    public Optional<StockResponse> updateStock(Long productId, Integer quantity){
        if (quantity < 0) throw new InvalidArgumentException("Cannot incrase negative quantities");

        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() -> 
            new ResourceNotFoundException("Could not find product in stock"));

        if (stock.getReservedStock() == 0 && stock.getAvaliableStock() == 0){
            stockRepository.deleteByProductId(productId);
            
            return Optional.empty();

        } else {
            stock.setAvaliableStock(quantity);

            return Optional.of(stockMapper.mapToStockResponse(stockRepository.save(stock)));
        }
    }

}
