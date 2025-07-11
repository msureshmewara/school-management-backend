package com.edu.school.management.dto;

import lombok.Data;

@Data
public class PendingStudentFeeDTO {
    private Long id;
    private String name;
    private String className;
    private double pendingAmount;
}
