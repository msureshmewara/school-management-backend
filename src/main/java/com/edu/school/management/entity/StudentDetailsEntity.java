package com.edu.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "student_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One-to-One relationship with UserEntity
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // Family Info
    private String fatherName;
    private String motherName;
    private String fatherPhone;
    private String motherPhone;
    private String fatherAadhar;
    private String motherAadhar;
    private String guardianName;
    private String guardianAadhar;
    private String guardianNumber;
    private String fatherOccupation;
    private String motherOccupation;
    private String guardianOccupation;
    private String fatherEmail;
    private String motherEmail;
    private String guardianEmail;
    private String fatherEducation;
    private String motherEducation;
    private String guardianEducation;
    private String isSibling;
    
    
    // Contact Info
    private String contactNumber;

    // Documents
    private String aadharCard;
    private String panCard;
    private String sssmid;
    private String casteCertificate;
    private String incomeCertificate;
    private String domicile;
    private String transferCertificate;
    private String migrationCertificate;
    private String characterCertificate;
    private String previousMarksheet;
    private String disabilityCertificate;
    private String rationCard;
    private String admissionForm;
    private String passbook;
    
    
    // Personal
    private LocalDate dob;
    private String bloodGroup;
    private String apaarId;
    private String caste;
    private String religion;
    private String nationality;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String motherToungue;
    private String previousSchool;
    private String isAnyDisability;
    private String medicalHistory;
    private String educationBoard;
    private String registartionNumber;
    private String scholarNumber;
    private String rollNumber;
    private String enrollmentNumber;
    
    

    // Photos
    private String studentPhoto;     // You can store URL/path or base64
    private String guardianPhoto;
    private String motherPhoto;
    private String fatherPhoto;
}

