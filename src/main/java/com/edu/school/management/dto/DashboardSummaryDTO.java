package com.edu.school.management.dto;

import lombok.Data;

@Data
public class DashboardSummaryDTO {
    private CountWithIds totalStudents;
    private BirthdaysInfo studentBirthdaysToday;
    private BirthdaysInfo teacherBirthdaysToday;
    private FeesCollectionInfo totalFeesCollectedToday;
    private PendingFeesInfo totalPendingFees;
    private CountWithDetails todaysAbsentTeachers;
    private CountWithIds totalTeachers;
}
