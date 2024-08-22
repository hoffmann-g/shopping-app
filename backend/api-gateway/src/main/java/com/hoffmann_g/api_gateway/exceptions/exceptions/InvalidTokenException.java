package com.hoffmann_g.api_gateway.exceptions.exceptions;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String msg){
        super(msg);
    }

}
