package com.edu.school.management.dto;

import java.util.List;

import lombok.Data;

@Data
public class SubjectRequestDTO {
	private String title;

    private Integer totalTheoryMarks;
    private Integer passingTheoryMarks;
    private Integer obtainedTheoryMarks;

    private Boolean hasInternal;
    private Integer totalInternalMarks;
    private Integer passingInternalMarks;
    private Integer obtainedInternalMarks;
    private Long schoolId;

    private List<Long> classIds; // IDs of classes the subject belongs to
}
