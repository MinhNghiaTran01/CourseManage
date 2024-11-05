package com.javaweb.course.controller.admin;

import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/admin/course")
public class CourseAdminController {

    @Autowired
    CourseService courseService;

    @GetMapping("")
    public List<CourseResponse> findALl() {
        return courseService.findAll();
    }

    @PostMapping("")
    public Boolean save(@RequestBody CourseDto courseDto) {
        courseService.save(courseDto);
        return true;
    }

    @PutMapping("{id}")
    public Boolean update(@PathVariable(value = "id") Integer id, @RequestBody CourseDto courseDto) {
        courseService.update(id, courseDto);
        return true;
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable(value = "id") Integer id) {
        courseService.delete(id);
        return true;
    }

    @GetMapping("{id}")
    public CourseResponse findById(@PathVariable(value = "id") Integer id) {
        return courseService.findById(id);
    }
}
