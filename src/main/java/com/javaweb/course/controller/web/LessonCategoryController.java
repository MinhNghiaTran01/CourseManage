package com.javaweb.course.controller.web;

import com.javaweb.course.model.respone.LessonCategoryResponse;
import com.javaweb.course.service.LessonCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user/lesson-category")
@RestController
public class LessonCategoryController {

    @Autowired
    private LessonCategoryService lessonCategoryService;

    @GetMapping
    public ResponseEntity<List<LessonCategoryResponse>> findAll(@RequestParam Integer courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonCategoryService.findByCourseId(courseId));
    }
}
