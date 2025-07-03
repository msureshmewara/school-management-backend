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
            student.getDocuments().setStudent(student);
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
    public StudentFeeResponse getStudentWithFees(String rollNumber, String className, String section) {
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
    
    public StudentFullResponseDTO getFullStudentDetails(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        StudentFullResponseDTO dto = new StudentFullResponseDTO();
        dto.setStudentPin(student.getStudentPin());
        dto.setUsername(student.getUsername());
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
            fam.setGuardianName(f.getGuardianName());
            fam.setFatherPhone(f.getFatherPhone());
            fam.setMotherPhone(f.getMotherPhone());
            fam.setGuardianPhone(f.getGuardianPhone());
            return fam;
        }).toList());

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

        // Documents
        if (student.getDocuments() != null) {
            StudentDocDTO doc = new StudentDocDTO();
            doc.setAadharCard(student.getDocuments().getAadharCard());
            doc.setPanCard(student.getDocuments().getPanCard());
            doc.setCasteCertificate(student.getDocuments().getCasteCertificate());
            doc.setAdmissionForm(student.getDocuments().getAdmissionForm());
            dto.setDocuments(doc);
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

}
