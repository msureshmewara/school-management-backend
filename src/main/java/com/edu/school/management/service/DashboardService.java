package com.edu.school.management.service;

import com.edu.school.management.dto.DashboardStatsDTO;
import com.edu.school.management.entity.*;
import com.edu.school.management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentFeesRepository feesRepository;
    private final TeacherAttendanceRepository attendanceRepository;

    public DashboardStatsDTO getStats() {
        LocalDate today = LocalDate.now();

        long totalStudents = studentRepository.count();

        long studentBirthdaysToday = studentRepository.countByDOBMonthDay(today.getMonthValue(), today.getDayOfMonth());

        long teacherBirthdaysToday = userRepository.countTeachersWithBirthdayToday(today.getMonthValue(), today.getDayOfMonth());

        double totalFeesCollectedToday = feesRepository.sumPaidAmountByPaymentDate(today);

        double totalPendingFees = studentRepository.sumTotalFeesMinusDiscount()
                - feesRepository.sumPaidAmount();

        long todaysAbsentTeachers = attendanceRepository.countByDateAndIsPresentFalse(today);

        long totalTeachers = userRepository.countByRoleTitle("TEACHER");

        return DashboardStatsDTO.builder()
                .totalStudents(totalStudents)
                .studentBirthdaysToday(studentBirthdaysToday)
                .teacherBirthdaysToday(teacherBirthdaysToday)
                .totalFeesCollectedToday(totalFeesCollectedToday)
                .totalPendingFees(totalPendingFees)
                .todaysAbsentTeachers(todaysAbsentTeachers)
                .totalTeachers(totalTeachers)
                .build();
    }
}
