package com.reis.demo.park.api.exception;

public class UsernameUniqueViolationException extends RuntimeException{
    public UsernameUniqueViolationException(String message){
        super(message);
    }
}
