package com.example.exercise.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RuntimeExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(RuntimeException e, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

}