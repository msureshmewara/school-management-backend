package com.edu.school.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.school.management.dto.DashboardStatsDTO;
import com.edu.school.management.dto.DashboardSummaryDTO;
import com.edu.school.management.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<DashboardSummaryDTO> getDashboardStats(@RequestParam Long schoolId) {
        return ResponseEntity.ok(dashboardService.getDashboardSummary(schoolId));
    }
}

