package com.hoffmann_g.stock_service.controller.exceptions;

public class ProductAlreadyExistsException extends RuntimeException{

    public ProductAlreadyExistsException(String msg){
        super(msg);
    }

}
