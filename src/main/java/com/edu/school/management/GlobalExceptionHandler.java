package com.edu.school.management;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Validation annotations like @NotBlank
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", "Validation failed");
        body.put("errors", fieldErrors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Unique constraint / null DB constraint violations
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        String rootCause = ex.getRootCause() != null ? ex.getRootCause().getMessage() : "";

        if ((rootCause != null && !rootCause.isEmpty())
                || (rootCause.contains("username") && rootCause.toLowerCase().contains("duplicate"))) {
            fieldErrors.put("username", "Username must be unique");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("password") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("password", "Password cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("role") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("role", "Role cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("gender") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("gender", "Gender cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("firstName") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("firstName", "FirstName cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("lastName") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("lastName", "LastName cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("contactNumber") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("contactNumber", "Contact Number cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("dOB") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("dOB", "Date of birth cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("address") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("address", "Address cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("city") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("city", "City cannot be null");
        } else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("state") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("state", "State cannot be null");
        }  else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("pin") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("pin", "Pin Code cannot be null");
        }  else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("country") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("country", "Country cannot be null");
        }  else if (rootCause != null && !rootCause.isEmpty()
                && rootCause.contains("status") && rootCause.toLowerCase().contains("null")) {
            fieldErrors.put("status", "Status cannot be null");
        }  else {
            fieldErrors.put("error", "Data integrity violation: " + rootCause);
        }
      
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", "Data integrity violation");
        body.put("errors", fieldErrors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
