package com.edu.school.management.dto;

public record SubjectDTO(
	    Long subjectId,
	    String subjectName,
	    Integer totalTheoryMarks,
	    Integer passingTheoryMarks,
	    Integer totalInternalMarks,
	    Integer passingInternalMarks
	) {}

