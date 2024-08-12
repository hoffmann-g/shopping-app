package com.hoffmann_g.stock_service.controller.exceptions;

public class InsufficientStockException extends RuntimeException{

    public InsufficientStockException(String msg){
        super(msg);
    }

}
