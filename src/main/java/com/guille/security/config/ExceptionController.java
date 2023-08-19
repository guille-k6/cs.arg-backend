package com.guille.security.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionController {
    @ExceptionHandler(AuthorizationServiceException.class) // exception handled
    public ResponseEntity<?> handleNullPointerExceptions(Exception e)
    {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
