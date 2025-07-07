package com.edu.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableRequestDTO {
    private Long classId;
    private Long subjectId;
    private Long teacherId;
    private String dayOfWeek;
    private Integer period;
    private String timeSlot;
}

