package com.edu.school.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(
    name = "school_classes",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_class_school_section",
        columnNames = {"class_name", "section", "school_id"}
    )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(nullable = false)
    private String section;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<StudentEntity> students = new ArrayList<>();

    @ManyToMany(mappedBy = "classes")
    @JsonIgnore
    private List<SubjectEntity> subjects = new ArrayList<>();
}
