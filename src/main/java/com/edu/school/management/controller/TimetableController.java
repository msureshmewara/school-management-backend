package com.edu.school.management.controller;

import com.edu.school.management.dto.TimetableRequestDTO;
import com.edu.school.management.dto.TimetableResponseDTO;
import com.edu.school.management.entity.*;
import com.edu.school.management.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableRepository timetableRepository;
    private final TeacherAttendanceRepository attendanceRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    // ✅ Create a timetable entry
    @PostMapping("/create")
    public ResponseEntity<TimetableEntity> createTimetable(@RequestBody TimetableRequestDTO request) {

        SchoolClassEntity schoolClass = schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        UserEntity teacher = userRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        TimetableEntity timetable = TimetableEntity.builder()
                .schoolClass(schoolClass)
                .subject(subject)
                .teacher(teacher)
                .dayOfWeek(request.getDayOfWeek())
                .period(request.getPeriod())
                .timeSlot(request.getTimeSlot())
                .build();

        TimetableEntity saved = timetableRepository.save(timetable);

        return ResponseEntity.ok(saved);
    }

    // ✅ Get timetable with attendance info
    @GetMapping("/{classId}")
    public ResponseEntity<List<TimetableResponseDTO>> getTimetableWithAttendance(
            @PathVariable Long classId,
            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null) {
            date = LocalDate.now();
        }

        List<TimetableEntity> timetable = timetableRepository.findBySchoolClass_ClassId(classId);
        List<TeacherAttendanceEntity> attendance = attendanceRepository.findByDate(date);

        Set<Long> absentTeacherIds = attendance.stream()
                .filter(a -> !a.getIsPresent())
                .map(a -> a.getTeacher().getId())
                .collect(Collectors.toSet());

        List<TimetableResponseDTO> response = timetable.stream()
                .map(entry -> {
                    boolean isAbsent = absentTeacherIds.contains(entry.getTeacher().getId());

                    return TimetableResponseDTO.builder()
                            .className(entry.getSchoolClass().getClassName())
                            .dayOfWeek(entry.getDayOfWeek())
                            .period(entry.getPeriod())
                            .timeSlot(entry.getTimeSlot())
                            .subjectName(entry.getSubject().getSubjectName())  // Make sure SubjectEntity has getSubjectName()
                            .teacherName(entry.getTeacher().getFirstName() + " " + entry.getTeacher().getLastName())
                            .teacherAbsent(isAbsent)
                            .build();
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/today/{classId}")
    public ResponseEntity<List<TimetableResponseDTO>> getTodaysTimetable(@PathVariable Long classId) {
        // Get today's day of week as String (e.g., "MONDAY")
        String todayDayOfWeek = LocalDate.now().getDayOfWeek().name();

        // Fetch timetable entries for the class and today’s day of week
        List<TimetableEntity> timetable = timetableRepository.findBySchoolClass_ClassIdAndDayOfWeek(classId, todayDayOfWeek);

        // Convert entities to DTOs
        List<TimetableResponseDTO> response = timetable.stream()
            .map(entry -> TimetableResponseDTO.builder()
                .className(entry.getSchoolClass().getClassName())
                .dayOfWeek(entry.getDayOfWeek())
                .period(entry.getPeriod())
                .timeSlot(entry.getTimeSlot())
                .subjectName(entry.getSubject().getSubjectName())
                .teacherName(entry.getTeacher().getFirstName() + " " + entry.getTeacher().getLastName())
                .teacherAbsent(false)  // or handle absence if you want
                .build()
            )
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/today/{classId}/full")
    public ResponseEntity<List<TimetableResponseDTO>> getTodaysTimetableWithAttendance(@PathVariable Long classId) {

        LocalDate today = LocalDate.now();
        String todayDayOfWeek = today.getDayOfWeek().name();

        // Fetch timetable entries for class and day of week
        List<TimetableEntity> timetable = timetableRepository.findBySchoolClass_ClassIdAndDayOfWeek(classId, todayDayOfWeek);

        // Fetch attendance for today (for all teachers)
        List<TeacherAttendanceEntity> attendanceList = attendanceRepository.findByDate(today);

        // Collect IDs of absent teachers
        Set<Long> absentTeacherIds = attendanceList.stream()
            .filter(a -> !a.getIsPresent())
            .map(a -> a.getTeacher().getId())
            .collect(Collectors.toSet());

        // Build response with attendance info
        List<TimetableResponseDTO> response = timetable.stream()
            .map(entry -> {
                boolean isAbsent = absentTeacherIds.contains(entry.getTeacher().getId());
                return TimetableResponseDTO.builder()
                    .className(entry.getSchoolClass().getClassName())
                    .dayOfWeek(entry.getDayOfWeek())
                    .period(entry.getPeriod())
                    .timeSlot(entry.getTimeSlot())
                    .subjectName(entry.getSubject().getSubjectName())
                    .teacherName(entry.getTeacher().getFirstName() + " " + entry.getTeacher().getLastName())
                    .teacherAbsent(isAbsent)
                    .build();
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
