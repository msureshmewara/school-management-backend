package com.edu.school.management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentRequestDTO {

    private String username;
    private String password;
    private String gender;
    private Integer rollNumber;
    private String scholarNumber;
    private String className;

    private String firstName;
    private String lastName;
    private String contactNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;

    private String address;
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
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String status;
    private Double totalFees;
    private Double feesDiscount;

    private String isDisable, sssmidNum, aadharCardNum, rationCardNum;
    private String admissionFormNumber, disabilityType, currentEduBoard;

    private String createdBy;

    // Nested
    private SchoolDTO school;
    private RoleDTO role;
    private SchoolClassDTOStu schoolClass;

    // Child objects
    private List<StudentFeesDTO> fees;
    private List<StudentFamilyDTO> family;
    private List<StudentDocDTO> documents;
    private StudentPhotoDTO photos;

    // Helper getters to extract IDs
    public Long getSchoolId() {
        return school != null ? school.getSchoolId() : null;
    }

    public Long getRoleId() {
        return role != null ? role.getRoleId() : null;
    }

    public Long getClassId() {
        return schoolClass != null ? schoolClass.getClassId() : null;
    }
}
