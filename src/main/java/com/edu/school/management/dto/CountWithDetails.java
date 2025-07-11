package com.edu.school.management.dto;

import lombok.Data;
import java.util.List;

@Data
public class CountWithDetails {
    private long count;
    private List<PersonDTO> teachers;
    private List<Long> ids;
}
