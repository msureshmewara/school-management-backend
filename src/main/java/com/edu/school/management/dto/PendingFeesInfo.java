package com.edu.school.management.dto;

import lombok.Data;
import java.util.List;

@Data
public class PendingFeesInfo {
    private double amount;
    private List<PendingStudentFeeDTO> students;
}
