package com.javaweb.course.controller.web;

import com.javaweb.course.repository.LessonRepository;
import com.javaweb.course.service.LessonService;
import org.checkerframework.checker.units.qual.A;
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

    @Autowired
    private LessonRepository lessonRepository;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam Integer courseId, @RequestParam Integer lessonCategoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAll(courseId, lessonCategoryId));
    }

    @GetMapping("/find-lesson")
    public ResponseEntity<?> findLesson(@RequestParam Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonRepository.findById(id));
    }

}
