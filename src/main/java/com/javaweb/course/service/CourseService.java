package com.javaweb.course.service;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.CourseBenefit;
import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.model.respone.CourseWebResponse;

public interface CourseService extends BaseService<Course, CourseDto, CourseResponse> {

  CourseWebResponse getCourseById(Integer id);
}
