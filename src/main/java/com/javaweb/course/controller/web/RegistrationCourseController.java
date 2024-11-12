package com.javaweb.course.controller.web;

import com.javaweb.course.entity.RegistrationCourse;
import com.javaweb.course.model.dto.RegistrationCourseDTO;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.model.respone.RegistrationCourseResponse;
import com.javaweb.course.service.CourseService;
import com.javaweb.course.service.RegistrationCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/registration-course")
public class RegistrationCourseController {

    @Autowired
    private RegistrationCourseService registrationCourseService;

    @Autowired
    private CourseService courseService;

//    @PostMapping
//    public ResponseEntity<?> save(@RequestBody RegistrationCourseDTO registrationCourseDTO) {
//        if(registrationCourseService.save(registrationCourseDTO)){
//            return ResponseEntity.status(HttpStatus.CREATED).body(registrationCourseDTO);
//        }
//        else{
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(registrationCourseDTO);
//        }
//    }

    @GetMapping("")
    public ResponseEntity<List<CourseResponse>> findByUserId(@RequestParam Integer userId) {
        List<RegistrationCourse> registrationCourses = registrationCourseService.findByUserId(userId);
        List<CourseResponse> courseResponses = (List<CourseResponse>) registrationCourses.stream()
                .map(registrationCourse -> courseService.findById(registrationCourse.getCourse().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(courseResponses);
    }

    @ExceptionHandler(Exception.class)
    @GetMapping("/search")
    public ResponseEntity<RegistrationCourseResponse> findByCourseIdAndUserId(@RequestParam Integer courseId, @RequestParam Integer userId) {
        RegistrationCourse registrationCourse = registrationCourseService.findByCourseIdAndUserId(courseId, userId);
        RegistrationCourseResponse registrationCourseResponse = new RegistrationCourseResponse();
        registrationCourseResponse.setCourseId(registrationCourse.getCourse().getId());
        registrationCourseResponse.setCourseName(registrationCourse.getCourse().getCourseName());
        return ResponseEntity.status(HttpStatus.OK).body(registrationCourseResponse);
    }
}
