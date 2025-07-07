package com.edu.school.management.controller;

import com.edu.school.management.entity.SubjectEntity;
import com.edu.school.management.service.SubjectService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectEntity> addSubject(@RequestBody SubjectEntity subject) {
        SubjectEntity saved = subjectService.createOrUpdateSubject(subject);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{subjectId}/add-class/{classId}")
    public ResponseEntity<SubjectEntity> addClassToSubject(
            @PathVariable Long subjectId,
            @PathVariable Long classId) {
        SubjectEntity updated = subjectService.addClassToSubject(subjectId, classId);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/getAllSubjects")
    public ResponseEntity<List<SubjectEntity>> getAllSubjectsWithClasses() {
        List<SubjectEntity> subjects = subjectService.getAllSubjectsWithClasses();
        return ResponseEntity.ok(subjects);
    }
}

