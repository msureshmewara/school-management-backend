package com.edu.school.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.StudentEntity;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
   

	Optional<StudentEntity> findByUsernameAndPassword(String username, String password);
	Optional<StudentEntity> findByRollNumberAndSchoolClass_ClassNameAndSchoolClass_Section(
		    Integer rollNumber, String className, String section);
	
	List<StudentEntity> findByClassName(String className);
	Optional<StudentEntity> findByClassNameAndRollNumber(String className, Integer rollNumber);
	
    Optional<StudentEntity> findBySchoolClass_ClassIdAndRollNumber(Long classId, Integer rollNumber);
    List<StudentEntity> findBySchoolClass_ClassId(Long classId);


}

