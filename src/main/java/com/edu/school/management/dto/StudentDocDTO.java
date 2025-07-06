package com.edu.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDocDTO {
    private Long stuDocId;
    private String docType;
    private String fileName;
    private String filePath;
    private String contentType;
}
