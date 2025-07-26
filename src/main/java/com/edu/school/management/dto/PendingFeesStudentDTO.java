package com.edu.school.management.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PendingFeesStudentDTO {
    private String studentName;
    private String fatherName;
    private String studentPhone;
    private String fatherPhone;
    private String motherPhone;
}
