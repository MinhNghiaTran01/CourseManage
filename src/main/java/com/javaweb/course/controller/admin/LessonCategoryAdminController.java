package com.javaweb.course.controller.admin;

import com.javaweb.course.model.dto.LessonCategoryDto;
import com.javaweb.course.model.respone.LessonCategoryResponse;
import com.javaweb.course.service.LessonCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/admin/lesson-category")
public class LessonCategoryAdminController {
    @Autowired
    private LessonCategoryService lessonCategoryService;

    @GetMapping("courseId/{courseId}")
    public List<LessonCategoryResponse> findAll(@PathVariable(value = "courseId") Integer courseId) {
        return lessonCategoryService.findByCourseId(courseId);
    }

    @PostMapping("")
    public Boolean save(@RequestBody LessonCategoryDto lessonCategoryDto) {
        lessonCategoryService.save(lessonCategoryDto);
        return true;
    }

    @PutMapping("{id}")
    public Boolean update(@PathVariable("id") Integer id, LessonCategoryDto lessonCategoryDto) {
        lessonCategoryService.update(id, lessonCategoryDto);
        return true;
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable(value = "id") Integer id) {
        lessonCategoryService.delete(id);
        return true;
    }

    @GetMapping("{id}")
    public LessonCategoryResponse findById(@PathVariable(value = "id") Integer id) {
        return lessonCategoryService.findById(id);
    }

    @PatchMapping()
    public Boolean update(@RequestBody LessonCategoryDto lessonCategoryDto) {
        lessonCategoryService.updateNew(lessonCategoryDto);
        return true;
    }

}
