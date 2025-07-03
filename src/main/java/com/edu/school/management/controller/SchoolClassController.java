package com.edu.school.management.controller;

import com.edu.school.management.entity.SchoolClassEntity;
import com.edu.school.management.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @PostMapping("/createClass")
    public ResponseEntity<SchoolClassEntity> createClass(@RequestBody SchoolClassEntity schoolClass) {
        return ResponseEntity.ok(schoolClassService.createClass(schoolClass));
    }

    @GetMapping("/getAllClasses")
    public ResponseEntity<List<SchoolClassEntity>> getAllClasses() {
        return ResponseEntity.ok(schoolClassService.getAllClasses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassEntity> getClassById(@PathVariable Long id) {
        return schoolClassService.getClassById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        schoolClassService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
