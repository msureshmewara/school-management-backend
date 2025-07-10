package com.edu.school.management.dto;

public record SubjectDTO(
	    Long subjectId,
	    String subjectName,
	    Integer totalTheoryMarks,
	    Integer passingTheoryMarks,
	    Integer obtainedTheoryMarks,
	    Boolean hasInternal,
	    Integer totalInternalMarks,
	    Integer passingInternalMarks,
	    Integer obtainedInternalMarks
	) {}

