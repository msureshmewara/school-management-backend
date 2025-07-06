package com.edu.school.management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDocEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stu_doc_id")
    private Long stuDocId;

    private String docType;       // Aadhar, Report Card, etc.
    private String fileName;
    private String filePath;
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_pin", nullable = false)
    private StudentEntity student;
    
    
}
