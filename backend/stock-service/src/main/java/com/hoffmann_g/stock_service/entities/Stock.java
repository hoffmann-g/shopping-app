package com.hoffmann_g.stock_service.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "stock")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Stock {
    
    @Id
    private String id;
    private Long productId;
    private Integer avaliableStock;
    private Integer reservedStock;
}
