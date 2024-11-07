package com.javaweb.course.service;

import com.javaweb.course.entity.Course;
import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;

import java.io.IOException;
import java.util.List;

public interface CourseService extends BaseService<Course, CourseDto, CourseResponse> {

    void updateAndImage(CourseDto courseDto) throws IOException;


    List<CourseResponse> findAll();

    List<Course> findByCourseNameLikeIgnoreCase(String courseName);

}
