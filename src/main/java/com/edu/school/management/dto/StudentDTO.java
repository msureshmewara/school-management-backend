package com.edu.school.management.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Roll number is required")
    private String rollNumber;

    @NotBlank(message = "Scholar number is required")
    private String scholarNumber;

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Role ID is required")
    private Long roleId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Contact number is required")
    private String contactNumber;

    @NotNull(message = "Date of birth is required")
    private LocalDate dOB;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Caste is required")
    private String caste;

    @NotBlank(message = "Religion is required")
    private String religion;

    @NotBlank(message = "Nationality is required")
    private String nationality;

    @NotBlank(message = "Mother tongue is required")
    private String motherToungue;

    @NotBlank(message = "Medical history is required")
    private String medicalHistory;

    @NotBlank(message = "Apaar ID is required")
    private String apaarId;

    @NotBlank(message = "Previous school is required")
    private String prevSchool;

    @NotBlank(message = "Previous education board is required")
    private String prevEduBoard;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotBlank(message = "Enrollment number is required")
    private String enrollmentNumber;

    @NotBlank(message = "Blood group is required")
    private String bloodGroup;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Pin code is required")
    private String pinCode;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Status is required")
    private String status;

    @NotBlank(message = "Created by is required")
    private String createdBy;
}
