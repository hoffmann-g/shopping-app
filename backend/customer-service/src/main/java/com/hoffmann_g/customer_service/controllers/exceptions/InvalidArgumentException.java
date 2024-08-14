package com.hoffmann_g.customer_service.controllers.exceptions;

public class InvalidArgumentException extends RuntimeException{

    public InvalidArgumentException(String msg){
        super(msg);
    }

}
