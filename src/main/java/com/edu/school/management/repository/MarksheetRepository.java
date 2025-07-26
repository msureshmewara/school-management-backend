package com.edu.school.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.MarksheetEntity;
import com.edu.school.management.entity.StudentEntity;

public interface MarksheetRepository extends JpaRepository<MarksheetEntity, Long> {
    Optional<MarksheetEntity> findTopByStudentOrderByIdDesc(StudentEntity student);

}

