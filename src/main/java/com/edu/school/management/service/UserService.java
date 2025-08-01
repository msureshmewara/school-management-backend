package com.edu.school.management.service;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import com.edu.school.management.dto.AttendanceDTO;
import com.edu.school.management.dto.UserWithAttendanceDTO;
import com.edu.school.management.entity.UserEntity;
import com.edu.school.management.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<UserEntity> getAllUsersBySchoolId(Long schoolId) {
        return userRepository.findAllBySchoolId(schoolId);
    }

    
    public List<UserEntity> getUsersByRoleId(Long roleId) {
        return userRepository.findByRoleRoleId(roleId);
    }

    public Optional<UserEntity> updateUser(Long id, UserEntity updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

	public Optional<UserEntity> getUserByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return userRepository.findByUsernameAndPassword(username, password);
	}
	
	public List<UserWithAttendanceDTO> getUsersWithAttendanceByRoleId(Long roleId) {
	    List<UserEntity> users = userRepository.findByRoleRoleId(roleId);

	    return users.stream().map(user -> {
	        List<AttendanceDTO> attendance = user.getAttendance().stream()
	                .map(att -> AttendanceDTO.builder()
	                        .id(att.getId())
	                        .date(att.getDate())
	                        .isPresent(att.getIsPresent())
	                        .build())
	                .toList();

	        return UserWithAttendanceDTO.builder()
	                .id(user.getId())
//	                .username(user.getUsername())
	                .firstName(user.getFirstName())
	                .lastName(user.getLastName())
	                .contactNumber(user.getContactNumber())
//	                .gender(user.getGender())
//	                .address(user.getAddress())
//	                .city(user.getCity())
//	                .state(user.getState())
//	                .country(user.getCountry())
//	                .status(user.getStatus())
	                .attendance(attendance)
	                .build();
	    }).toList();
	}
	public Optional<UserWithAttendanceDTO> getUserWithAttendanceByUserId(Long userId) {
	    return userRepository.findById(userId).map(user -> {
	        LocalDate today = LocalDate.now();

	        // ✅ Filter only today's attendance (if exists)
	        Optional<AttendanceDTO> todayAttendance = user.getAttendance().stream()
	                .filter(att -> today.equals(att.getDate()))
	                .findFirst()
	                .map(att -> AttendanceDTO.builder()
	                        .id(att.getId())
	                        .date(att.getDate())
	                        .isPresent(att.getIsPresent())
	                        .build());

	        return UserWithAttendanceDTO.builder()
	                .id(user.getId())
	                .firstName(user.getFirstName())
	                .lastName(user.getLastName())
	                .contactNumber(user.getContactNumber())
	                .attendance(todayAttendance
	                        .map(List::of)
	                        .orElse(List.of())) // ✅ Return empty list if no attendance today
	                .build();
	    });
	}

	public List<UserWithAttendanceDTO> getUsersWithAttendanceByRoleAndSchoolId(Long roleId, Long schoolId) {
	    List<UserEntity> users = userRepository.findByRoleIdAndSchoolId(roleId, schoolId);

	    return users.stream().map(user -> {
	    	// Set attendance data
	        List<AttendanceDTO> attendanceDTOs = user.getAttendance().stream()
	            .map(att -> AttendanceDTO.builder()
	                .date(att.getDate())
	                .isPresent(att.getIsPresent())
	                .build())
	            .collect(Collectors.toList());
	    	UserWithAttendanceDTO dto = UserWithAttendanceDTO.builder()
	    		    .id(user.getId())
	    		    .firstName(user.getFirstName())
	    		    .lastName(user.getLastName())
	    		    .contactNumber(user.getContactNumber())
	    		    .dob(user.getDOB())
	    		    .schoolId(user.getSchoolId())
	    		    .attendance(attendanceDTOs)
	    		    .build();
	        

	        return dto;
	    }).collect(Collectors.toList());
	}



}
