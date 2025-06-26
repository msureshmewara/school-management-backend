package com.edu.school.management.service;

import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import com.edu.school.management.dto.StudentFeeResponse;
import com.edu.school.management.dto.StudentFeeResponse.FeeDetail;
import com.edu.school.management.entity.StudentEntity;
import com.edu.school.management.repository.StudentRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentEntity createUser(StudentEntity user) {
        return studentRepository.save(user);
    }

    public List<StudentEntity> getAllUsers() {
        return studentRepository.findAll();
    }


    public Optional<StudentEntity> updateUser(Long id, StudentEntity updatedUser) {
        return studentRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            return studentRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        studentRepository.deleteById(id);
    }

	public Optional<StudentEntity> getUserByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return studentRepository.findByUsernameAndPassword(username, password);
	}
	
	 public StudentFeeResponse getStudentWithFees(String rollNumber, String stuClass, String section) {
	        StudentEntity student = studentRepository
	                .findByRollNumberAndStuClassAndSection(rollNumber, stuClass, section)
	                .orElseThrow(() -> new RuntimeException("Student not found"));

	        StudentFeeResponse response = new StudentFeeResponse();
	        response.setStudentPin(student.getStudentPin());
	        response.setFirstName(student.getFirstName());
	        response.setLastName(student.getLastName());
	        response.setStu_class(student.getStu_class());
	        response.setSection(student.getSection());
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
}
