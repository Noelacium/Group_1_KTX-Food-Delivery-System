package com.ktxfood.handler;

import com.ktxfood.exception.OutOfStockException;
import com.ktxfood.exception.InvalidQuantityException;
import com.ktxfood.exception.EmptyCartException;
import com.ktxfood.exception.InsufficientBalanceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            OutOfStockException.class,
            InvalidQuantityException.class,
            EmptyCartException.class,
            InsufficientBalanceException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, String>> handleBusinessException(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}