package com.hoffmann_g.stock_service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hoffmann_g.stock_service.dtos.StockRequest;
import com.hoffmann_g.stock_service.dtos.StockResponse;
import com.hoffmann_g.stock_service.services.StockService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/internal/stock")
public class StockInternalController {

    @Autowired
    private StockService stockService;

    @GetMapping("/verify/cart")
    @ResponseStatus(HttpStatus.OK)
    public List<Long> isCartInStock(@NotEmpty @RequestParam Map<String, String> items){
        return stockService.isCartInStock(convertItemsMap(items));
    }

    @GetMapping("/verify/product")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isProductInStock(@RequestParam Long productId, @RequestParam Integer quantity){
        return stockService.isProductInStock(productId, quantity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> getAllStock(){
        return stockService.getAllStock();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponse createStock(@Valid @RequestBody StockRequest request){
        return stockService.createStock(request);
    }

    @PutMapping("/reserve")
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> reserveStock(@RequestParam Map<String, String> stockItems){
        return stockService.reserveStock(convertItemsMap(stockItems));
    }

    @PutMapping("/unreserve")
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> unreserveStock(@RequestParam Map<String, String> stockItems){
        return stockService.unreserveStock(convertItemsMap(stockItems));
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> decreaseReservedStock(@RequestParam Map<String, String> stockItems){
        return stockService.decreaseReservedStock(convertItemsMap(stockItems));
    }

    @PutMapping("/increase")
    @ResponseStatus(HttpStatus.OK)
    public Optional<StockResponse> updateStock(@RequestParam Long productId, @RequestParam Integer quantity){
        return stockService.updateStock(productId, quantity);
    }

    private Map<Long, Integer> convertItemsMap(Map<String, String> items) {
        Map<Long, Integer> convertedItems = new HashMap<>();

        for(Entry<String, String> entry : items.entrySet()){
            try {
                Long productId = Long.parseLong(entry.getKey());
                Integer quantity = Integer.parseInt(entry.getValue());

                convertedItems.put(productId, quantity);
            } catch (Exception e){
                throw new IllegalArgumentException("Could not parse " + entry);
            }
        }

        return convertedItems;
    }

}
