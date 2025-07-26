package com.edu.school.management.dto;

import lombok.Data;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private String className;
    private String dob;
    private String contactNumber;
    private String fatherName;
    private String fatherPhone;
    private String guardianName;
    private String guardianPhone;
}
