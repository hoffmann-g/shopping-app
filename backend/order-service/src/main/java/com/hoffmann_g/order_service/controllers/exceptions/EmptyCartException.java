package com.hoffmann_g.order_service.controllers.exceptions;

public class EmptyCartException extends RuntimeException{

    public EmptyCartException(String msg){
        super(msg);
    }

}
