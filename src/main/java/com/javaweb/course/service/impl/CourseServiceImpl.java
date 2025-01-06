package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.enums.State;
import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.repository.CourseBenefitRepository;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.repository.LessonCategoryRepository;
import com.javaweb.course.repository.LessonRepository;
import com.javaweb.course.service.CourseService;
import com.javaweb.course.service.GoogleDriveService;
import com.javaweb.course.untils.Helper;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GoogleDriveService googleDriveService;
    @Autowired
    private LessonCategoryRepository lessonCategoryRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private CourseBenefitRepository courseBenefitRepository;
    @Autowired
    private ModelMapper modelMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        Course course = courseRepository.findById(id).orElseThrow();
        lessonCategoryRepository.deleteAllByCourseId(id);
        lessonRepository.deleteAllByCourseId(id);
        courseBenefitRepository.deleteAllByCourseId(id);
        courseRepository.delete(course);
        googleDriveService.deleteFolderById(course.getFolderId());
    }

    public List<CourseResponse> findAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseResponse> courseResponses = new ArrayList<>();
        for(Course course : courses) {
            if(course.getState()==State.INACTIVE) continue;
            CourseResponse courseResponse = new CourseResponse(course);
            courseResponses.add(courseResponse);
        }
        return courseResponses;
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
        if(courseDto.getCourseName()!=null) {
            course.setCourseName(courseDto.getCourseName());
            googleDriveService.updateFolderCourseName(course.getFolderId(),courseDto.getCourseName());
        }
        if(courseDto.getDescription()!=null) {
            course.setCourseName(courseDto.getCourseName());
        }
        if(courseDto.getPrice()!=null) {
            course.setPrice(courseDto.getPrice());
        }
        if(courseDto.getState()!=null) {
            course.setState(courseDto.getState());
        }
        course.setUpdatedAt(Helper.getNowMillisAtUtc());
        courseRepository.save(course);
    }

    public List<Course> findByCourseNameLikeIgnoreCase(String courseName) {
        return courseRepository.findByCourseNameLikeIgnoreCase(courseName);
    }
}
