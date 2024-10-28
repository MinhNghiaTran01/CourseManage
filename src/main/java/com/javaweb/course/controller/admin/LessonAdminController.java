package com.javaweb.course.controller.admin;

import com.javaweb.course.model.respone.LessonResponse;
import com.javaweb.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/admin/lesson")
public class LessonAdminController {


    @Autowired
    private LessonService lessonService;

    @GetMapping("")
    public List<LessonResponse> findALl(){
       return lessonService.findAll();
    }

    @DeleteMapping("")
    public Boolean deleteById(Integer id) {
        lessonService.delete(id);
        return true;
    }

}
