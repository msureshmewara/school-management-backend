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

	@Query("SELECT u.id FROM UserEntity u WHERE u.role.title = 'TEACHER'")
	List<Long> findAllTeacherIds();
	
    @Query("SELECT u FROM UserEntity u WHERE u.role.title = 'TEACHER' AND FUNCTION('MONTH', u.dOB) = :month AND FUNCTION('DAY', u.dOB) = :day")
    List<UserEntity> findTeacherBirthdaysToday(@Param("month") int month, @Param("day") int day);

    
    List<UserEntity> findAllBySchoolId(Long schoolId);



    @Query("SELECT u FROM UserEntity u WHERE u.schoolId = :schoolId AND FUNCTION('MONTH', u.dOB) = :month AND FUNCTION('DAY', u.dOB) = :day AND u.role.title = 'TEACHER'")
    List<UserEntity> findTeacherBirthdaysTodayBySchoolId(@Param("schoolId") Long schoolId, @Param("month") int month, @Param("day") int day);

    // Get count of teachers
    long countByRoleTitleAndSchoolId(String roleTitle, Long schoolId);

    // Get all teacher IDs
    @Query("SELECT u.id FROM UserEntity u WHERE u.schoolId = :schoolId AND u.role.title = 'TEACHER'")
    List<Long> findAllTeacherIdsBySchoolId(@Param("schoolId") Long schoolId);
    
    @Query("SELECT u FROM UserEntity u WHERE u.role.roleId = :roleId AND u.schoolId = :schoolId")
    List<UserEntity> findByRoleIdAndSchoolId(@Param("roleId") Long roleId, @Param("schoolId") Long schoolId);

}

