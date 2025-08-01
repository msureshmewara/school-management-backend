package com.edu.school.management.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "schools")
@Getter
@Setter
public class SchoolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolName;
    private String registrationNumber;
    private String schoolCode;
    private String boardAffiliation;
    private String affiliationNumber;
    private String establishedYear;
    private String type;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private String state;
    private String pinCode;
    private String country;

    private String contactNumber;
    private String email;
    private String website;
    private String principalName;
    private String principalContact;

    private String logoUrl;
    private String schoolMotto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate validUpTo; // or Date validUpTo; 
}
