package com.javaweb.course.controller.web;

import com.javaweb.course.model.respone.CourseBenefitResponse;
import com.javaweb.course.model.respone.CourseWebResponse;
import com.javaweb.course.service.CourseBenefitService;
import com.javaweb.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/web/course-benefit")
public class CourseBenefitController {

  @Autowired
  CourseBenefitService courseBenefitService;

  @PutMapping("{id}")
  public CourseBenefitResponse findCourseById(@PathVariable(value = "id") Integer id) {
    return courseBenefitService.findById(id);
  }
}
