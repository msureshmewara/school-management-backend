package com.edu.school.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // 👈 Generates getters & setters
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String contactNumber;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String country;
    private String status;
    private String role;  // e.g. "Admin", "Teacher"
    private String dob;   // formatted as dd/MM/yyyy
    private Long schoolId;
}
