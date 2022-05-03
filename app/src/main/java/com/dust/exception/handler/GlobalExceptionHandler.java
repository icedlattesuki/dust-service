package com.dust.exception.handler;

import com.dust.exception.InternalException;
import com.dust.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorMessage", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<Map<String, Object>> handleInternalException(InternalException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("errorMessage", e.getMessage());
        return ResponseEntity.internalServerError().body(map);
    }
}
