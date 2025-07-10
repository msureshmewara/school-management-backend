package com.edu.school.management.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceDTO {
    private Long id;
    private LocalDate date;
    private Boolean isPresent;
}
