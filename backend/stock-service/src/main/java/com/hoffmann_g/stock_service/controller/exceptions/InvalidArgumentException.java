package com.hoffmann_g.stock_service.controller.exceptions;

public class InvalidArgumentException extends RuntimeException{

    public InvalidArgumentException(String msg){
        super(msg);
    }

}
