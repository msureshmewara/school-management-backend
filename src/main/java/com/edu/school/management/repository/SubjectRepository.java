package com.edu.school.management.repository;

import com.edu.school.management.entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    @Query("SELECT s FROM SubjectEntity s JOIN s.classes c WHERE c.classId = :classId")
    List<SubjectEntity> findAllByClassId(Long classId);

}
