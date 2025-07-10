package com.edu.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "marksheet_subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarksheetSubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalTheoryMarks;
    private Integer passingTheoryMarks;
    private Integer obtainedTheoryMarks;

    private Integer totalInternalMarks;
    private Integer passingInternalMarks;
    private Integer obtainedInternalMarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marksheet_id")
    private MarksheetEntity marksheet;
}
