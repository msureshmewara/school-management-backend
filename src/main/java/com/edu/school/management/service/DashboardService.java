package com.edu.school.management.service;


import com.edu.school.management.dto.*;
import com.edu.school.management.entity.*;
import com.edu.school.management.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

        Double sumTotalFeesMinusDiscount = studentRepository.sumTotalFeesMinusDiscount();
        Double sumPaidFees = feesRepository.sumPaidAmount();

        double totalPendingFees = (sumTotalFeesMinusDiscount == null ? 0 : sumTotalFeesMinusDiscount)
                - (sumPaidFees == null ? 0 : sumPaidFees);

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

    public DashboardSummaryDTO getDashboardSummary() {
        DashboardSummaryDTO dto = new DashboardSummaryDTO();

        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        // Total students with IDs
        CountWithIds totalStudents = new CountWithIds();
        totalStudents.setCount(studentRepository.count());
        totalStudents.setIds(studentRepository.findAllIds());
        dto.setTotalStudents(totalStudents);

        // Student birthdays today info
        List<StudentEntity> birthdayStudentEntities = studentRepository.findBirthdaysToday(month, day);
        List<PersonDTO> birthdayStudents = birthdayStudentEntities.stream()
            .map(student -> {
                PersonDTO p = new PersonDTO();
                p.setId(student.getStudentPin());
                p.setName(student.getFirstName() + " " + student.getLastName());
                p.setClassName(student.getClassName());
                p.setDob(student.getDOB().toString());
                return p;
            }).collect(Collectors.toList());

        BirthdaysInfo studentBirthdays = new BirthdaysInfo();
        studentBirthdays.setCount(birthdayStudents.size());
        studentBirthdays.setStudents(birthdayStudents);
        dto.setStudentBirthdaysToday(studentBirthdays);

        // Teacher birthdays today info
        List<UserEntity> birthdayTeacherEntities = userRepository.findTeacherBirthdaysToday(month, day);
        List<PersonDTO> birthdayTeachers = birthdayTeacherEntities.stream()
            .map(teacher -> {
                PersonDTO p = new PersonDTO();
                p.setId(teacher.getId());
                p.setName(teacher.getFirstName() + " " + teacher.getLastName());
                p.setClassName(null); // Teachers may not have a className, or you can set their department
                p.setDob(teacher.getDOB().toString());
                return p;
            }).collect(Collectors.toList());

        BirthdaysInfo teacherBirthdays = new BirthdaysInfo();
        teacherBirthdays.setCount(birthdayTeachers.size());
        teacherBirthdays.setStudents(birthdayTeachers);
        dto.setTeacherBirthdaysToday(teacherBirthdays);

        // Fees collected today info
        FeesCollectionInfo feesInfo = new FeesCollectionInfo();
        double amountCollected = feesRepository.sumPaidAmountByPaymentDate(today);
        feesInfo.setAmount(amountCollected);

        List<StudentFeesEntity> transactionsToday = feesRepository.findTransactionsByPaymentDate(today);
        List<FeeTransactionDTO> feeTransactionDTOs = transactionsToday.stream()
            .map(tx -> {
                FeeTransactionDTO dtoTx = new FeeTransactionDTO();
                dtoTx.setTransactionId(tx.getFeesId());
                dtoTx.setStudentId(tx.getStudent().getStudentPin());
                dtoTx.setStudentName(tx.getStudent().getFirstName() + " " + tx.getStudent().getLastName());
                dtoTx.setClassName(tx.getStudent().getClassName());
                dtoTx.setAmountPaid(tx.getPaidAmount());
                dtoTx.setPaymentDate(tx.getPaymentDate().toString());
                dtoTx.setPaymentMode(tx.getPaymentMode());
                return dtoTx;
            }).collect(Collectors.toList());
        feesInfo.setTransactions(feeTransactionDTOs);
        dto.setTotalFeesCollectedToday(feesInfo);

        // Pending fees info
        Double sumTotalFeesMinusDiscount = studentRepository.sumTotalFeesMinusDiscount();
        Double sumPaidFees = feesRepository.sumPaidAmount();

        double totalPending = (sumTotalFeesMinusDiscount == null ? 0 : sumTotalFeesMinusDiscount)
                - (sumPaidFees == null ? 0 : sumPaidFees);

        PendingFeesInfo pendingFeesInfo = new PendingFeesInfo();
        pendingFeesInfo.setAmount(totalPending);

        // You might want a query to fetch students with pending fees and amounts:
        List<StudentEntity> allStudents = studentRepository.findAll();

        List<PendingStudentFeeDTO> pendingStudents = allStudents.stream()
            .map(student -> {
                double pendingAmount = (student.getTotalFees() - student.getFeesDiscount()) - 
                        feesRepository.sumPaidAmountByStudent(student.getStudentPin());
                if (pendingAmount > 0) {
                    PendingStudentFeeDTO p = new PendingStudentFeeDTO();
                    p.setId(student.getStudentPin());
                    p.setName(student.getFirstName() + " " + student.getLastName());
                    p.setClassName(student.getClassName());
                    p.setPendingAmount(pendingAmount);
                    return p;
                }
                return null;
            }).filter(p -> p != null).collect(Collectors.toList());

        pendingFeesInfo.setStudents(pendingStudents);
        dto.setTotalPendingFees(pendingFeesInfo);

        // Today's absent teachers
        CountWithDetails absentTeachers = new CountWithDetails();
        absentTeachers.setCount(attendanceRepository.countByDateAndIsPresentFalse(today));
        List<Long> absentTeacherIds = attendanceRepository.findAbsentTeacherIdsByDate(today);
        absentTeachers.setIds(absentTeacherIds);

        // You could map teacher details if needed, else just ids
        dto.setTodaysAbsentTeachers(absentTeachers);

        // Total teachers with IDs
        CountWithIds totalTeachers = new CountWithIds();
        totalTeachers.setCount(userRepository.countByRoleTitle("TEACHER"));
        totalTeachers.setIds(userRepository.findAllTeacherIds()); // You have to add this method in UserRepository
        dto.setTotalTeachers(totalTeachers);

        return dto;
    }
}
