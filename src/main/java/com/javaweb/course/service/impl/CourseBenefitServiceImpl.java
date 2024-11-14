package com.javaweb.course.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.CourseBenefit;
import com.javaweb.course.model.dto.CourseBenefitDTO;
import com.javaweb.course.repository.CourseBenefitRepository;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.CourseBenefitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseBenefitServiceImpl implements CourseBenefitService {

    @Autowired
    private CourseBenefitRepository courseBenefitRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseBenefit> findByCourseId(Integer courseId) {
        return courseBenefitRepository.findByCourseId(courseId);
    }

    @Override
    public Boolean save(CourseBenefitDTO courseBenefitDTO) {
        Course course = courseRepository.findByCode(courseBenefitDTO.getCodeCourse());
        if (course == null) return false;
        try {
            CourseBenefit courseBenefit = new CourseBenefit();
            ObjectMapper objectMapper = new ObjectMapper();
            String benefitJson = objectMapper.writeValueAsString(courseBenefitDTO);
            courseBenefit.setBenefit(benefitJson);
            courseBenefit.setCourseId(course.getId());
            courseBenefitRepository.save(courseBenefit);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error saving course benefit", e);
        }
    }
}
