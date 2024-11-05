package com.javaweb.course.controller.admin;

import com.javaweb.course.entity.type.State;
import com.javaweb.course.model.dto.LessonDto;
import com.javaweb.course.model.respone.LessonResponse;
import com.javaweb.course.service.LessonService;
import com.javaweb.course.service.impl.LessonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("v1/admin/lesson")
public class LessonAdminController {


  @Autowired
  private LessonServiceImpl lessonService;

  @GetMapping("")
  public List<LessonResponse> findALl(@RequestParam(value = "courseId") Integer courseId,
                                      @RequestParam(value = "lessonCategoryId") Integer lessonCategoryId) {
    return lessonService.findAll(courseId, lessonCategoryId);
  }

  @DeleteMapping("")
  public Boolean deleteById(Integer id) {
    lessonService.delete(id);
    return true;
  }

  @CrossOrigin(origins = "http://127.0.0.1:5500")
  @PostMapping
  public Boolean save(@RequestParam(value = "file") MultipartFile file,
                      @RequestParam(value = "courseId") Integer courseId,
                      @RequestParam(value = "lessonCategoryId") Integer lessonCategoryId,
                      @RequestParam(value = "lessonName") String lessonName,
                      @RequestParam(value = "description") String description,
                      @RequestParam(value = "duration") String duration) {
    LessonDto lessonDto = new LessonDto(courseId, lessonCategoryId, lessonName, description, duration);
    lessonService.save(lessonDto, file);
    return true;
  }
}
