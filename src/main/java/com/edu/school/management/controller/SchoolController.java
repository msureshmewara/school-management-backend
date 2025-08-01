package com.edu.school.management.controller;

import com.edu.school.management.entity.SchoolEntity;
import com.edu.school.management.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public List<SchoolEntity> getAllSchools() {
        return schoolService.getAllSchools();
    }

    @GetMapping("/{id}")
    public SchoolEntity getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    @PostMapping("/createSchool")
    public SchoolEntity createSchool(@RequestBody SchoolEntity school) {
        return schoolService.saveSchool(school);
    }
}
