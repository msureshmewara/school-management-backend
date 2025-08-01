package com.edu.school.management.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.school.management.dto.StudentDocDTO;
import com.edu.school.management.dto.StudentFeeResponse;
import com.edu.school.management.dto.StudentFeeResponse.FeeDetail;
import com.edu.school.management.dto.StudentFeesDTO;
import com.edu.school.management.dto.StudentFamilyDTO;
import com.edu.school.management.dto.StudentFullResponseDTO;
import com.edu.school.management.dto.StudentPhotoDTO;
import com.edu.school.management.dto.StudentRequestDTO;
import com.edu.school.management.dto.StudentSiblingDTO;
import com.edu.school.management.dto.StudentSummaryDTO;
import com.edu.school.management.entity.*;
import com.edu.school.management.repository.RoleRepository;
import com.edu.school.management.repository.SchoolClassRepository;
import com.edu.school.management.repository.SchoolRepository;
import com.edu.school.management.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

	@Autowired private StudentRepository studentRepo;
    @Autowired private RoleRepository roleRepo;
    @Autowired private SchoolRepository schoolRepo;
    @Autowired private SchoolClassRepository classRepo;

    public StudentEntity createStudent(StudentRequestDTO dto) {
    	SchoolEntity school = schoolRepo.findById(dto.getSchoolId())
    		    .orElseThrow(() -> new RuntimeException("School not found"));

    		RoleEntity role = roleRepo.findById(dto.getRoleId())
    		    .orElseThrow(() -> new RuntimeException("Role not found"));

    		SchoolClassEntity schoolClass = classRepo.findById(dto.getClassId())
    		    .orElseThrow(() -> new RuntimeException("Class not found"));

        StudentEntity student = StudentEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .gender(dto.getGender())
                .rollNumber(dto.getRollNumber())
                .scholarNumber(dto.getScholarNumber())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .contactNumber(dto.getContactNumber())
                .dOB(dto.getDob())
                .address(dto.getAddress())
                .caste(dto.getCaste())
                .religion(dto.getReligion())
                .nationality(dto.getNationality())
                .motherToungue(dto.getMotherToungue())
                .medicalHistory(dto.getMedicalHistory())
                .apaarId(dto.getApaarId())
                .prevSchool(dto.getPrevSchool())
                .prevEduBoard(dto.getPrevEduBoard())
                .registrationNumber(dto.getRegistrationNumber())
                .enrollmentNumber(dto.getEnrollmentNumber())
                .bloodGroup(dto.getBloodGroup())
                .city(dto.getCity())
                .state(dto.getState())
                .pinCode(dto.getPinCode())
                .country(dto.getCountry())
                .status(dto.getStatus())
                .totalFees(dto.getTotalFees())
                .feesDiscount(dto.getFeesDiscount())
                .createdBy(dto.getCreatedBy())
                .className(dto.getClassName())
                .isDisable(dto.getIsDisable())
                .sssmidNum(dto.getSssmidNum())
                .aadharCardNum(dto.getAadharCardNum())
                .rationCardNum(dto.getRationCardNum())
                .admissionFormNumber(dto.getAdmissionFormNumber())
                .disabilityType(dto.getDisabilityType())
                .currentEduBoard(dto.getCurrentEduBoard())
                .school(school)
                .role(role)
                .schoolClass(schoolClass)
                .build();

        // Set parent reference to child entities
        if (dto.getFees() != null) {
            List<StudentFeesEntity> fees = dto.getFees().stream().map(f -> {
                StudentFeesEntity fee = new StudentFeesEntity();
                fee.setStudent(student);
                fee.setTotalFees(f.getTotalFees());
                fee.setPaymentDate(f.getPaymentDate());
                fee.setPaymentMode(f.getPaymentMode());
                fee.setPaidAmount(f.getPaidAmount());
                fee.setStatus(f.getStatus());
                fee.setCreatedAt(LocalDateTime.now());
                fee.setUpdatedAt(LocalDateTime.now());
                return fee;
            }).toList();
            student.setFees(fees);
        }

        if (dto.getFamily() != null) {
            List<StudentFamilyEntity> families = dto.getFamily().stream().map(f -> {
                StudentFamilyEntity fam = new StudentFamilyEntity();
                fam.setStudent(student);
                fam.setFatherName(f.getFatherName());
                fam.setFatherPhone(f.getFatherPhone());
                fam.setFatherEmail(f.getFatherEmail());
                fam.setMotherName(f.getMotherName());
                fam.setMotherPhone(f.getMotherPhone());
                fam.setCreatedBy(f.getCreatedBy());
                return fam;
            }).toList();
            student.setFamily(families);
        }

        if (dto.getDocuments() != null) {
            List<StudentDocEntity> docs = dto.getDocuments().stream().map(d -> {
                StudentDocEntity doc = new StudentDocEntity();
                doc.setStudent(student);
               
                return doc;
            }).toList();
            student.setDocuments(docs);
        }

        if (dto.getPhotos() != null) {
            StudentPhotoEntity photo = new StudentPhotoEntity();
            photo.setStudent(student);
            photo.setCreatedBy(dto.getPhotos().getCreatedBy());
            photo.setCreatedAt(LocalDateTime.now());
            photo.setUpdatedAt(LocalDateTime.now());
            student.setPhotos(photo);
            
        }

        return studentRepo.save(student);
    }

    // ✅ Fetch all students
    public List<StudentEntity> getAllStudents() {
        return studentRepo.findAll();
    }

    // ✅ Update basic student info
    public Optional<StudentEntity> updateStudent(Long id, StudentEntity updatedUser) {
        RoleEntity role = roleRepo.findById(updatedUser.getRole().getRoleId())
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        SchoolClassEntity clazz = classRepo.findById(updatedUser.getSchoolClass().getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        return studentRepo.findById(id).map(existing -> {
            existing.setUsername(updatedUser.getUsername());
            existing.setPassword(updatedUser.getPassword());
            existing.setGender(updatedUser.getGender());
            existing.setRollNumber(updatedUser.getRollNumber());
            existing.setScholarNumber(updatedUser.getScholarNumber());
            existing.setClassName(updatedUser.getClassName());
            existing.setSchoolClass(clazz);
            existing.setRole(role);
            existing.setFirstName(updatedUser.getFirstName());
            existing.setLastName(updatedUser.getLastName());
            existing.setContactNumber(updatedUser.getContactNumber());
            existing.setDOB(updatedUser.getDOB());
            existing.setAddress(updatedUser.getAddress());
            existing.setCaste(updatedUser.getCaste());
            existing.setReligion(updatedUser.getReligion());
            existing.setNationality(updatedUser.getNationality());
            existing.setMotherToungue(updatedUser.getMotherToungue());
            existing.setMedicalHistory(updatedUser.getMedicalHistory());
            existing.setApaarId(updatedUser.getApaarId());
            existing.setPrevSchool(updatedUser.getPrevSchool());
            existing.setPrevEduBoard(updatedUser.getPrevEduBoard());
            existing.setRegistrationNumber(updatedUser.getRegistrationNumber());
            existing.setEnrollmentNumber(updatedUser.getEnrollmentNumber());
            existing.setBloodGroup(updatedUser.getBloodGroup());
            existing.setCity(updatedUser.getCity());
            existing.setState(updatedUser.getState());
            existing.setPinCode(updatedUser.getPinCode());
            existing.setCountry(updatedUser.getCountry());
            existing.setStatus(updatedUser.getStatus());
            existing.setTotalFees(updatedUser.getTotalFees());
            existing.setFeesDiscount(updatedUser.getFeesDiscount());
            existing.setCreatedBy(updatedUser.getCreatedBy());
            existing.setIsDisable(updatedUser.getIsDisable());
            existing.setSssmidNum(updatedUser.getSssmidNum());
            existing.setAadharCardNum(updatedUser.getAadharCardNum());
            existing.setRationCardNum(updatedUser.getRationCardNum());
            existing.setAdmissionFormNumber(updatedUser.getAdmissionFormNumber());
            existing.setDisabilityType(updatedUser.getDisabilityType());
            existing.setCurrentEduBoard(updatedUser.getCurrentEduBoard());

            // --- ✅ Update fees ---
//            if (updatedUser.getFees() != null) {
//                existing.getFees().clear();
//                updatedUser.getFees().forEach(fee -> {
//                    fee.setStudent(existing);
//                    existing.getFees().add(fee);
//                });
//            }

            // --- ✅ Update family ---
            if (updatedUser.getFamily() != null) {
                existing.getFamily().clear();
                updatedUser.getFamily().forEach(fam -> {
                    fam.setStudent(existing);
                    existing.getFamily().add(fam);
                });
            }

            // --- ✅ Update documents ---
//            if (updatedUser.getDocuments() != null) {
//                existing.getDocuments().clear();
//                updatedUser.getDocuments().forEach(doc -> {
//                    doc.setStudent(existing);
//                    existing.getDocuments().add(doc);
//                });
//            }

            // --- ✅ Update photo ---
//            if (updatedUser.getPhotos() != null) {
//                updatedUser.getPhotos().setStudent(existing);
//                existing.setPhotos(updatedUser.getPhotos());
//            }

            // --- ✅ Update siblings ---
            if (updatedUser.getSiblings() != null) {
                existing.getSiblings().clear();
                updatedUser.getSiblings().forEach(sib -> {
                    sib.setStudent(existing);
                    existing.getSiblings().add(sib);
                });
            }

            return studentRepo.save(existing);
        });
    }



    // ✅ Delete student
    public void deleteUser(Long id) {
    	studentRepo.deleteById(id);
    }

    // ✅ Find student by username and password
    public Optional<StudentEntity> getUserByUsernameAndPassword(String username, String password) {
        return studentRepo.findByUsernameAndPassword(username, password);
    }

    // ✅ Get student and fee details by roll/class/section
    public StudentFeeResponse getStudentWithFees(Integer rollNumber, String className, String section) {
        StudentEntity student = studentRepo
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
        StudentEntity student = studentRepo.findById(id)
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
            dto.setRoleId(student.getRole().getRoleId());
        }

        // Class
        if (student.getSchoolClass() != null) {
            dto.setClassName(student.getSchoolClass().getClassName());
            dto.setSection(student.getSchoolClass().getSection());
            dto.setClassId(student.getSchoolClass().getClassId());
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
        return studentRepo.findBySchoolClass_ClassId(classId).stream()
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
        return studentRepo.findBySchoolClass_ClassIdAndRollNumber(classId, rollNumber)
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
        return studentRepo.findBySchoolClass_ClassIdAndRollNumber(classId, rollNumber);
    }

    public List<StudentSummaryDTO> getStudentsWithPendingFees() {
        return studentRepo.findAll().stream()
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
    public List<StudentEntity> getAllStudentsBySchoolId(Long schoolId) {
        return studentRepo.findAllBySchoolId(schoolId);
    }

}
