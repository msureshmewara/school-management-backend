package com.edu.school.management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TeacherAttendanceRequest {
    private Long teacherId;
    private LocalDate date;
    private Boolean isPresent;
}
