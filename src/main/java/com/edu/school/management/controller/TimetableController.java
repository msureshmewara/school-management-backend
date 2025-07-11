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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
    
    

    @PostMapping("/create")
    public ResponseEntity<?> createBulkTimetable(@RequestBody TimetableRequestDTO request) {
        Long classId = request.getClassId();
        String dayOfWeek = request.getDayOfWeek();

        // 1. Validate class
        SchoolClassEntity schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + classId));

        Set<Integer> seenPeriods = new HashSet<>();
        Set<String> seenTimeSlots = new HashSet<>();
        Set<Long> seenSubjects = new HashSet<>();

        List<TimetableEntity> timetableEntries = new ArrayList<>();

        for (TimetableRequestDTO.PeriodEntry entry : request.getPeriods()) {
            // 2. Validate required fields
            if (entry.getTeacherId() == null || entry.getSubjectId() == null || entry.getTimeSlot() == null || entry.getPeriod() == null) {
                return ResponseEntity.badRequest().body("Missing required field in period entry: " + entry);
            }

            // 3. Check for duplicate period numbers
            if (!seenPeriods.add(entry.getPeriod())) {
                return ResponseEntity.badRequest().body("Duplicate period number: " + entry.getPeriod());
            }

            // 4. Check for duplicate or overlapping time slots
            if (!seenTimeSlots.add(entry.getTimeSlot())) {
                return ResponseEntity.badRequest().body("Duplicate or overlapping time slot: " + entry.getTimeSlot());
            }

            // 5. Check for repeated subject in the same day
            if (!seenSubjects.add(entry.getSubjectId())) {
                return ResponseEntity.badRequest().body("Subject ID " + entry.getSubjectId() + " is repeated on the same day.");
            }

            // 6. Validate teacher
            UserEntity teacher = userRepository.findById(entry.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + entry.getTeacherId()));

            // 7. Validate subject
            SubjectEntity subject = subjectRepository.findById(entry.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + entry.getSubjectId()));

            // 8. Build TimetableEntity
            TimetableEntity timetable = TimetableEntity.builder()
                    .teacher(teacher)
                    .subject(subject)
                    .schoolClass(schoolClass)
                    .dayOfWeek(dayOfWeek)
                    .period(entry.getPeriod())
                    .timeSlot(entry.getTimeSlot())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            timetableEntries.add(timetable);
        }

        // 9. Save all after validation
        timetableRepository.saveAll(timetableEntries);

        return ResponseEntity.ok("Timetable created for multiple periods successfully");
    }


    // ✅ Get timetable with attendance info
//    @GetMapping("/{classId}")
//    public ResponseEntity<List<TimetableResponseDTO>> getTimetableWithAttendance(
//            @PathVariable Long classId,
//            @RequestParam(name = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//
//        if (date == null) {
//            date = LocalDate.now();
//        }
//
//        List<TimetableEntity> timetable = timetableRepository.findBySchoolClass_ClassId(classId);
//        List<TeacherAttendanceEntity> attendance = attendanceRepository.findByDate(date);
//
//        Set<Long> absentTeacherIds = attendance.stream()
//                .filter(a -> !a.getIsPresent())
//                .map(a -> a.getTeacher().getId())
//                .collect(Collectors.toSet());
//
////        List<TimetableResponseDTO> response = timetable.stream()
////                .map(entry -> {
////                    boolean isAbsent = absentTeacherIds.contains(entry.getTeacher().getId());
////
////                    return TimetableResponseDTO.builder()
////                            .className(entry.getSchoolClass().getClassName())
////                            .dayOfWeek(entry.getDayOfWeek())
////                            .period(entry.getPeriod())
////                            .timeSlot(entry.getTimeSlot())
////                            .subjectName(entry.getSubject().getSubjectName())  // Make sure SubjectEntity has getSubjectName()
////                            .teacherName(entry.getTeacher().getFirstName() + " " + entry.getTeacher().getLastName())
////                            .teacherAbsent(isAbsent)
////                            .build();
////                })
////                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(response);
//    }
    
