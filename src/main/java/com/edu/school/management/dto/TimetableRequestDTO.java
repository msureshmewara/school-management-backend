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
	private Long teacherId;
    private Long subjectId;
    private Long classId;
    private String dayOfWeek; // e.g. "MONDAY"
    private Integer period;   // e.g. 1st, 2nd...
    private String timeSlot;  // e.g. "09:00 - 09:45"
}

