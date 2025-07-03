package com.edu.school.management.controller;

import com.edu.school.management.dto.StudentFeeResponse;
import com.edu.school.management.dto.StudentFullResponseDTO;
import com.edu.school.management.entity.StudentEntity;
import com.edu.school.management.exceptions.InvalidCredentialsException;
import com.edu.school.management.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // ✅ Required for annotations

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/createStudent")
    public ResponseEntity<StudentEntity> createStudent(@Valid @RequestBody StudentEntity user) {
        return ResponseEntity.ok(studentService.createUser(user));
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<StudentEntity>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping("/login")
    public ResponseEntity<StudentEntity> loginUser(@RequestBody StudentEntity user) {
        return studentService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentEntity> updateUser(@PathVariable Long id, @RequestBody StudentEntity user) {
        return studentService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        studentService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/fees")
    public ResponseEntity<StudentFeeResponse> getStudentFees(
            @RequestParam String rollNumber,
            @RequestParam String className,  // change name for clarity
            @RequestParam String section) {
        StudentFeeResponse response = studentService.getStudentWithFees(rollNumber, className, section);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/studentDetails/{id}")
    public ResponseEntity<StudentFullResponseDTO> getFullStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getFullStudentDetails(id));
    }

}
