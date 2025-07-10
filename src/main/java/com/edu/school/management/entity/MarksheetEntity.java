package com.edu.school.management.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "marksheets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarksheetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer academicYear;
    private Integer grade;
    private Integer rollNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClassEntity schoolClass;

    @OneToMany(mappedBy = "marksheet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarksheetSubjectEntity> subjects;
}

