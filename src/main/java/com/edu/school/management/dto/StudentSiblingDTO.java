package com.edu.school.management.dto;

import lombok.Data;

@Data
public class StudentSiblingDTO {
    private Long id;
    private String siblingName;
    private String siblingClass;
    private String siblingSchool;
    private String contactNumber;
    private String relationship;
}
