package com.edu.school.management.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_fees") // ✅ DIFFERENT TABLE NAME
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentFeesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feesId;

    private Double totalFees;
    private LocalDate paymentDate;
    private String paymentMode;
    private String paymentRefNum;
    private String receivedBy;
    private String status;

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

    @ManyToOne
    @JoinColumn(name = "student_pin", nullable = false) // ✅ MUST MATCH PK NAME
    private StudentEntity student;
}
