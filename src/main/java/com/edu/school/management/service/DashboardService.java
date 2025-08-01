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

    public DashboardSummaryDTO getDashboardSummary(Long schoolId) {
        DashboardSummaryDTO dto = new DashboardSummaryDTO();

        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        // Total students
        CountWithIds totalStudents = new CountWithIds();
        totalStudents.setCount(studentRepository.countBySchoolId(schoolId));
        totalStudents.setIds(studentRepository.findIdsBySchoolId(schoolId));
        dto.setTotalStudents(totalStudents);

        // Student birthdays today
        List<StudentEntity> birthdayStudentEntities = studentRepository.findBirthdaysTodayBySchoolId(schoolId, month, day);
        List<PersonDTO> birthdayStudents = birthdayStudentEntities.stream().map(student -> {
            String fatherName = student.getFamily() != null && !student.getFamily().isEmpty()
                    ? student.getFamily().get(0).getFatherName() : "N/A";
            String fatherPhone = student.getFamily() != null && !student.getFamily().isEmpty()
                    ? student.getFamily().get(0).getFatherPhone() : "N/A";
            String guardianName = student.getFamily() != null && !student.getFamily().isEmpty()
                    ? student.getFamily().get(0).getGuardianName() : "N/A";
            String guardianPhone = student.getFamily() != null && !student.getFamily().isEmpty()
                    ? student.getFamily().get(0).getGuardianPhone() : "N/A";

            PersonDTO p = new PersonDTO();
            p.setId(student.getStudentPin());
            p.setName(student.getFirstName() + " " + student.getLastName());
            p.setClassName(student.getClassName());
            p.setDob(student.getDOB().toString());
            p.setFatherName(fatherName);
            p.setFatherPhone(fatherPhone);
            p.setGuardianName(guardianName);
            p.setGuardianPhone(guardianPhone);
            return p;
        }).collect(Collectors.toList());

        BirthdaysInfo studentBirthdays = new BirthdaysInfo();
        studentBirthdays.setCount(birthdayStudents.size());
        studentBirthdays.setStudents(birthdayStudents);
        dto.setStudentBirthdaysToday(studentBirthdays);

        // Teacher birthdays today
        List<UserEntity> birthdayTeacherEntities = userRepository.findTeacherBirthdaysTodayBySchoolId(schoolId, month, day);
        List<PersonDTO> birthdayTeachers = birthdayTeacherEntities.stream().map(teacher -> {
            PersonDTO p = new PersonDTO();
            p.setId(teacher.getId());
            p.setName(teacher.getFirstName() + " " + teacher.getLastName());
            p.setClassName(null);
            p.setDob(teacher.getDOB().toString());
            p.setContactNumber(teacher.getContactNumber());
            return p;
        }).collect(Collectors.toList());

        BirthdaysInfo teacherBirthdays = new BirthdaysInfo();
        teacherBirthdays.setCount(birthdayTeachers.size());
        teacherBirthdays.setStudents(birthdayTeachers);
        dto.setTeacherBirthdaysToday(teacherBirthdays);

        // Fees collected today
        FeesCollectionInfo feesInfo = new FeesCollectionInfo();
        double amountCollected = feesRepository.sumPaidAmountByPaymentDateAndSchoolId(today, schoolId);
        feesInfo.setAmount(amountCollected);

        List<StudentFeesEntity> transactionsToday = feesRepository.findTransactionsByPaymentDateAndSchoolId(today, schoolId);
        List<FeeTransactionDTO> feeTransactionDTOs = transactionsToday.stream().map(tx -> {
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
        Double sumTotalFeesMinusDiscount = studentRepository.sumTotalFeesMinusDiscountBySchoolId(schoolId);
        Double sumPaidFees = feesRepository.sumPaidAmountBySchoolId(schoolId);

        double totalPending = (sumTotalFeesMinusDiscount == null ? 0 : sumTotalFeesMinusDiscount)
                - (sumPaidFees == null ? 0 : sumPaidFees);

        PendingFeesInfo pendingFeesInfo = new PendingFeesInfo();
        pendingFeesInfo.setAmount(totalPending);

        List<StudentEntity> allStudents = studentRepository.findAllBySchoolId(schoolId);
        List<PendingStudentFeeDTO> pendingStudents = allStudents.stream().map(student -> {
            double paid = feesRepository.sumPaidAmountByStudent(student.getStudentPin());
            double pendingAmount = (student.getTotalFees() - student.getFeesDiscount()) - paid;

            if (pendingAmount > 0) {
                String fatherName = student.getFamily() != null && !student.getFamily().isEmpty()
                        ? student.getFamily().get(0).getFatherName() : "N/A";
                String fatherPhone = student.getFamily() != null && !student.getFamily().isEmpty()
                        ? student.getFamily().get(0).getFatherPhone() : "N/A";
                String guardianName = student.getFamily() != null && !student.getFamily().isEmpty()
                        ? student.getFamily().get(0).getGuardianName() : "N/A";
                String guardianPhone = student.getFamily() != null && !student.getFamily().isEmpty()
                        ? student.getFamily().get(0).getGuardianPhone() : "N/A";

                PendingStudentFeeDTO p = new PendingStudentFeeDTO();
                p.setId(student.getStudentPin());
                p.setName(student.getFirstName() + " " + student.getLastName());
                p.setClassName(student.getClassName());
                p.setPendingAmount(pendingAmount);
                p.setFatherName(fatherName);
                p.setFatherPhone(fatherPhone);
                p.setGuardianName(guardianName);
                p.setGuardianPhone(guardianPhone);
                return p;
            }
            return null;
        }).filter(p -> p != null).collect(Collectors.toList());

        pendingFeesInfo.setStudents(pendingStudents);
        dto.setTotalPendingFees(pendingFeesInfo);

        // Today's absent teachers
        CountWithDetails absentTeachers = new CountWithDetails();
        absentTeachers.setCount(attendanceRepository.countByDateAndIsPresentFalseAndSchoolId(today, schoolId));
        List<Long> absentTeacherIds = attendanceRepository.findAbsentTeacherIdsByDateAndSchoolId(today, schoolId);
        absentTeachers.setIds(absentTeacherIds);
        dto.setTodaysAbsentTeachers(absentTeachers);

        // Total teachers
        CountWithIds totalTeachers = new CountWithIds();
        totalTeachers.setCount(userRepository.countByRoleTitleAndSchoolId("TEACHER", schoolId));
        totalTeachers.setIds(userRepository.findAllTeacherIdsBySchoolId(schoolId));
        dto.setTotalTeachers(totalTeachers);

        return dto;
    }
}
