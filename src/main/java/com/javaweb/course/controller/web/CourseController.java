package com.javaweb.course.controller.web;

import com.javaweb.course.entity.Course;
import com.javaweb.course.enums.State;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/user/course")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/search")
    public ResponseEntity<List<CourseResponse>> findByCourseNameContainingIgnoreCase(@RequestParam String courseName) {
        List<Course> courses = courseService.findByCourseNameLikeIgnoreCase(courseName);
//        List<CourseResponse> courseResponses = courses.stream()
//                .map(course -> modelMapper.map(course, CourseResponse.class))
//                .collect(Collectors.toList());
        List<CourseResponse> courseResponses = new ArrayList<>();
        for(Course course : courses) {
            if(course.getState()== State.INACTIVE) continue;
            CourseResponse courseResponse = modelMapper.map(course, CourseResponse.class);
            courseResponses.add(courseResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> findById(@PathVariable Integer id) {
        CourseResponse courseResponse = courseService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(courseResponse);
    }


    @GetMapping
    public ResponseEntity<List<CourseResponse>> findAll() {
        List<CourseResponse> courseResponses = courseService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(courseResponses);
    }

}
