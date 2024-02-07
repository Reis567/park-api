package com.reis.demo.park.api.exception;

public class CodigoUniqueViolationException extends RuntimeException{
    public CodigoUniqueViolationException(String message){
        super(message);
    }
}
