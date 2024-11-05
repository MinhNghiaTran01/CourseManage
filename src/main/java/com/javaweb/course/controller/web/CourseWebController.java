package com.javaweb.course.controller.web;

import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.model.respone.CourseWebResponse;
import com.javaweb.course.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/web/course")
public class CourseWebController {

  @Autowired
  CourseServiceImpl courseServiceImpl;

  @GetMapping("")
  public List<CourseResponse> findALl() {
    return courseServiceImpl.findAll();
  }

  @PutMapping("{id}")
  public CourseWebResponse findCourseById(@PathVariable(value = "id") Integer id) {
    return courseServiceImpl.getCourseById(id);
  }
}
