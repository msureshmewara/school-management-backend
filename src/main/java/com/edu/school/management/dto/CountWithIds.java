package com.edu.school.management.dto;

import lombok.Data;
import java.util.List;

@Data
public class CountWithIds {
    private long count;
    private List<Long> ids;
}
