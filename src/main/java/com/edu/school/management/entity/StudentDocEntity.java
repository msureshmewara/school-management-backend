package com.edu.school.management.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "student_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDocEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stu_doc_id;

    @OneToOne
    @JoinColumn(name = "student_pin", referencedColumnName = "student_pin")
    @JsonBackReference
    private StudentEntity student;

    
    private String aadharCard;
    private String panCard;
    private String sssmid;
    private String casteCertificate;
    private String incomeCertificate;
    private String domicileCertificate;
    private String transferCertificate;
    private String migrationCertificate;
    private String characterCertificate;
    private String previousMarksheet;
    private String disabilityCertificate;
    private String rationCard;
    private String admissionForm;
    private String passbook;

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
