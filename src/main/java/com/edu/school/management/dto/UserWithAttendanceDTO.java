package com.edu.school.management.dto;

import com.edu.school.management.entity.TeacherAttendanceEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserWithAttendanceDTO {
    private Long id;
//    private String username;
    private String firstName;
    private String lastName;
    private String contactNumber;
//    private String gender;
//    private String address;
//    private String city;
//    private String state;
//    private String country;
//    private String status;

    private List<AttendanceDTO> attendance;
}
