package com.edu.school.management.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.edu.school.management.entity.TeacherAttendanceEntity;

public interface TeacherAttendanceRepository extends JpaRepository<TeacherAttendanceEntity, Long> {
    List<TeacherAttendanceEntity> findByDate(LocalDate date);
    List<TeacherAttendanceEntity> findAllByTeacher_IdAndDate(Long teacherId, LocalDate date);
    long countByDateAndIsPresentFalse(LocalDate date);
    @Query("SELECT ta.teacher.id FROM TeacherAttendanceEntity ta WHERE ta.date = :date AND ta.isPresent = false")
    List<Long> findAbsentTeacherIdsByDate(LocalDate date);
    
    long countByDateAndIsPresentFalseAndSchoolId(LocalDate date, Long schoolId);
    @Query("SELECT ta.teacher.id FROM TeacherAttendanceEntity ta WHERE ta.date = :date AND ta.isPresent = false AND ta.schoolId = :schoolId")
    List<Long> findAbsentTeacherIdsByDateAndSchoolId(LocalDate date, Long schoolId);

    
    List<TeacherAttendanceEntity> findByDateAndSchoolId(LocalDate date, Long schoolId);


}
