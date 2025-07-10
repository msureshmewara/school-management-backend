package com.edu.school.management.dto;

import lombok.Data;

import java.util.List;

@Data
public class MarksheetRequest {
    private Long studentId;
    private Integer academicYear;
    private Long classId;
    private Integer grade;
    private Integer rollNumber;
    private List<SubjectMark> subjects;

    @Data
    public static class SubjectMark {
        private Long subjectId;
        private Integer totalTheoryMarks;
        private Integer passingTheoryMarks;
        private Integer obtainedTheoryMarks;
        private Integer totalInternalMarks;
        private Integer passingInternalMarks;
        private Integer obtainedInternalMarks;
    }
}
