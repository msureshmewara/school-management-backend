package com.edu.school.management.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

//    @Query("SELECT COUNT(s) FROM StudentEntity s WHERE MONTH(s.dOB) = :month AND DAY(s.dOB) = :day")
//    long countByDOBMonthDay(int month, int day);

    @Query("SELECT COALESCE(SUM(s.totalFees - s.feesDiscount), 0) FROM StudentEntity s")
    Double sumTotalFeesMinusDiscount();
    
    @Query("SELECT COUNT(s) FROM StudentEntity s WHERE FUNCTION('month', s.dOB) = :month AND FUNCTION('day', s.dOB) = :day")
    long countByDOBMonthAndDay(@Param("month") int month, @Param("day") int day);

 // 2) Get list of all student IDs
    @Query("SELECT s.studentPin FROM StudentEntity s")
    List<Long> findAllIds();

    // 3) Find students whose birthday is today (month and day match)
    @Query("SELECT s FROM StudentEntity s WHERE FUNCTION('MONTH', s.dOB) = :month AND FUNCTION('DAY', s.dOB) = :day")
    List<StudentEntity> findBirthdaysToday(@Param("month") int month, @Param("day") int day);

    // You can add your existing countByDOBMonthDay as:
    @Query("SELECT COUNT(s) FROM StudentEntity s WHERE FUNCTION('MONTH', s.dOB) = :month AND FUNCTION('DAY', s.dOB) = :day")
    long countByDOBMonthDay(@Param("month") int month, @Param("day") int day);

}

