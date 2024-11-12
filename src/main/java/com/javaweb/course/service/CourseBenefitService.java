package com.javaweb.course.service;

import com.javaweb.course.entity.CourseBenefit;
import com.javaweb.course.model.dto.CourseBenefitDTO;

import java.util.List;

public interface CourseBenefitService {
    List<CourseBenefit> findByCourseId(Integer courseId);

    Boolean save(CourseBenefitDTO courseBenefitDTO);
}
