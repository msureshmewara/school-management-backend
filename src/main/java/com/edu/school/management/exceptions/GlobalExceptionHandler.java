package com.edu.school.management.exceptions;

import jakarta.validation.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String,String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
          .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "message", "Validation failed",
                    "errors", fieldErrors
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String,String> fieldErrors = new HashMap<>();
        String msg = ex.getRootCause() != null ? ex.getRootCause().getMessage().toLowerCase() : "";
        if (msg.contains("duplicate") && msg.contains("username")) {
            fieldErrors.put("username","Username must be unique");
        } else if (msg.contains("null")) {
            if (msg.contains("role")) fieldErrors.put("role","Role cannot be null");
            // more null checks as needed...
            else fieldErrors.put("unknown_field","Missing required field");
        } else {
            fieldErrors.put("error","Data integrity violation: "+msg);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "message", "Data integrity violation",
                    "errors", fieldErrors
                ));
    }

    @ExceptionHandler(TransientPropertyValueException.class)
    public ResponseEntity<Map<String,Object>> handleTransientValue(TransientPropertyValueException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "message", "Entity reference error",
                    "error", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleGenericException(Exception ex) {
        // Optional: detect nested TransientPropertyValueException
        if (ExceptionUtils.hasCause(ex, TransientPropertyValueException.class)) {
            Throwable cause = ExceptionUtils.getRootCause(ex);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", "Entity reference error",
                        "error", cause.getMessage()
                    ));
        }
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An unexpected error occurred",
                    "error", ex.getMessage()
                ));
    }
}
