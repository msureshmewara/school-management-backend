package com.edu.school.management.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "students") // ✅ keep as is
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

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Role is required")
    @Column(nullable = false)
    private String role;

    @NotBlank(message = "Gender is required")
    @Column(nullable = false)
    private String gender;
    
    @NotBlank(message = "Roll Number is required")
    @Column(nullable = false)
    private String rollNumber;
    
    @NotBlank(message = "Scholar Number is required")
    @Column(nullable = false)
    private String scholarNumber;
    
    @NotBlank(message = "Class Number is required")
    @Column(nullable = false)
    private String stu_class;
    
    @NotBlank(message = "Section Number is required")
    @Column(nullable = false)
    private String section;
    
    @NotBlank(message = "First name is required")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Contact number is required")
    @Column(nullable = false)
    private String contactNumber;

    @NotNull(message = "Date of Birth is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dOB;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "State is required")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "PIN Code is required")
    @Column(nullable = false)
    private String pin;

    @NotBlank(message = "Country is required")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "Status is required")
    @Column(nullable = false)
    private String status;
    
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

    // ✅ Optional Bidirectional Mapping
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentFeesEntity> fees;
}
