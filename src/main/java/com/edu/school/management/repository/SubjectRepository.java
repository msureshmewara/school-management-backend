package com.edu.school.management.repository;

import com.edu.school.management.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    @Query("SELECT s FROM SubjectEntity s JOIN s.classes c WHERE c.classId = :classId")
    List<SubjectEntity> findAllByClassId(Long classId);

    @Query("SELECT s FROM SubjectEntity s JOIN s.classes c WHERE s.schoolId = :schoolId AND c.classId = :classId")
    List<SubjectEntity> findBySchoolIdAndClassId(@Param("schoolId") Long schoolId, @Param("classId") Long classId);

    List<SubjectEntity> findBySchoolId(Long schoolId);

    @Query("SELECT s FROM SubjectEntity s JOIN s.classes c WHERE s.schoolId = :schoolId AND c.classId = :classId")
    List<SubjectEntity> findAllByClassIdAndSchoolId(@Param("classId") Long classId, @Param("schoolId") Long schoolId);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE cs FROM class_subjects cs " +
                   "JOIN subjects s ON s.subject_id = cs.subject_id " +
                   "WHERE cs.class_id = :classId", nativeQuery = true)
    void deleteClassSubjectLinksByClassId(@Param("classId") Long classId);
}
