package com.edu.school.management.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentFeesDTO {
    private Long feesId;
    private Double totalFees;
    private LocalDate paymentDate;
    private String paymentMode;
    private Double paidAmount;
    private String status;
    private String createdBy;
}
