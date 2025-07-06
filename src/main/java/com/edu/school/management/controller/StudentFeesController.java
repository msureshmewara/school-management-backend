package com.edu.school.management.controller;

import com.edu.school.management.dto.StudentFeesInfoDTO;
import com.edu.school.management.entity.StudentEntity;
import com.edu.school.management.entity.StudentFeesEntity;
import com.edu.school.management.repository.StudentFeesRepository;
import com.edu.school.management.repository.StudentRepository;
import com.edu.school.management.service.StudentFeesService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fees")
@RequiredArgsConstructor
public class StudentFeesController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentFeesRepository studentFeesRepository;

    private final StudentFeesService service;

    // ✅ Create new fee entry for a student
    @PostMapping("/pay/{studentId}")
    public ResponseEntity<StudentFeesEntity> payFees(
            @PathVariable Long studentId,
            @RequestBody StudentFeesEntity feeData) {

        StudentEntity student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        feeData.setStudent(student);  // Link fee to student

        StudentFeesEntity savedFee = studentFeesRepository.save(feeData);

        return ResponseEntity.ok(savedFee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentFeesEntity> update(@PathVariable Long id, @RequestBody StudentFeesEntity updated) {
        return ResponseEntity.ok(service.update(id, updated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentFeesEntity> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentFeesEntity>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(service.getByStudentId(studentId));
    }

    @GetMapping
    public ResponseEntity<List<StudentFeesEntity>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/search")
    public ResponseEntity<StudentFeesInfoDTO> getFeesByClassAndRoll(
            @RequestParam("className") String className,
            @RequestParam("rollNumber") Integer rollNumber) {

        List<StudentFeesEntity> feesList = studentFeesRepository
                .findByStudent_ClassNameAndStudent_RollNumber(className, rollNumber);

        if (feesList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        StudentEntity student = feesList.get(0).getStudent(); // All fees belong to same student

        String studentName = student.getFirstName() + " " + student.getLastName();
        String fatherName = student.getFamily() != null && !student.getFamily().isEmpty()
                ? student.getFamily().get(0).getFatherName()
                : "N/A";

        Double totalPaid = feesList.stream()
                .mapToDouble(StudentFeesEntity::getPaidAmount)
                .sum();

        Double totalFees = student.getTotalFees();
        Double dueFees = totalFees - totalPaid;

        StudentFeesInfoDTO dto = new StudentFeesInfoDTO(
                student.getStudentPin(),
                studentName,
                fatherName,
                student.getRollNumber(),
                student.getScholarNumber(),
                student.getSchoolClass().getClassName() + " - " + student.getSchoolClass().getSection(),
                totalFees,
                totalPaid,
                dueFees
        );

        return ResponseEntity.ok(dto);
    }
}
