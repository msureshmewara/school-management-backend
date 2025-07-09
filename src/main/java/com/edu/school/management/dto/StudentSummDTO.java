package com.edu.school.management.dto;

public record StudentSummDTO(
	    Long studentPin,
	    String username,
	    String firstName,
	    String lastName,
	    Integer rollNumber
	) {}
