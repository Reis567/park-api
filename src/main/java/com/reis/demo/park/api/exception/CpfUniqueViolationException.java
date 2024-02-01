package com.reis.demo.park.api.exception;

public class CpfUniqueViolationException extends RuntimeException{ 
    public CpfUniqueViolationException( String message){
        super(message);
    }
}
