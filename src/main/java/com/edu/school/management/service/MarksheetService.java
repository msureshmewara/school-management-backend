package com.edu.school.management.service;

import com.edu.school.management.dto.MarksheetRequest;
import com.edu.school.management.entity.*;
import com.edu.school.management.repository.*;
import com.edu.school.management.util.MarksheetResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarksheetService {

    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final MarksheetRepository marksheetRepository;

    public MarksheetEntity createMarksheet(MarksheetRequest request) {
        StudentEntity student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        SchoolClassEntity schoolClass = schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new RuntimeException("Class not found"));

        MarksheetEntity marksheet = MarksheetEntity.builder()
                .academicYear(request.getAcademicYear())
                .grade(request.getGrade())
                .rollNumber(request.getRollNumber())
                .student(student)
                .schoolClass(schoolClass)
                .build();

        List<MarksheetSubjectEntity> subjectMarks = request.getSubjects().stream().map(subReq -> {
            SubjectEntity subject = subjectRepository.findById(subReq.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));

            return MarksheetSubjectEntity.builder()
                    .subject(subject)
                    .marksheet(marksheet)
                    .totalTheoryMarks(subReq.getTotalTheoryMarks())
                    .passingTheoryMarks(subReq.getPassingTheoryMarks())
                    .obtainedTheoryMarks(subReq.getObtainedTheoryMarks())
                    .totalInternalMarks(subReq.getTotalInternalMarks())
                    .passingInternalMarks(subReq.getPassingInternalMarks())
                    .obtainedInternalMarks(subReq.getObtainedInternalMarks())
                    .build();
        }).collect(Collectors.toList());

        marksheet.setSubjects(subjectMarks);

        return marksheetRepository.save(marksheet);
    }
    
    public MarksheetResult evaluateMarksheet(Long marksheetId) {
    	MarksheetEntity marksheet = marksheetRepository.findById(marksheetId)
                .orElseThrow(() -> new RuntimeException("Marksheet not found with id: " + marksheetId));

        StudentEntity student = marksheet.getStudent();
        if (student == null) {
            throw new RuntimeException("Student not associated with this marksheet");
        }

        MarksheetResult.StudentInfo studentInfo = new MarksheetResult.StudentInfo(
            student.getFirstName(),
            student.getLastName(),
            student.getDOB(),
            student.getRollNumber(),
            student.getScholarNumber(),
            student.getFamily() != null && !student.getFamily().isEmpty() ? student.getFamily().get(0).getFatherName() : null,
            student.getFamily() != null && !student.getFamily().isEmpty() ? student.getFamily().get(0).getMotherName() : null,
            student.getSchoolClass().getClassName(),
            student.getSchoolClass().getSection(),
            student.getEnrollmentNumber(),
            student.getCurrentEduBoard()
        );

        boolean overallPass = true;
        List<MarksheetResult.SubjectResult> subjectResults = new ArrayList<>();

        for (MarksheetSubjectEntity subjectMark : marksheet.getSubjects()) {
            boolean theoryPass = subjectMark.getObtainedTheoryMarks() >= subjectMark.getPassingTheoryMarks();
            boolean internalPass = true;

            if (subjectMark.getTotalInternalMarks() != null && subjectMark.getTotalInternalMarks() > 0) {
                Integer obtainedInternal = subjectMark.getObtainedInternalMarks();
                Integer passingInternal = subjectMark.getPassingInternalMarks();
                internalPass = obtainedInternal != null && obtainedInternal >= passingInternal;
            }

            boolean isPassed = theoryPass && internalPass;
            if (!isPassed) overallPass = false;

            subjectResults.add(new MarksheetResult.SubjectResult(
                subjectMark.getSubject().getTitle(),
                isPassed,
                subjectMark.getObtainedTheoryMarks(),
                subjectMark.getObtainedInternalMarks()
            ));
        }

        return new MarksheetResult(studentInfo, overallPass, subjectResults);
    }

}
