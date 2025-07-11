package com.edu.school.management.dto;

import lombok.Data;

@Data
public class FeeTransactionDTO {
    private Long transactionId;
    private Long studentId;
    private String studentName;
    private String className;
    private double amountPaid;
    private String paymentDate; // You can use LocalDate if preferred
    private String paymentMode;
}
