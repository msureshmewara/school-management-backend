package com.edu.school.management.controller;

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
	
	public StudentFeesEntity createFee(Long studentId, StudentFeesEntity feeData) {
	    StudentEntity student = studentRepository.findById(studentId)
	        .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

	    feeData.setStudent(student); // 🔥 This is crucial

	    return studentFeesRepository.save(feeData);
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
}
