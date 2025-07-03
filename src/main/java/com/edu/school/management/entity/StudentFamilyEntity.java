package com.edu.school.management.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "student_family")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFamilyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long family_id;

    @ManyToOne
    @JoinColumn(name = "student_pin", nullable = false)
    @JsonBackReference
    private StudentEntity student;

    // Family Info
    private String fatherName;
    private String motherName;
    private String fatherPhone;
    private String motherPhone;
    private String fatherAadharNum;
    private String motherAadharNum;
    private String guardianName;
    private String guardianAadharNum;
    private String guardianPhone;
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
    private String siblingDetails;
    @NotBlank(message = "Created By is required")
    @Column(nullable = false)
    private String createdBy;
    

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

