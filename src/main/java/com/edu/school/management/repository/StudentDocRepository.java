package com.edu.school.management.repository;

import com.edu.school.management.entity.StudentDocEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentDocRepository extends JpaRepository<StudentDocEntity, Long> {
    List<StudentDocEntity> findByStudent_StudentPin(Long studentPin);
    Optional<StudentDocEntity> findByStuDocIdAndStudent_StudentPin(Long stuDocId, Long studentPin);
    Optional<StudentDocEntity> findByStudent_StudentPinAndDocType(Long studentPin, String docType);

}
