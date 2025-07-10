package com.edu.school.management.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimetableResponseDTO {
    private Long teacherId;
    private String teacherName;
    private Boolean isTeacherPresent;

    private Long subjectId;
    private String subjectName;

    private Long classId;
    private String className;
    private String classSection;

    private String dayOfWeek;
    private Integer period;
    private String timeSlot;
}



