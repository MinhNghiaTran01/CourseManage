package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.CourseService;
import com.javaweb.course.untils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void save(CourseDto courseDto) {
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setState(courseDto.getState());
        course.setCreatedAt(Helper.getNowMillisAtUtc());
        course.setUpdatedAt(Helper.getNowMillisAtUtc());

        courseRepository.save(course);
    }

    @Override
    public void update(Integer id, CourseDto courseDto) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setCourseName(courseDto.getCourseName());
            course.setCode(courseDto.getCode());
            course.setDescription(courseDto.getDescription());
            course.setPrice(courseDto.getPrice());
            course.setState(courseDto.getState());
            courseRepository.save(course);
        }
    }

    @Override
    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseResponse> findAll() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().map(CourseResponse::new).toList();
    }

    @Override
    public CourseResponse findById(Integer id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new RuntimeException(String.format("course id: %s is null",id));
        }
        return new CourseResponse(course);
    }
}
