package com.edu.school.management.dto;

import java.util.List;

public record ClassDetailsDTO(
	    SchoolClassDTO schoolClass,
	    List<StudentSummDTO> students,
	    List<SubjectDTO> subjects
	) {}
