package com.edu.school.management.controller;


import com.edu.school.management.dto.TeacherAttendanceRequest;
import com.edu.school.management.entity.TeacherAttendanceEntity;
import com.edu.school.management.entity.UserEntity;
import com.edu.school.management.repository.TeacherAttendanceRepository;
import com.edu.school.management.repository.TimetableRepository;
import com.edu.school.management.repository.UserRepository;
import com.edu.school.management.response.ApiResponse;
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
    
    @PostMapping("/markBulkAttendance")
    public ResponseEntity<ApiResponse<String>> markBulkAttendance(
            @RequestBody List<TeacherAttendanceRequest> attendanceRequests) {

        List<TeacherAttendanceEntity> records = attendanceRequests.stream().map(req -> {
            UserEntity teacher = userRepository.findById(req.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + req.getTeacherId()));

            LocalDate attendanceDate = req.getDate() != null ? req.getDate() : LocalDate.now();

            List<TeacherAttendanceEntity> existingRecords = attendanceRepository
                    .findAllByTeacher_IdAndDate(teacher.getId(), attendanceDate);

            if (!existingRecords.isEmpty()) {
                // Update the first existing record (if multiple exist)
                TeacherAttendanceEntity existing = existingRecords.get(0);
                existing.setIsPresent(req.getIsPresent());
                return existing;
            } else {
                // Create new record
                return TeacherAttendanceEntity.builder()
                        .teacher(teacher)
                        .date(attendanceDate)
                        .isPresent(req.getIsPresent())
                        .build();
            }
        }).toList();

        attendanceRepository.saveAll(records);

        return ResponseEntity.ok(new ApiResponse<>("success", "Attendance saved/updated successfully", null));
    }

    
    


}

