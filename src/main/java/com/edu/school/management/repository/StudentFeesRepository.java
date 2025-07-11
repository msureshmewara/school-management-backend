package com.edu.school.management.repository;

import com.edu.school.management.entity.StudentFeesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StudentFeesRepository extends JpaRepository<StudentFeesEntity, Long> {
    List<StudentFeesEntity> findByStudentStudentPin(Long studentId);
    List<StudentFeesEntity> findByStudent_ClassNameAndStudent_RollNumber(String className, Integer rollNumber);
    
    @Query("SELECT COALESCE(SUM(f.paidAmount), 0) FROM StudentFeesEntity f WHERE f.paymentDate = :date")
    double sumPaidAmountByPaymentDate(LocalDate date);

    @Query("SELECT COALESCE(SUM(f.paidAmount), 0) FROM StudentFeesEntity f")
    double sumPaidAmount();
}
