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
@Table(name = "school_classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(nullable = false, unique = true)
    private String className;

    @Column(nullable = false)
    private String section;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    @JsonIgnore // hide the back-reference to students
    private List<StudentEntity> students = new ArrayList<>();

    @ManyToMany(mappedBy = "classes")
    @JsonIgnore
    private List<SubjectEntity> subjects = new ArrayList<>();


}
