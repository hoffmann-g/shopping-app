package com.hoffmann_g.order_service.controllers.exceptions;

public class ItemOutOfStockException extends RuntimeException{

    public ItemOutOfStockException(String msg){
        super(msg);
    }

}
