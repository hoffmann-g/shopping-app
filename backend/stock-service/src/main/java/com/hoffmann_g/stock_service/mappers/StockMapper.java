package com.hoffmann_g.stock_service.mappers;

import org.springframework.stereotype.Component;

import com.hoffmann_g.stock_service.dtos.StockRequest;
import com.hoffmann_g.stock_service.dtos.StockResponse;
import com.hoffmann_g.stock_service.entities.Stock;

@Component
public class StockMapper {
    
    public Stock mapToStock(StockRequest request){
        return new Stock(null, request.productId(), request.quantity(), 0);
    }

    public StockResponse mapToStockResponse(Stock stock){
        return new StockResponse(stock.getId(), stock.getProductId(), stock.getAvaliableStock(), stock.getReservedStock());
    }
}
