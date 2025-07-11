package com.edu.school.management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsDTO {
    private long totalStudents;
    private long studentBirthdaysToday;
    private long teacherBirthdaysToday;
    private double totalFeesCollectedToday;
    private double totalPendingFees;
    private long todaysAbsentTeachers;
    private long totalTeachers;
}
