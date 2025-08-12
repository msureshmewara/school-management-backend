package com.edu.school.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.edu.school.management.entity.TimetableEntity;

public interface TimetableRepository extends JpaRepository<TimetableEntity, Long> {
    List<TimetableEntity> findBySchoolClass_ClassId(Long classId);

    List<TimetableEntity> findBySchoolClass_ClassIdAndDayOfWeek(Long classId, String dayOfWeek);
    
    List<TimetableEntity> findBySchoolClass_ClassIdAndSchoolClass_SchoolId(Long classId, Long schoolId);
    List<TimetableEntity> findBySchoolClass_ClassIdAndSchoolIdAndDayOfWeek(Long classId, Long schoolId, String dayOfWeek);

    @Modifying
    @Transactional
    @Query("DELETE FROM TimetableEntity t WHERE t.schoolClass.classId = :classId")
    void deleteByClassId(@Param("classId") Long classId);
}
