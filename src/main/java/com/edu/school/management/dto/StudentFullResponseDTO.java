package com.edu.school.management.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;

@Data
public class StudentFullResponseDTO {
    private Long studentPin;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String contactNumber;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dOB;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String status;
    private Integer rollNumber;
    private String scholarNumber;
    private Double feesDiscount;
    private Double totalFees;
    private String isDisable, sssmidNum, aadharCardNum, rationCardNum,
    admissionFormNumber, disabilityType, currentEduBoard;
    private String caste;
    private String religion;
    private String nationality;
    private String motherToungue;
    private String medicalHistory;
    private String apaarId;
    private String prevSchool;
    private String prevEduBoard;
    private String registrationNumber;
    private String enrollmentNumber;
    private String bloodGroup;
    private Long classId;
    private Long roleId;

    private String roleTitle;

    private String className;
    private String section;

    private List<StudentFamilyDTO> family;
    private List<StudentFeesDTO> fees;

    private StudentDocDTO documents;
    private StudentPhotoDTO photos;
    private List<StudentDocDTO> documentsList;
    private List<StudentSiblingDTO> siblings;
}
