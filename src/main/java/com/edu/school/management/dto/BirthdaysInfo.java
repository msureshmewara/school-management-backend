package com.edu.school.management.dto;

import lombok.Data;
import java.util.List;

@Data
public class BirthdaysInfo {
    private long count;
    private List<PersonDTO> students; // or teachers
}
