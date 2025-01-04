package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.enums.State;
import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.service.CourseService;
import com.javaweb.course.untils.Helper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void save(CourseDto courseDto) {
        Course course = new Course();
        course.setCourseName(courseDto.getCourseName());
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setState(courseDto.getState());
        course.setCreatedAt(Helper.getNowMillisAtUtc());
        course.setUpdatedAt(Helper.getNowMillisAtUtc());
        courseRepository.save(course);
    }

    @Override
    public void update(Integer id, CourseDto courseDto) {
    }


//    @Override
//    public void update(Integer id, CourseDto courseDto) {
//        Course course = courseRepository.findById(id).orElse(null);
//        if (course != null) {
//            course.setCourseName(courseDto.getCourseName());
//            course.setCode(courseDto.getCode());
//            course.setDescription(courseDto.getDescription());
//            course.setPrice(courseDto.getPrice());
//            course.setState(courseDto.getState());
//            course.setUpdatedAt(Helper.getNowMillisAtUtc());
//            courseRepository.save(course);
//        }
//    }

    @Override
    public void delete(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow();
        course.setState(State.INACTIVE);
        courseRepository.save(course);
    }

    public List<CourseResponse> findAll() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().map(CourseResponse::new).toList();
    }

    @Override
    public CourseResponse findById(Integer id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new RuntimeException(String.format("course id: %s is null", id));
        }
        return new CourseResponse(course);
    }

    @SneakyThrows
    public Boolean saveAndImage(CourseDto courseDto) {
        // Tạo một thực thể Course mới và gán dữ liệu từ DTO
        try {
            Course course = new Course();
            course.setCode(courseDto.getCode());
            course.setCourseName(courseDto.getCourseName());
            course.setDescription(courseDto.getDescription());
            course.setPrice(courseDto.getPrice());
            course.setState(courseDto.getState());
            course.setFolderId(courseDto.getFolderId());
            course.setCreatedAt(Helper.getNowMillisAtUtc());
            course.setUpdatedAt(Helper.getNowMillisAtUtc());
            if (courseDto.getFileImage() != null) {
                course.setImage(courseDto.getFileImage().getBytes());
            }
            courseRepository.save(course);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updateCourse(CourseDto courseDto) throws IOException {
        Course course = courseRepository.findById(courseDto.getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setState(courseDto.getState());
        course.setUpdatedAt(Helper.getNowMillisAtUtc());
        // để khi không thay đổi ảnh trên frontend sẽ k bị null ( các trường khác không cần kiểm tra, vì
        // ảnh gửi từ frontend về ở dạng chuỗi ảnh base64, không phải file để truyền về
        if (courseDto.getFileImage() != null && !courseDto.getFileImage().isEmpty()) {
            course.setImage(courseDto.getFileImage().getBytes());
        }
        courseRepository.save(course);
    }

    public List<Course> findByCourseNameLikeIgnoreCase(String courseName) {
        return courseRepository.findByCourseNameLikeIgnoreCase(courseName);
    }
}
