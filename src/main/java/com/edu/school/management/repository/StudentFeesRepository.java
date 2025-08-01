package com.edu.school.management.repository;

import com.edu.school.management.entity.StudentFeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StudentFeesRepository extends JpaRepository<StudentFeesEntity, Long> {
    List<StudentFeesEntity> findByStudentStudentPin(Long studentId);
    List<StudentFeesEntity> findByStudent_ClassNameAndStudent_RollNumber(String className, Integer rollNumber);
    
    
    @Query("SELECT COALESCE(SUM(f.paidAmount), 0) FROM StudentFeesEntity f")
    double sumPaidAmount();
    
    @Query("SELECT COALESCE(SUM(f.paidAmount), 0) FROM StudentFeesEntity f WHERE f.student.studentPin = :studentId")
    double sumPaidAmountByStudent(@Param("studentId") Long studentId);


    @Query("SELECT f FROM StudentFeesEntity f WHERE f.paymentDate = :date")
    List<StudentFeesEntity> findTransactionsByPaymentDate(LocalDate date);
    
    @Query("SELECT COALESCE(SUM(fees.paidAmount), 0) FROM StudentFeesEntity fees WHERE fees.paymentDate = :date AND fees.student.school.id = :schoolId")
    Double sumPaidAmountByPaymentDateAndSchoolId(@Param("date") LocalDate date, @Param("schoolId") Long schoolId);

    List<StudentFeesEntity> findTransactionsByPaymentDateAndSchoolId(LocalDate date, Long schoolId);
   
    @Query("SELECT COALESCE(SUM(f.paidAmount), 0) FROM StudentFeesEntity f WHERE f.student.school.id = :schoolId")
    Double sumPaidAmountBySchoolId(@Param("schoolId") Long schoolId);



}
