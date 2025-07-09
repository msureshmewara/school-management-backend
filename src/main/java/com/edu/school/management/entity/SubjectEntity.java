package com.edu.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Column(nullable = false, unique = true)
    private String title;

    private Integer totalTheoryMarks;
    private Integer passingTheoryMarks;
    private Integer obtainedTheoryMarks;

    private Boolean hasInternal; // true if internal exists

    private Integer totalInternalMarks;
    private Integer passingInternalMarks;
    private Integer obtainedInternalMarks;

    @ManyToMany
    @JoinTable(
        name = "class_subjects",
        joinColumns = @JoinColumn(name = "subject_id"),
        inverseJoinColumns = @JoinColumn(name = "class_id")
    )
    private List<SchoolClassEntity> classes;
}
