package com.edu.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(nullable = false)
    private String title;

    private Integer totalTheoryMarks;
    private Integer passingTheoryMarks;
    private Integer obtainedTheoryMarks;
    private Long schoolId;

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
    @JsonIgnore // don't serialize classes to avoid loops
    private List<SchoolClassEntity> classes = new ArrayList<>();
}
