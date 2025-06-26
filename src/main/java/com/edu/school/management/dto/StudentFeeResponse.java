package com.edu.school.management.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class StudentFeeResponse {
    private Long studentPin;
    private String firstName;
    private String lastName;
    private String stu_class;
    private String section;
    private String contactNumber;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String country;
    private String status;

    private List<FeeDetail> fees;

    @Data
    public static class FeeDetail {
        private Long feesId;
        private Double totalFees;
        private LocalDate paymentDate;
        private String paymentMode;
        private String paymentRefNum;
        private String receivedBy;
        private String status;
    }
}
