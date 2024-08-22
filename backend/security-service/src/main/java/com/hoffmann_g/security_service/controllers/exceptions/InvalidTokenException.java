package com.hoffmann_g.security_service.controllers.exceptions;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String msg){
        super(msg);
    }

}
