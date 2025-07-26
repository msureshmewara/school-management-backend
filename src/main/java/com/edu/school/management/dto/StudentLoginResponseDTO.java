package com.edu.school.management.dto;

import lombok.Data;

@Data
public class StudentLoginResponseDTO {
    private Long studentPin;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private Integer rollNumber;
    private String scholarNumber;
    private String contactNumber;
    private String status;
    private String className;
    private String dob; // formatted dd/MM/yyyy
    private String address;
    private String caste;
    private String religion;
    private String nationality;
    private String motherToungue;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private Double totalFees;
    private Double feesDiscount;
    private String createdBy;
    private String roleTitle;
}
