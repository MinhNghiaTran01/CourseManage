package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.CourseBenefit;
import com.javaweb.course.entity.Lesson;
import com.javaweb.course.entity.LessonCategory;
import com.javaweb.course.model.dto.CourseDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.model.respone.CourseWebResponse;
import com.javaweb.course.model.respone.LessonCategoryWebResponse;
import com.javaweb.course.model.respone.LessonResponse;
import com.javaweb.course.repository.CourseBenefitRepository;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.repository.LessonCategoryRepository;
import com.javaweb.course.repository.LessonRepository;
import com.javaweb.course.service.CourseService;
import com.javaweb.course.untils.Helper;
import com.javaweb.course.untils.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonCategoryRepository lessonCategoryRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseBenefitRepository courseBenefitRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(CourseDto courseDto) {
        Course course = new Course();
        course.setCode(courseDto.getCode());
        course.setCourseName(courseDto.getCourseName());
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setState(courseDto.getState());
        course.setCreatedAt(Helper.getNowMillisAtUtc());
        course.setUpdatedAt(Helper.getNowMillisAtUtc());
        courseRepository.save(course);

        CourseBenefit courseBenefit = new CourseBenefit();
        courseBenefit.setCourseId(course.getId());
        courseBenefit.setBenefits(JsonParser.getJson(courseDto.getBenefits()));
        courseBenefit.setCreatedAt(Helper.getNowMillisAtUtc());
        courseBenefit.setUpdatedAt(Helper.getNowMillisAtUtc());

        courseBenefitRepository.save(courseBenefit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Integer id, CourseDto courseDto) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setCourseName(courseDto.getCourseName());
            course.setCode(courseDto.getCode());
            course.setDescription(courseDto.getDescription());
            course.setPrice(courseDto.getPrice());
            course.setState(courseDto.getState());
            course.setUpdatedAt(Helper.getNowMillisAtUtc());
            courseRepository.save(course);
        }
        CourseBenefit courseBenefit = courseBenefitRepository.findById(id).orElse(null);
        if (courseBenefit != null) {
            courseBenefit.setBenefits(JsonParser.getJson(courseDto.getBenefits()));
            courseBenefit.setCreatedAt(Helper.getNowMillisAtUtc());
            courseBenefitRepository.save(courseBenefit);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        courseRepository.deleteById(id);
        courseBenefitRepository.deleteById(id);
    }

    @Override
    public List<CourseResponse> findAll() {
        List<Course> courses = courseRepository.findAll();

        return courses.stream().map(CourseResponse::new).toList();
    }

    @Override
    public CourseResponse findById(Integer id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new RuntimeException(String.format("course id: %s is null",id));
        }
        return new CourseResponse(course);
    }

    @Override
    public CourseWebResponse getCourseById(Integer id) {
        CourseWebResponse courseWebResponse = new CourseWebResponse();
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            throw new RuntimeException(String.format("course id: %s is null",id));
        }
        List<LessonCategory> lessonCategories = lessonCategoryRepository.findAllByCourseId(course.getId());
        List<LessonCategoryWebResponse> lessonCategoryWebResponses = new ArrayList<>();
        courseWebResponse.setCourseId(course.getId());
        for(LessonCategory lessonCategory : lessonCategories){
            LessonCategoryWebResponse lessonCategoryWebResponse = new LessonCategoryWebResponse();
            lessonCategoryWebResponse.setLessonCategoryId(lessonCategory.getId());

            List<Lesson> lessons = lessonRepository.findAllByLessonCategoryIdAndCourseId(lessonCategory.getId(),course.getId());

            List<LessonResponse> lessonResponses = lessons.stream().map(LessonResponse::new).toList();
            lessonCategoryWebResponse.setLessonResponseList(lessonResponses);

            lessonCategoryWebResponses.add(lessonCategoryWebResponse);
        }
        courseWebResponse.setLessonCategoryWebResponses(lessonCategoryWebResponses);

        return courseWebResponse;
    }
}
