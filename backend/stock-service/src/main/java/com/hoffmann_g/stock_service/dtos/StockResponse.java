package com.hoffmann_g.stock_service.dtos;

public record StockResponse(
    String id,
    Long productId,
    Integer avaliableStock,
    Integer reservedStock
) {
    
}
