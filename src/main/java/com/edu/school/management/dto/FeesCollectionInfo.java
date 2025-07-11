package com.edu.school.management.dto;

import lombok.Data;
import java.util.List;

@Data
public class FeesCollectionInfo {
    private double amount;
    private List<FeeTransactionDTO> transactions;
}
