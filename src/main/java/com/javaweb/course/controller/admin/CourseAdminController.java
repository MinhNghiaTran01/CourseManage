package com.javaweb.course.controller.admin;

import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @PostMapping
    public Boolean save(@RequestBody CourseDto courseDto) {
        courseServiceImpl.save(courseDto);
        return true;
    }

    @PutMapping("{id}")
    public Boolean update(@PathVariable(value = "id") Integer id, CourseDto courseDto) {
        courseServiceImpl.update(id, courseDto);
        return true;
    }

    // Thiếu dấu / trước {id}
// CourseResponse làm gì có cung cấp id trả về mà ở đây lại nhận id từ frontend trả về
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable(value = "id") Integer id) {
        courseServiceImpl.delete(id);
        return true;
    }

    // Thiếu dấu / trước {id}
    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable(value = "id") Integer id) {
            return courseServiceImpl.findById(id);
    }

    // Thêm cac phương thức bổ sung trường private MultipartFile fileImage
    @PostMapping(value = "/s2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean saveAndImage(@ModelAttribute CourseDto courseDto) throws IOException {
        courseServiceImpl.saveAndImage(courseDto);
        return true;
    }

    // Thêm cac phương thức bổ sung trường private byte[] image;
    @GetMapping("/s2")
    public List<CourseResponse> findALlAndImage() {
        return courseServiceImpl.findAll();
    }

    // Thêm cac phương thức bổ sung trường private MultipartFile fileImage
    @PatchMapping(value = "s2",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean updateAndImage(@ModelAttribute CourseDto courseDto) throws IOException {
        courseServiceImpl.updateAndImage(courseDto);
        return true;
    }
}
