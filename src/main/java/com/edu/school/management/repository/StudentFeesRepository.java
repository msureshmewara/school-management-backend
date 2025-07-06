package com.edu.school.management.repository;

import com.edu.school.management.entity.StudentFeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentFeesRepository extends JpaRepository<StudentFeesEntity, Long> {
    List<StudentFeesEntity> findByStudentStudentPin(Long studentId);
    List<StudentFeesEntity> findByStudent_ClassNameAndStudent_RollNumber(String className, Integer rollNumber);
    

}
