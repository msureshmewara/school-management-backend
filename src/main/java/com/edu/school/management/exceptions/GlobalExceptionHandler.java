package com.edu.school.management.exceptions;

import org.hibernate.TransientPropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
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
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        String msg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

        if (msg != null && msg.toLowerCase().contains("null value")) {
            String[] parts = msg.split("column '");
            if (parts.length > 1) {
                String field = parts[1].split("'")[0];
                fieldErrors.put(field, field + " is required (missing)");
            } else {
                fieldErrors.put("unknown_field", "A required field is missing");
            }
        } else if (msg != null && msg.toLowerCase().contains("duplicate entry")) {
            String duplicateValue = extractBetween(msg, "Duplicate entry '", "'");
            fieldErrors.put("unique_constraint", "Duplicate entry for " + duplicateValue );
        } else {
            fieldErrors.put("error", "Data integrity issue: " + msg);
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
    public ResponseEntity<Map<String, Object>> handleTransientValue(TransientPropertyValueException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", "Entity reference error",
                        "error", ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "message", "Unexpected error occurred",
                        "error", ex.getMessage()
                ));
    }

    private String extractBetween(String input, String start, String end) {
        int startIndex = input.indexOf(start);
        int endIndex = input.indexOf(end, startIndex + start.length());
        if (startIndex != -1 && endIndex != -1) {
            return input.substring(startIndex + start.length(), endIndex);
        }
        return "unknown";
    }

  
}
