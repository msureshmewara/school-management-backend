package com.edu.school.management.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_pin")
    private Long studentPin;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private String gender;
    @Column(nullable = false) private String rollNumber;
    @Column(nullable = false) private String scholarNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClassEntity schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Column(nullable = false) private String firstName;
    @Column(nullable = false) private String lastName;
    @Column(nullable = false) private String contactNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Column(nullable = false) private LocalDate dOB;
    @Column(nullable = false) private String address;
    @Column(nullable = false) private String caste;
    @Column(nullable = false) private String religion;
    @Column(nullable = false) private String nationality;
    @Column(nullable = false) private String motherToungue;
    private String isDisable, sssmidNum, aadharCardNum, rationCardNum,
                   admissionFormNumber, disabilityType, currentEduBoard, feesDiscount;
    @Column(nullable = false) private String medicalHistory;
    @Column(nullable = false) private String apaarId;
    @Column(nullable = false) private String prevSchool;
    @Column(nullable = false) private String prevEduBoard;
    @Column(nullable = false) private String registrationNumber;
    @Column(nullable = false) private String enrollmentNumber;
    @Column(nullable = false) private String bloodGroup;
    @Column(nullable = false) private String city;
    @Column(nullable = false) private String state;
    @Column(nullable = false) private String pinCode;
    @Column(nullable = false) private String country;
    @Column(nullable = false) private String status;
    @Column(nullable = false) private String createdBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    @PreUpdate protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentFeesEntity> fees;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentFamilyEntity> family;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentDocEntity documents;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private StudentPhotoEntity photos;
}
