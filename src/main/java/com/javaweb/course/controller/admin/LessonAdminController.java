package com.javaweb.course.controller.admin;

import com.javaweb.course.model.dto.LessonDto;
import com.javaweb.course.model.respone.LessonResponse;
import com.javaweb.course.service.impl.LessonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

//  Thiếu /{id} và @PathVariable
    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable Integer id) {
        lessonService.delete(id);
        return true;
    }

    // nhận MultipartFile vào mà chưa thấy làm gì
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean save(@ModelAttribute LessonDto lessonDto) {
        lessonService.save(lessonDto);
        return true;
    }

// thêm updates2 test
    @PatchMapping
    public Boolean updates2(@RequestBody LessonDto lessonDto) {
        lessonService.updates2(lessonDto);
        return true;
    }
}
