package com.edu.school.management.service;

import com.edu.school.management.entity.StudentFeesEntity;
import java.util.List;
import java.util.Optional;

public interface StudentFeesService {
    StudentFeesEntity create(StudentFeesEntity studentFees);
    StudentFeesEntity update(Long id, StudentFeesEntity studentFees);
    Optional<StudentFeesEntity> getById(Long id);
    List<StudentFeesEntity> getByStudentId(Long studentId);
    List<StudentFeesEntity> getAll();
}
