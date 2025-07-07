package com.edu.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
	boolean existsBySubjectName(String subjectName);
}

