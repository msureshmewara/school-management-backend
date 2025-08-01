package com.edu.school.management.service;

import com.edu.school.management.dto.SubjectRequestDTO;
import com.edu.school.management.entity.SchoolClassEntity;
import com.edu.school.management.entity.SubjectEntity;
import com.edu.school.management.repository.SchoolClassRepository;
import com.edu.school.management.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SchoolClassRepository classRepository;

    public SubjectService(SubjectRepository subjectRepository, SchoolClassRepository classRepository) {
        this.subjectRepository = subjectRepository;
        this.classRepository = classRepository;
    }

    public List<SubjectEntity> getSubjectsByClassId(Long classId) {
        return subjectRepository.findAllByClassId(classId);
    }
    
    public SubjectEntity createSubject(SubjectRequestDTO request) {
        List<SchoolClassEntity> classes = classRepository.findAllById(request.getClassIds());

        SubjectEntity subject = SubjectEntity.builder()
                .title(request.getTitle())
                .totalTheoryMarks(request.getTotalTheoryMarks())
                .passingTheoryMarks(request.getPassingTheoryMarks())
                .obtainedTheoryMarks(request.getObtainedTheoryMarks())
                .hasInternal(request.getHasInternal())
                .totalInternalMarks(request.getTotalInternalMarks())
                .passingInternalMarks(request.getPassingInternalMarks())
                .obtainedInternalMarks(request.getObtainedInternalMarks())
                .classes(classes)
                .schoolId(request.getSchoolId())
                .build();

        return subjectRepository.save(subject);
    }
    
    public List<SubjectEntity> getAllSubjects() {
        return subjectRepository.findAll();
    }
    
    public List<SubjectEntity> getSubjectsBySchoolAndClass(Long schoolId, Long classId) {
        return subjectRepository.findBySchoolIdAndClassId(schoolId, classId);
    }
    
    public List<SubjectEntity> getAllSubjectsBySchoolId(Long schoolId) {
        return subjectRepository.findBySchoolId(schoolId);
    }


}
