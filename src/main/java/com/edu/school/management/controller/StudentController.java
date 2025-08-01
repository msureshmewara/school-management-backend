package com.edu.school.management.controller;

import com.edu.school.management.dto.StudentFeeResponse;
import com.edu.school.management.dto.StudentFullResponseDTO;
import com.edu.school.management.dto.StudentLoginResponseDTO;
import com.edu.school.management.dto.StudentRequestDTO;
import com.edu.school.management.dto.StudentSummaryDTO;
import com.edu.school.management.entity.StudentEntity;
import com.edu.school.management.exceptions.InvalidCredentialsException;
import com.edu.school.management.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // ✅ Required for annotations

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/createStudent")
    public ResponseEntity<StudentEntity> createStudent(@Valid @RequestBody StudentRequestDTO user) {
        return ResponseEntity.ok(studentService.createStudent(user));
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<List<StudentEntity>> getAllStudents(@RequestParam Long schoolId) {
        return ResponseEntity.ok(studentService.getAllStudentsBySchoolId(schoolId));
    }


    @PostMapping("/login")
    public ResponseEntity<StudentLoginResponseDTO> loginUser(@RequestBody StudentEntity user) {
        return studentService.getUserByUsernameAndPassword(user.getUsername(), user.getPassword())
                .map(student -> {
                    StudentLoginResponseDTO dto = new StudentLoginResponseDTO();
                    dto.setStudentPin(student.getStudentPin());
                    dto.setUsername(student.getUsername());
                    dto.setFirstName(student.getFirstName());
                    dto.setLastName(student.getLastName());
                    dto.setGender(student.getGender());
                    dto.setRollNumber(student.getRollNumber());
                    dto.setScholarNumber(student.getScholarNumber());
                    dto.setContactNumber(student.getContactNumber());
                    dto.setStatus(student.getStatus());
                    dto.setClassName(student.getClassName());
                    dto.setDob(student.getDOB().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    dto.setAddress(student.getAddress());
                    dto.setCaste(student.getCaste());
                    dto.setReligion(student.getReligion());
                    dto.setNationality(student.getNationality());
                    dto.setMotherToungue(student.getMotherToungue());
                    dto.setCity(student.getCity());
                    dto.setState(student.getState());
                    dto.setPinCode(student.getPinCode());
                    dto.setCountry(student.getCountry());
                    dto.setTotalFees(student.getTotalFees());
                    dto.setFeesDiscount(student.getFeesDiscount());
                    dto.setCreatedBy(student.getCreatedBy());
                    dto.setRoleTitle(student.getRole() != null ? student.getRole().getTitle() : null);

                    return ResponseEntity.ok(dto);
                })
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));
    }


    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable Long id, @RequestBody StudentEntity user) {
        return studentService.updateStudent(id, user)
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
            @RequestParam Integer rollNumber,
            @RequestParam String className,  // change name for clarity
            @RequestParam String section) {
        StudentFeeResponse response = studentService.getStudentWithFees(rollNumber, className, section);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/studentDetails/{id}")
    public ResponseEntity<StudentFullResponseDTO> getFullStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getFullStudentDetails(id));
    }
    
    @GetMapping("/stuDetailsByClass/{id}")
    public ResponseEntity<List<StudentSummaryDTO>> getFullStudentByClassId(@PathVariable Long id) {
    	 List<StudentSummaryDTO> students = studentService.getSummariesByClass(id);
         return ResponseEntity.ok(students);
    }
    
    @GetMapping("/summary")
    public ResponseEntity<?> getStudentSummary(
            @RequestParam Long classId,
            @RequestParam(required = false) Integer rollNumber) {

        if (rollNumber == null) {
            // Return all students in class by classId
            List<StudentSummaryDTO> students = studentService.getSummariesByClass(classId);
            return ResponseEntity.ok(students);
        } else {
            // Return one student by classId and roll number
            Optional<StudentSummaryDTO> studentOpt = studentService.getSummaryByClassAndRoll(classId, rollNumber);
            return studentOpt
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found"));
        }
    }


    @GetMapping("/find")
    public ResponseEntity<?> getStudentByClassNameAndRollNumber(
            @RequestParam Long classId,
            @RequestParam Integer rollNumber) {

        Optional<StudentEntity> studentOpt = studentService.findBySchoolClass_ClassIdAndRollNumber(classId, rollNumber);

        return studentOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/pending-fees")
    public ResponseEntity<List<StudentSummaryDTO>> getStudentsWithPendingFees() {
        List<StudentSummaryDTO> pendingStudents = studentService.getStudentsWithPendingFees();
        return ResponseEntity.ok(pendingStudents);
    }

}
