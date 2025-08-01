package com.edu.school.management.service.impl;

import com.edu.school.management.entity.SchoolEntity;
import com.edu.school.management.repository.SchoolRepository;
import com.edu.school.management.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public List<SchoolEntity> getAllSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public SchoolEntity getSchoolById(Long id) {
        return schoolRepository.findById(id).orElse(null);
    }

    @Override
    public SchoolEntity saveSchool(SchoolEntity school) {
        return schoolRepository.save(school);
    }
}
