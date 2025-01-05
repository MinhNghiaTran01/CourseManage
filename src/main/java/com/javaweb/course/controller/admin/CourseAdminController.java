package com.javaweb.course.controller.admin;

import com.javaweb.course.entity.Course;
import com.javaweb.course.model.dto.CourseBenefitDTO;
import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.CourseBenefitService;
import com.javaweb.course.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("v1/admin/course")
public class CourseAdminController {

    @Autowired
    private CourseServiceImpl courseServiceImpl;

    @Autowired
    private CourseBenefitService courseBenefitService;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("")
    public List<CourseResponse> findALl() {
        return courseServiceImpl.findAll();
    }

//    @PutMapping("{id}")
//    public Boolean update(@PathVariable(value = "id") Integer id, CourseDto courseDto) {
//        courseServiceImpl.update(id, courseDto);
//        return true;
//    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable(value = "id") Integer id) {
        courseServiceImpl.delete(id);
        return true;
    }

    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable(value = "id") Integer id) {
        return courseServiceImpl.findById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> save(@ModelAttribute CourseDto courseDto) throws IOException {
        Boolean checkSaveCourse = courseServiceImpl.saveAndImage(courseDto);
        CourseBenefitDTO courseBenefitDTO = CourseBenefitDTO.builder()
                .benefits(courseDto.getBenefits())
                .codeCourse(courseDto.getCode())
                .build();
        Boolean checkSaveCourseBenefit = courseBenefitService.save(courseBenefitDTO);
        if (!checkSaveCourse) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lưu khóa học thất bại");
        } else if (!checkSaveCourseBenefit) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Lưu lợi ích khóa học thất bại");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body("Đã tạo khóa học thành công");
        }
    }

    @GetMapping("/s2")
    public List<CourseResponse> findALlAndImage() {
        return courseServiceImpl.findAll();
    }

    @PatchMapping(value = "/s2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Boolean update(@ModelAttribute CourseDto courseDto) throws IOException {
        courseServiceImpl.updateCourse(courseDto);
        return true;
    }
}
