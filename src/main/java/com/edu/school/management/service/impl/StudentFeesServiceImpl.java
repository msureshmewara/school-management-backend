package com.edu.school.management.service.impl;

import com.edu.school.management.entity.StudentFeesEntity;
import com.edu.school.management.repository.StudentFeesRepository;
import com.edu.school.management.service.StudentFeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentFeesServiceImpl implements StudentFeesService {

    private final StudentFeesRepository repository;

    @Override
    public StudentFeesEntity create(StudentFeesEntity studentFees) {
        return repository.save(studentFees);
    }

    @Override
    public StudentFeesEntity update(Long id, StudentFeesEntity updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setTotalFees(updated.getTotalFees());
                    existing.setPaymentDate(updated.getPaymentDate());
                    existing.setPaymentMode(updated.getPaymentMode());
                    existing.setPaymentRefNum(updated.getPaymentRefNum());
                    existing.setReceivedBy(updated.getReceivedBy());
                    existing.setStatus(updated.getStatus());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Fees record not found with ID: " + id));
    }

    @Override
    public Optional<StudentFeesEntity> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<StudentFeesEntity> getByStudentId(Long studentId) {
        return repository.findByStudentStudentPin(studentId);
    }

    @Override
    public List<StudentFeesEntity> getAll() {
        return repository.findAll();
    }
}
