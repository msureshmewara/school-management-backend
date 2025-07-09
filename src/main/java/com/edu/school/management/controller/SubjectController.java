package com.edu.school.management.controller;

import com.edu.school.management.dto.SubjectRequestDTO;
import com.edu.school.management.entity.SubjectEntity;
import com.edu.school.management.service.SubjectService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    
    @PostMapping("/createSubject")
    public ResponseEntity<SubjectEntity> createSubject(@RequestBody SubjectRequestDTO request) {
        SubjectEntity createdSubject = subjectService.createSubject(request);
        return ResponseEntity.ok(createdSubject);
    }

    @GetMapping("/by-class/{classId}")
    public List<SubjectEntity> getSubjectsByClassId(@PathVariable Long classId) {
        return subjectService.getSubjectsByClassId(classId);
    }
    
    @GetMapping("/all")
    public List<SubjectEntity> getAllSubjects() {
        return subjectService.getAllSubjects();
    }
}
