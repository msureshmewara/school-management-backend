package com.edu.school.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.edu.school.management.dto.StudentDocDTO;
import com.edu.school.management.dto.StudentFeeResponse;
import com.edu.school.management.dto.StudentFeeResponse.FeeDetail;
import com.edu.school.management.dto.StudentFeesDTO;
import com.edu.school.management.dto.StudentFamilyDTO;
import com.edu.school.management.dto.StudentFullResponseDTO;
import com.edu.school.management.dto.StudentPhotoDTO;
import com.edu.school.management.dto.StudentSiblingDTO;
import com.edu.school.management.dto.StudentSummaryDTO;
import com.edu.school.management.entity.*;
import com.edu.school.management.repository.RoleRepository;
import com.edu.school.management.repository.SchoolClassRepository;
import com.edu.school.management.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepo;
    private final SchoolClassRepository classRepo;

    // ✅ Save Student with all nested data
    public StudentEntity createUser(StudentEntity student) {
        RoleEntity role = roleRepo.findById(student.getRole().getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        SchoolClassEntity clazz = classRepo.findById(student.getSchoolClass().getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        student.setRole(role);
        student.setSchoolClass(clazz);

        // Set bidirectional links
        if (student.getFees() != null) {
            student.getFees().forEach(f -> f.setStudent(student));
        }

        if (student.getFamily() != null) {
            student.getFamily().forEach(f -> f.setStudent(student));
        }

        if (student.getDocuments() != null) {
            student.getDocuments().forEach(doc -> doc.setStudent(student));
        }

        if (student.getPhotos() != null) {
            student.getPhotos().setStudent(student);
        }

        return studentRepository.save(student);
    }

    // ✅ Fetch all students
    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    // ✅ Update basic student info
    public Optional<StudentEntity> updateUser(Long id, StudentEntity updatedUser) {
        return studentRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return studentRepository.save(user);
        });
    }

    // ✅ Delete student
    public void deleteUser(Long id) {
        studentRepository.deleteById(id);
    }

    // ✅ Find student by username and password
    public Optional<StudentEntity> getUserByUsernameAndPassword(String username, String password) {
        return studentRepository.findByUsernameAndPassword(username, password);
    }

    // ✅ Get student and fee details by roll/class/section
    public StudentFeeResponse getStudentWithFees(Integer rollNumber, String className, String section) {
        StudentEntity student = studentRepository
                .findByRollNumberAndSchoolClass_ClassNameAndSchoolClass_Section(rollNumber, className, section)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentFeeResponse response = new StudentFeeResponse();
        response.setStudentPin(student.getStudentPin());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());

        if (student.getSchoolClass() != null) {
            response.setStu_class(student.getSchoolClass().getClassName());
            response.setSection(student.getSchoolClass().getSection());
        }

        response.setContactNumber(student.getContactNumber());
        response.setGender(student.getGender());
        response.setAddress(student.getAddress());
        response.setCity(student.getCity());
        response.setState(student.getState());
        response.setCountry(student.getCountry());
        response.setStatus(student.getStatus());

        response.setFees(student.getFees().stream().map(fee -> {
            FeeDetail detail = new FeeDetail();
            detail.setFeesId(fee.getFeesId());
            detail.setTotalFees(fee.getTotalFees());
            detail.setPaymentDate(fee.getPaymentDate());
            detail.setPaymentMode(fee.getPaymentMode());
            detail.setPaymentRefNum(fee.getPaymentRefNum());
            detail.setReceivedBy(fee.getReceivedBy());
            detail.setStatus(fee.getStatus());
            return detail;
        }).collect(Collectors.toList()));

        return response;
    }

    // ✅ Get full student details including family, fees, photos, and multiple documents
    public StudentFullResponseDTO getFullStudentDetails(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentFullResponseDTO dto = new StudentFullResponseDTO();
        dto.setStudentPin(student.getStudentPin());
        dto.setUsername(student.getUsername());
        dto.setPassword(student.getPassword());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setGender(student.getGender());
        dto.setContactNumber(student.getContactNumber());
        dto.setDOB(student.getDOB());
        dto.setAddress(student.getAddress());
        dto.setCity(student.getCity());
        dto.setState(student.getState());
        dto.setPinCode(student.getPinCode());
        dto.setCountry(student.getCountry());
        dto.setStatus(student.getStatus());
        dto.setRollNumber(student.getRollNumber());
        dto.setScholarNumber(dto.getScholarNumber());
        dto.setFeesDiscount(student.getFeesDiscount());
        dto.setTotalFees(student.getTotalFees());
        dto.setIsDisable(student.getIsDisable());
        dto.setSssmidNum(dto.getSssmidNum());
        dto.setAadharCardNum(student.getAadharCardNum());
        dto.setRationCardNum(student.getRationCardNum());
        dto.setAdmissionFormNumber(student.getAdmissionFormNumber());
        dto.setDisabilityType(student.getDisabilityType());
        dto.setCurrentEduBoard(student.getCurrentEduBoard());
        dto.setScholarNumber(student.getScholarNumber());
        dto.setCaste(student.getCaste());
        dto.setReligion(student.getReligion());
        dto.setNationality(student.getNationality());
        dto.setMotherToungue(student.getMotherToungue());
        dto.setMedicalHistory(student.getMedicalHistory());
        dto.setApaarId(student.getApaarId());
        dto.setPrevEduBoard(student.getPrevEduBoard());
        dto.setRegistrationNumber(student.getRegistrationNumber());
        dto.setEnrollmentNumber(student.getEnrollmentNumber());
        dto.setBloodGroup(student.getBloodGroup());
        dto.setPrevSchool(student.getPrevSchool());
        // Role
        if (student.getRole() != null) {
            dto.setRoleTitle(student.getRole().getTitle());
        }

        // Class
        if (student.getSchoolClass() != null) {
            dto.setClassName(student.getSchoolClass().getClassName());
            dto.setSection(student.getSchoolClass().getSection());
        }

        // Family
        dto.setFamily(student.getFamily().stream().map(f -> {
            StudentFamilyDTO fam = new StudentFamilyDTO();
            fam.setFatherName(f.getFatherName());
            fam.setMotherName(f.getMotherName());
            fam.setFatherPhone(f.getFatherPhone());
            fam.setMotherPhone(f.getMotherPhone());
            fam.setFatherAadharNum(f.getFatherAadharNum());
            fam.setMotherAadharNum(f.getMotherAadharNum());
            fam.setGuardianName(f.getGuardianName());
            fam.setGuardianPhone(f.getGuardianPhone());
            fam.setFatherOccupation(f.getFatherOccupation());
            fam.setMotherOccupation(f.getMotherOccupation());
            fam.setGuardianOccupation(f.getGuardianOccupation());
            fam.setFatherEmail(f.getFatherEmail());
            fam.setMotherEmail(f.getMotherEmail());
            fam.setGuardianEmail(f.getGuardianEmail());
            fam.setFatherEducation(f.getFatherEducation());
            fam.setMotherEducation(f.getMotherEducation());
            fam.setGuardianEducation(f.getGuardianEducation());
            fam.setIsSibling(f.getIsSibling());
            
            return fam;
        }).toList());

        if (student.getSiblings() != null) {
            dto.setSiblings(student.getSiblings().stream()
                    .map(this::mapSiblingToDTO)
                    .toList());
        }
        // Fees
        dto.setFees(student.getFees().stream().map(fee -> {
            StudentFeesDTO feeDto = new StudentFeesDTO();
            feeDto.setFeesId(fee.getFeesId());
            feeDto.setTotalFees(fee.getTotalFees());
            feeDto.setPaymentDate(fee.getPaymentDate());
            feeDto.setPaymentMode(fee.getPaymentMode());
            feeDto.setPaidAmount(fee.getPaidAmount());
            feeDto.setStatus(fee.getStatus());
            return feeDto;
        }).toList());

        // Documents (support multiple)
        if (student.getDocuments() != null && !student.getDocuments().isEmpty()) {
            List<StudentDocDTO> docDTOList = student.getDocuments().stream().map(doc -> {
                StudentDocDTO dtoDoc = new StudentDocDTO();
                
                dtoDoc.setDocType(doc.getDocType());
                dtoDoc.setFileName(doc.getFileName());
                dtoDoc.setFilePath(doc.getFilePath());
                dtoDoc.setContentType(doc.getContentType());
                return dtoDoc;
            }).toList();
            dto.setDocumentsList(docDTOList);
        }


        // Photos
        if (student.getPhotos() != null) {
            StudentPhotoDTO photo = new StudentPhotoDTO();
            photo.setStudentPhoto(student.getPhotos().getStudentPhoto());
            photo.setFatherPhoto(student.getPhotos().getFatherPhoto());
            photo.setMotherPhoto(student.getPhotos().getMotherPhoto());
            photo.setGuardianPhoto(student.getPhotos().getGuardianPhoto());
            dto.setPhotos(photo);
        }

        return dto;
    }
    public List<StudentSummaryDTO> getSummariesByClass(Long classId) {
        return studentRepository.findBySchoolClass_ClassId(classId).stream()
                .map(this::mapToSummary)
                .toList();
    }

    private StudentSiblingDTO mapSiblingToDTO(StudentSiblingEntity sibling) {
        StudentSiblingDTO dto = new StudentSiblingDTO();
        dto.setId(sibling.getId());
        dto.setSiblingName(sibling.getSiblingName());
        dto.setSiblingClass(sibling.getSiblingClass());
        dto.setSiblingSchool(sibling.getSiblingSchool());
        dto.setContactNumber(sibling.getContactNumber());
        dto.setRelationship(sibling.getRelationship());
        return dto;
    }


    public Optional<StudentSummaryDTO> getSummaryByClassAndRoll(Long classId, Integer rollNumber) {
        return studentRepository.findBySchoolClass_ClassIdAndRollNumber(classId, rollNumber)
                .map(this::mapToSummary);
    }

    private StudentSummaryDTO mapToSummary(StudentEntity student) {
        double totalPaid = student.getFees() != null
                ? student.getFees().stream()
                        .mapToDouble(fee -> fee.getPaidAmount() != null ? fee.getPaidAmount() : 0.0)
                        .sum()
                : 0.0;

        double totalFee = student.getTotalFees() != null ? student.getTotalFees() : 0.0;
        double dueAmount = totalFee - totalPaid;

//        return new StudentSummaryDTO(
//                student.getStudentPin(),
//                student.getFirstName() + " " + student.getLastName(),
//                student.getRollNumber(),
//                student.getScholarNumber(),
//                student.getSchoolClass().getClassName() + " - " + student.getSchoolClass().getSection(),
//                totalFee,
//                totalPaid,
//                dueAmount
//        );
      return new StudentSummaryDTO(student.getStudentPin(),student.getFirstName() + " " + student.getLastName(),student.getFirstName(),
		student.getRollNumber(), student.getScholarNumber(),
		student.getSchoolClass().getClassName()+" - "+ student.getSchoolClass().getSection(), totalFee,
		totalPaid, dueAmount);
    }

