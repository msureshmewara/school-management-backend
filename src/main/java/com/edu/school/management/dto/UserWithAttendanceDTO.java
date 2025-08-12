package com.edu.school.management.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserWithAttendanceDTO {
	 private Long id;
	    private String firstName;
	    private String lastName;
	    private String contactNumber;
	    private LocalDate dob;
	    private Long schoolId;
	    private List<AttendanceDTO> attendance;
	    
	    //Test
	    @Column(nullable = false)
	    private String email;

	    private String qualification;
	    private String experience;
}
