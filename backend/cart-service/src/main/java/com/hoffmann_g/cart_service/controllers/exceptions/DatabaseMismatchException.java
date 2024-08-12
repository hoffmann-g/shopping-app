package com.hoffmann_g.cart_service.controllers.exceptions;

public class DatabaseMismatchException extends RuntimeException{

    public DatabaseMismatchException(String msg){
        super(msg);
    }

}
