package com.edu.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableRequestDTO {
    private Long classId;
    private String dayOfWeek;
    private Long schoolId;
    private List<PeriodEntry> periods;

    @Data
    public static class PeriodEntry {
        private Integer period;
        private String timeSlot;
        private Long subjectId;
        private Long teacherId;
    }
}


