package com.edu.school.management.repository;

import com.edu.school.management.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    List<StudentEntity> getAllStudentsBySchoolId(Long schoolId);

    @Query("SELECT COALESCE(SUM(s.totalFees - s.feesDiscount), 0) FROM StudentEntity s")
    Double sumTotalFeesMinusDiscount();

    @Query("SELECT COUNT(s) FROM StudentEntity s WHERE FUNCTION('MONTH', s.dOB) = :month AND FUNCTION('DAY', s.dOB) = :day")
    long countByDOBMonthAndDay(@Param("month") int month, @Param("day") int day);

    @Query("SELECT s.studentPin FROM StudentEntity s")
    List<Long> findAllIds();

    @Query("SELECT s FROM StudentEntity s WHERE FUNCTION('MONTH', s.dOB) = :month AND FUNCTION('DAY', s.dOB) = :day")
    List<StudentEntity> findBirthdaysToday(@Param("month") int month, @Param("day") int day);

    long countBySchoolId(Long schoolId);

    @Query("SELECT s.studentPin FROM StudentEntity s WHERE s.school.id = :schoolId")
    List<Long> findIdsBySchoolId(@Param("schoolId") Long schoolId);



    List<StudentEntity> findAllBySchoolId(Long schoolId);

    @Query("SELECT s FROM StudentEntity s WHERE s.school.id = :schoolId AND FUNCTION('MONTH', s.dOB) = :month AND FUNCTION('DAY', s.dOB) = :day")
    List<StudentEntity> findBirthdaysTodayBySchoolId(@Param("schoolId") Long schoolId, @Param("month") int month, @Param("day") int day);

    @Query("SELECT SUM(s.totalFees - s.feesDiscount) FROM StudentEntity s WHERE s.school.id = :schoolId")
    Double sumTotalFeesMinusDiscountBySchoolId(@Param("schoolId") Long schoolId);
    
    @Query("SELECT s FROM SubjectEntity s JOIN s.classes c WHERE s.schoolId = :schoolId AND c.classId = :classId")
    List<StudentEntity> findBySchoolClass_ClassIdAndSchoolId(Long classId, Long schoolId);

}
