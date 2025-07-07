package com.edu.school.management.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edu.school.management.entity.TeacherAttendanceEntity;

public interface TeacherAttendanceRepository extends JpaRepository<TeacherAttendanceEntity, Long> {
    List<TeacherAttendanceEntity> findByDate(LocalDate date);
}
