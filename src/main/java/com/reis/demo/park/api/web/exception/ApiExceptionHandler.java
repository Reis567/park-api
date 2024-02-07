package com.reis.demo.park.api.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.reis.demo.park.api.exception.CodigoUniqueViolationException;
import com.reis.demo.park.api.exception.CpfUniqueViolationException;
import com.reis.demo.park.api.exception.EntityNotFoundException;
import com.reis.demo.park.api.exception.PasswordConfException;
import com.reis.demo.park.api.exception.UsernameUniqueViolationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception,
            HttpServletRequest request, BindingResult result) {

        log.error("Api error - ", exception);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campos inválidos !", result));
    }

    @ExceptionHandler({ UsernameUniqueViolationException.class, CpfUniqueViolationException.class,CodigoUniqueViolationException.class })
    public ResponseEntity<ErrorMessage> uniqueViolationException(RuntimeException exception,
            HttpServletRequest request) {

        log.error("Api error - ", exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.CONFLICT, exception.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entitNotFoundException(RuntimeException exception, HttpServletRequest request) {

        log.error("Api error - ", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException exception,
            HttpServletRequest request) {

        log.error("Api error - ", exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, exception.getMessage()));
    }

    @ExceptionHandler(PasswordConfException.class)
    public ResponseEntity<ErrorMessage> senhaNaoConfereException(PasswordConfException exception,
            HttpServletRequest request) {
        log.error("Api error - ", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

}
