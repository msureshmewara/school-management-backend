package com.edu.school.management.service;

import com.edu.school.management.entity.SchoolClassEntity;
import com.edu.school.management.entity.SubjectEntity;
import com.edu.school.management.repository.SchoolClassRepository;
import com.edu.school.management.repository.SubjectRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepo;
    private final SchoolClassRepository classRepo;

    public SubjectEntity addClassToSubject(Long subjectId, Long classId) {
        SubjectEntity subject = subjectRepo.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        SchoolClassEntity clazz = classRepo.findById(classId)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        List<SchoolClassEntity> currentClasses = subject.getClasses();
        if (!currentClasses.contains(clazz)) {
            currentClasses.add(clazz);
            subject.setClasses(currentClasses);
        }

        return subjectRepo.save(subject);
    }
    
    public SubjectEntity createOrUpdateSubject(SubjectEntity subject) {
        List<SchoolClassEntity> validClasses = new ArrayList<>();
        if (subject.getClasses() != null) {
            for (SchoolClassEntity cls : subject.getClasses()) {
                SchoolClassEntity found = classRepo.findById(cls.getClassId())
                        .orElseThrow(() -> new EntityNotFoundException("Class not found: " + cls.getClassId()));
                validClasses.add(found);
            }
        }
        subject.setClasses(validClasses);
        return subjectRepo.save(subject);
    }

    public List<SubjectEntity> getAllSubjectsWithClasses() {
        return subjectRepo.findAll(); // JPA auto-fetches ManyToMany unless LAZY is set
    }

}
