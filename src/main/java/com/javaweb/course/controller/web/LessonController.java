package com.javaweb.course.controller.web;

import com.javaweb.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/lesson")
@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam Integer courseId, @RequestParam Integer lessonCategoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAll(courseId, lessonCategoryId));
    }
}
