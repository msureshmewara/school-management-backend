package com.edu.school.management.controller;

import com.edu.school.management.dto.ApiResponse;
import com.edu.school.management.dto.MarksheetRequest;
import com.edu.school.management.service.MarksheetService;
import com.edu.school.management.util.MarksheetResult;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marksheets")
@RequiredArgsConstructor
public class MarksheetController {

    private final MarksheetService marksheetService;

    @PostMapping("/createMarksheet")
    public ResponseEntity<ApiResponse<Void>> createMarksheet(@RequestBody MarksheetRequest request) {
        try {
            marksheetService.createMarksheet(request);
            return ResponseEntity.ok(new ApiResponse<>("success", "Marksheet created successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>("error", "Failed to create marksheet: " + e.getMessage(), null));
        }
    }

    
    @GetMapping("/evaluate/{id}")
    public ResponseEntity<?> evaluateMarksheet(@PathVariable Long id) {
        try {
            MarksheetResult result = marksheetService.evaluateMarksheet(id);

            if (result.getStudent() == null) {
                return ResponseEntity.badRequest().body(
                    new ApiResponse<>( "error", "Student not found for marksheet ID: " + id, null)
                );
            }

            return ResponseEntity.ok(
                new ApiResponse<>("success", "Marksheet evaluated successfully", result)
            );

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>( "error", "Evaluation failed: " + e.getMessage(), null)
            );
        }
    }

}
