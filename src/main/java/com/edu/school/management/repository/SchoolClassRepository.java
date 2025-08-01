package com.edu.school.management.repository;

import com.edu.school.management.entity.SchoolClassEntity;
import com.edu.school.management.entity.StudentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SchoolClassRepository extends JpaRepository<SchoolClassEntity, Long> {
    Optional<SchoolClassEntity> findByClassNameAndSection(String className, String section);
    List<SchoolClassEntity> findBySchoolId(Long schoolId);
    Optional<SchoolClassEntity> findByClassIdAndSchoolId(Long classId, Long schoolId);


}
