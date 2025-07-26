package com.edu.school.management.dto;

import lombok.Data;

@Data
public class PendingStudentFeeDTO {
    private Long id;
    private String name;
    private String className;
    private double pendingAmount;
    private String fatherName;
    private String fatherPhone;
    private String guardianName;
    private String guardianPhone;
}
