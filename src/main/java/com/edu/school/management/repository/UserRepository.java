package com.edu.school.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edu.school.management.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
   

	Optional<UserEntity> findByUsernameAndPassword(String username, String password);
	List<UserEntity> findByRoleRoleId(Long roleId);
	
	long countByRoleTitle(String roleTitle);
	@Query("SELECT COUNT(u) FROM UserEntity u WHERE FUNCTION('month', u.dOB) = :month AND FUNCTION('day', u.dOB) = :day AND u.role.title = 'TEACHER'")
	long countTeachersWithBirthdayToday(@Param("month") int month, @Param("day") int day);

}