//    @GetMapping("/today/{classId}")
//    public ResponseEntity<List<TimetableResponseDTO>> getTodaysTimetable(@PathVariable Long classId) {
//        // Get today's day of week as String (e.g., "MONDAY")
//        String todayDayOfWeek = LocalDate.now().getDayOfWeek().name();
//
//        // Fetch timetable entries for the class and today’s day of week
//        List<TimetableEntity> timetable = timetableRepository.findBySchoolClass_ClassIdAndDayOfWeek(classId, todayDayOfWeek);
//
//        // Convert entities to DTOs
//        List<TimetableResponseDTO> response = timetable.stream()
//            .map(entry -> TimetableResponseDTO.builder()
//                .className(entry.getSchoolClass().getClassName())
//                .dayOfWeek(entry.getDayOfWeek())
//                .period(entry.getPeriod())
//                .timeSlot(entry.getTimeSlot())
//                .subjectName(entry.getSubject().getSubjectName())
//                .teacherName(entry.getTeacher().getFirstName() + " " + entry.getTeacher().getLastName())
//                .teacherAbsent(false)  // or handle absence if you want
//                .build()
//            )
//            .collect(Collectors.toList());
//
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping("/today/{classId}/full")
//    public ResponseEntity<List<TimetableResponseDTO>> getTodaysTimetableWithAttendance(@PathVariable Long classId) {
//
//        LocalDate today = LocalDate.now();
//        String todayDayOfWeek = today.getDayOfWeek().name();
//
//        // Fetch timetable entries for class and day of week
//        List<TimetableEntity> timetable = timetableRepository.findBySchoolClass_ClassIdAndDayOfWeek(classId, todayDayOfWeek);
//
//        // Fetch attendance for today (for all teachers)
//        List<TeacherAttendanceEntity> attendanceList = attendanceRepository.findByDate(today);
//
//        // Collect IDs of absent teachers
//        Set<Long> absentTeacherIds = attendanceList.stream()
//            .filter(a -> !a.getIsPresent())
//            .map(a -> a.getTeacher().getId())
//            .collect(Collectors.toSet());
//
//        // Build response with attendance info
////        List<TimetableResponseDTO> response = timetable.stream()
////            .map(entry -> {
////                boolean isAbsent = absentTeacherIds.contains(entry.getTeacher().getId());
////                return TimetableResponseDTO.builder()
////                    .className(entry.getSchoolClass().getClassName())
////                    .dayOfWeek(entry.getDayOfWeek())
////                    .period(entry.getPeriod())
////                    .timeSlot(entry.getTimeSlot())
////                    .subjectName(entry.getSubject().getSubjectName())
////                    .teacherName(entry.getTeacher().getFirstName() + " " + entry.getTeacher().getLastName())
////                    .teacherAbsent(isAbsent)
////                    .build();
////            })
////            .collect(Collectors.toList());
//
//        return ResponseEntity.ok([{}]);
//    }

    @GetMapping("/class/{classId}")
    public ResponseEntity<List<TimetableResponseDTO>> getTimetableByClassWithAttendance(
            @PathVariable Long classId,
            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null) {
            date = LocalDate.now();
        }

        List<TimetableEntity> timetable = timetableRepository.findBySchoolClass_ClassId(classId);
        List<TeacherAttendanceEntity> attendanceList = attendanceRepository.findByDate(date);

        // Absent teachers
        Set<Long> absentTeacherIds = attendanceList.stream()
                .filter(a -> !a.getIsPresent())
                .map(a -> a.getTeacher().getId())
                .collect(Collectors.toSet());

        List<TimetableResponseDTO> response = timetable.stream()
                .map(entry -> TimetableResponseDTO.builder()
                        .teacherId(entry.getTeacher().getId())
                        .teacherName(entry.getTeacher().getFirstName() + " " + entry.getTeacher().getLastName())
                        .isTeacherPresent(!absentTeacherIds.contains(entry.getTeacher().getId()))

                        .subjectId(entry.getSubject().getSubjectId())
                        .subjectName(entry.getSubject().getTitle())

                        .classId(entry.getSchoolClass().getClassId())
                        .className(entry.getSchoolClass().getClassName())
                        .classSection(entry.getSchoolClass().getSection())

                        .dayOfWeek(entry.getDayOfWeek())
                        .period(entry.getPeriod())
                        .timeSlot(entry.getTimeSlot())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
