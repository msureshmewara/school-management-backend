package com.edu.school.management.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.TeacherAttendanceEntity;

public interface TeacherAttendanceRepository extends JpaRepository<TeacherAttendanceEntity, Long> {
    List<TeacherAttendanceEntity> findByDate(LocalDate date);
    List<TeacherAttendanceEntity> findAllByTeacher_IdAndDate(Long teacherId, LocalDate date);
    long countByDateAndIsPresentFalse(LocalDate date);

}
