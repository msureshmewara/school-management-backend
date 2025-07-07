package com.edu.school.management.controller;


import com.edu.school.management.entity.TeacherAttendanceEntity;
import com.edu.school.management.entity.UserEntity;
import com.edu.school.management.exceptions.InvalidCredentialsException;
import com.edu.school.management.repository.TeacherAttendanceRepository;
import com.edu.school.management.repository.UserRepository;
import com.edu.school.management.response.ApiResponse;
import com.edu.school.management.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // ✅ Required for annotations

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class TeacherAttendanceController {

    private final TeacherAttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<TeacherAttendanceEntity> markAttendance(
            @PathVariable Long teacherId,
            @RequestParam("isPresent") boolean isPresent) {

        UserEntity teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        TeacherAttendanceEntity attendance = TeacherAttendanceEntity.builder()
                .teacher(teacher)
                .date(LocalDate.now())
                .isPresent(isPresent)
                .build();

        return ResponseEntity.ok(attendanceRepository.save(attendance));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<TeacherAttendanceEntity>> getAttendanceByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(attendanceRepository.findAll()
                .stream()
                .filter(a -> a.getTeacher().getId().equals(teacherId))
                .toList());
    }
}

