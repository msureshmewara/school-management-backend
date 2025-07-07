package com.edu.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimetableResponseDTO {
    private String className;
    private String dayOfWeek;
    private int period;
    private String timeSlot;
    private String subjectName;
    private String teacherName;
    private boolean teacherAbsent;
}



