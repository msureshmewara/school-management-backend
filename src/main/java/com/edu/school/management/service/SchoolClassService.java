package com.edu.school.management.service;

import com.edu.school.management.dto.ClassDetailsDTO;
import com.edu.school.management.dto.SchoolClassDTO;
import com.edu.school.management.dto.StudentSummDTO;
import com.edu.school.management.dto.SubjectDTO;
import com.edu.school.management.entity.SchoolClassEntity;
import com.edu.school.management.entity.StudentEntity;
import com.edu.school.management.entity.SubjectEntity;
import com.edu.school.management.repository.SchoolClassRepository;
import com.edu.school.management.repository.StudentRepository;
import com.edu.school.management.repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolClassService {

	private final SchoolClassRepository classRepo;
    private final StudentRepository studentRepo;
    private final SubjectRepository subjectRepo;

    public SchoolClassEntity createClass(SchoolClassEntity schoolClass) {
        return classRepo.save(schoolClass);
    }

    public List<SchoolClassEntity> getAllClasses() {
        return classRepo.findAll();
    }

    public Optional<SchoolClassEntity> getClassById(Long id) {
        return classRepo.findById(id);
    }

    public void deleteClass(Long id) {
    	classRepo.deleteById(id);
    }
    
    public ClassDetailsDTO getClassDetails(Long classId) {
        var cls = classRepo.findById(classId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));

        List<StudentSummDTO> students = studentRepo.findBySchoolClass_ClassId(classId)
            .stream()
            .map(s -> new StudentSummDTO(s.getStudentPin(), s.getUsername(), s.getFirstName(), s.getLastName(), s.getRollNumber()))
            .toList();

        List<SubjectDTO> subjects = subjectRepo.findAllByClassId(classId)
            .stream()
            .map(s -> new SubjectDTO(
                s.getSubjectId(), 
                s.getTitle(), 
                s.getTotalTheoryMarks(), 
                s.getPassingTheoryMarks(),
                s.getTotalInternalMarks(), 
                s.getPassingInternalMarks()
            ))
            .toList();

        SchoolClassDTO classDto = new SchoolClassDTO(cls.getClassId(), cls.getClassName(), cls.getSection());
        return new ClassDetailsDTO(classDto, students, subjects);
    }

}
