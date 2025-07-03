package com.edu.school.management.service;

import com.edu.school.management.entity.SchoolClassEntity;
import com.edu.school.management.repository.SchoolClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;

    public SchoolClassEntity createClass(SchoolClassEntity schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    public List<SchoolClassEntity> getAllClasses() {
        return schoolClassRepository.findAll();
    }

    public Optional<SchoolClassEntity> getClassById(Long id) {
        return schoolClassRepository.findById(id);
    }

    public void deleteClass(Long id) {
        schoolClassRepository.deleteById(id);
    }
}
