package com.edu.school.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.StudentEntity;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
   

	Optional<StudentEntity> findByUsernameAndPassword(String username, String password);
	 Optional<StudentEntity> findByRollNumberAndStuClassAndSection(
	            String rollNumber, String stu_class, String section);
}

