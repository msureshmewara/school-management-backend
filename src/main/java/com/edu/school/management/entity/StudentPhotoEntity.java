package com.edu.school.management.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "student_photos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stu_photo_id;
    @OneToOne
    @JoinColumn(name = "student_pin", referencedColumnName = "student_pin")
    @JsonBackReference
    private StudentEntity student;

    
    private String studentPhoto;
    private String fatherPhoto;
    private String motherPhoto;
    private String guardianPhoto;

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
