package com.edu.school.management.service;

import com.edu.school.management.entity.SchoolEntity;
import java.util.List;

public interface SchoolService {
    List<SchoolEntity> getAllSchools();
    SchoolEntity getSchoolById(Long id);
    SchoolEntity saveSchool(SchoolEntity school);
}
