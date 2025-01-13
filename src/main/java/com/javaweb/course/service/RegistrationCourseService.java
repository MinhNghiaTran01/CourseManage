package com.javaweb.course.service;

import com.javaweb.course.entity.RegistrationCourse;
import com.javaweb.course.model.dto.RegistrationCourseDTO;

import java.util.List;

public interface RegistrationCourseService {
    Boolean save(RegistrationCourseDTO registrationCourseDTO);

    List<RegistrationCourse> findAll();

    RegistrationCourse findById(Integer id);

    RegistrationCourse findByCourseIdAndUserId(Integer courseId, Integer userId);

    List<RegistrationCourse> findByUserId(Integer userId);
}
