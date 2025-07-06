package com.edu.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_siblings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSiblingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the main student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_pin", nullable = false)
    private StudentEntity student;

    @Column(nullable = false)
    private String siblingName;

    @Column(nullable = false)
    private String siblingClass;

    @Column(nullable = false)
    private String siblingSchool;

    @Column
    private String contactNumber;

    @Column
    private String relationship; // e.g., Brother, Sister
}