//    private StudentSummaryDTO mapToSummary(StudentEntity student) {
//    // 1. Full Name
//    String fullName = (student.getFirstName() != null ? student.getFirstName() : "") +
//                      " " +
//                      (student.getLastName() != null ? student.getLastName() : "");
//
//    // 3. Total Paid
//    double totalPaid = student.getFees() != null
//            ? student.getFees().stream()
//                    .mapToDouble(fee -> fee.getPaidAmount() != null ? fee.getPaidAmount() : 0.0)
//                    .sum()
//            : 0.0;
//
//    // 4. Total Fee
//    double totalFee = student.getFees() != null
//            ? student.getFees().stream()
//                    .mapToDouble(fee -> fee.getTotalFees() != null ? fee.getTotalFees() : 0.0)
//                    .sum()
//            : 0.0;
//
//    // 5. Pending Fee
//    double pending = totalFee - totalPaid;
//    
//   
//       String fatherName = student.getFamily() != null && !student.getFamily().isEmpty()
//               ? student.getFamily().get(0).getFatherName()
//               : "N/A";
//    return new StudentSummaryDTO(student.getStudentPin(),fullName.trim(),fatherName,
//    		student.getRollNumber(), student.getScholarNumber(),
//    		student.getSchoolClass().getClassName()+" - "+ student.getSchoolClass().getSection(), totalFee,
//    		totalPaid, pending);
//}

    public Optional<StudentEntity> findBySchoolClass_ClassIdAndRollNumber(Long classId, Integer rollNumber) {
        return studentRepository.findBySchoolClass_ClassIdAndRollNumber(classId, rollNumber);
    }

    public List<StudentSummaryDTO> getStudentsWithPendingFees() {
        return studentRepository.findAll().stream()
                .filter(student -> {
                    double totalPaid = student.getFees() != null
                            ? student.getFees().stream()
                                    .mapToDouble(fee -> fee.getPaidAmount() != null ? fee.getPaidAmount() : 0.0)
                                    .sum()
                            : 0.0;

                    double totalFee = student.getTotalFees() != null ? student.getTotalFees() : 0.0;

                    double dueAmount = totalFee - totalPaid;

                    return dueAmount > 0;
                })
                .map(this::mapToSummary)
                .toList();
    }

}
