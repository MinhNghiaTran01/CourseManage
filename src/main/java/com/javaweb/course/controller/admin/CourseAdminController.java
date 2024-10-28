package com.javaweb.course.controller.admin;

import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/admin/course")
public class CourseAdminController {

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @GetMapping("")
    public List<CourseResponse> findALl() {
        return courseServiceImpl.findAll();
    }

    @PostMapping("")
    public Boolean save(CourseDto courseDto) {
        courseServiceImpl.save(courseDto);
        return true;
    }

    @PutMapping("{id}")
    public Boolean update(@PathVariable(value = "id") Integer id, CourseDto courseDto) {
        courseServiceImpl.update(id, courseDto);
        return true;
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable(value = "id") Integer id) {
        courseServiceImpl.delete(id);
        return true;
    }

    @GetMapping("{id}")
    public CourseResponse findById(@PathVariable(value = "id") Integer id) {
        return courseServiceImpl.findById(id);
    }
}
