package com.edu.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentFeesInfoDTO {
	private Long studentId; 
	private String studentName;
    private String fatherName;
    private Integer rollNumber;
    private String scholarNumber;
    private String className;         // ✅ 5th param
    private Double totalFees;
    private Double totalPaid;
    private Double dueFees;
}
