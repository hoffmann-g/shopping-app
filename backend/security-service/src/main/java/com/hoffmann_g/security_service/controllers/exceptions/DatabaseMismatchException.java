package com.hoffmann_g.security_service.controllers.exceptions;

public class DatabaseMismatchException extends RuntimeException{

    public DatabaseMismatchException(String msg){
        super(msg);
    }

}
