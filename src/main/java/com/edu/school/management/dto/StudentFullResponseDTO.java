package com.edu.school.management.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StudentFullResponseDTO {
    private Long studentPin;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String contactNumber;
    private LocalDate dOB;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String status;

    private String roleTitle;

    private String className;
    private String section;

    private List<StudentFamilyDTO> family;
    private List<StudentFeesDTO> fees;

    private StudentDocDTO documents;
    private StudentPhotoDTO photos;
}
