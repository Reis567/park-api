package com.reis.demo.park.api.web.exception;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorMessage {

    private String path;

    private String method;

    private int status;

    private String statusText;

    private String message;
 
    public ErrorMessage(HttpServletRequest request , HttpStatus status, String message){
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }
}
