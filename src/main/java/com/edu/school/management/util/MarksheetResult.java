package com.edu.school.management.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class MarksheetResult {

    private StudentInfo student;
    private boolean overallPass;
    private List<SubjectResult> subjectResults;

    @Data
    @AllArgsConstructor
    public static class StudentInfo {
        private String firstName;
        private String lastName;
        private LocalDate dob;
        private Integer rollNumber;
        private String scholarNumber;
        private String fatherName;
        private String motherName;
        private String className;
        private String section;
        private String enrollmentNumber;
        private String currentEduBoard;
    }

    @Data
    @AllArgsConstructor
    public static class SubjectResult {
        private String subjectTitle;
        private boolean isPassed;
        private Integer obtainedTheory;
        private Integer obtainedInternal;
    }
}
