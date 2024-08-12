package com.hoffmann_g.cart_service.controllers.exceptions;

public class InvalidArgumentException extends RuntimeException{

    public InvalidArgumentException(String msg){
        super(msg);
    }

}
