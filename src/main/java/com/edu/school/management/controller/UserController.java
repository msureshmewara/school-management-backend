package com.edu.school.management.controller;

import com.edu.school.management.dto.LoginResponseDTO;
import com.edu.school.management.dto.UserWithAttendanceDTO;
import com.edu.school.management.entity.UserEntity;
import com.edu.school.management.exceptions.InvalidCredentialsException;
import com.edu.school.management.response.ApiResponse;
import com.edu.school.management.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // ✅ Required for annotations

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers(@RequestParam Long schoolId) {
        return ResponseEntity.ok(userService.getAllUsersBySchoolId(schoolId));
    }



    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> loginUser(@RequestBody UserEntity userR) {
        return userService.getUserByUsernameAndPassword(userR.getUsername(), userR.getPassword())
            .map(user -> {
                LoginResponseDTO dto = new LoginResponseDTO();
                dto.setId(user.getId());
                dto.setUsername(user.getUsername());
                dto.setFirstName(user.getFirstName());
                dto.setLastName(user.getLastName());
                dto.setGender(user.getGender());
                dto.setContactNumber(user.getContactNumber());
                dto.setAddress(user.getAddress());
                dto.setCity(user.getCity());
                dto.setState(user.getState());
                dto.setPinCode(user.getPinCode());
                dto.setCountry(user.getCountry());
                dto.setStatus(user.getStatus());
                dto.setRole(user.getRole().getTitle());
                dto.setDob(user.getDOB().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                dto.setSchoolId(user.getSchoolId());

                ApiResponse<LoginResponseDTO> response = ApiResponse.<LoginResponseDTO>builder()
                    .status("success")
                    .message("Login successful")
                    .data(dto)
                    .build();

                return ResponseEntity.ok(response);
            })
            .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
    }




    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/byRoleId/{roleId}")
    public ResponseEntity<List<UserEntity>> getUsersByRoleId(@PathVariable Long roleId) {
        List<UserEntity> users = userService.getUsersByRoleId(roleId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/byRoleId/{roleId}/with-attendance")
    public ResponseEntity<List<UserWithAttendanceDTO>> getUsersWithAttendanceByRoleId(@PathVariable Long roleId) {
        List<UserWithAttendanceDTO> result = userService.getUsersWithAttendanceByRoleId(roleId);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/byUserId/{userId}/with-attendance")
    public ResponseEntity<UserWithAttendanceDTO> getUserWithAttendanceByUserId(@PathVariable Long userId) {
        return userService.getUserWithAttendanceByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/getSchoolUsers/{roleId}/schoolId/{schoolId}/with-attendance")
    public ResponseEntity<List<UserWithAttendanceDTO>> getUsersWithAttendanceByRoleAndSchoolId(
            @PathVariable Long roleId,
            @PathVariable Long schoolId) {

        List<UserWithAttendanceDTO> result = userService.getUsersWithAttendanceByRoleAndSchoolId(roleId, schoolId);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    
    @GetMapping("/getUsersByRole/{roleId}")
    public ResponseEntity<List<UserEntity>> getUsersByRole(@PathVariable Long roleId) {
        List<UserEntity> users = userService.getUsersByRoleId(roleId);
        return ResponseEntity.ok(users);
    }
}
