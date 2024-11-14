package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.Lesson;
import com.javaweb.course.entity.LessonCategory;
import com.javaweb.course.model.dto.LessonDto;
import com.javaweb.course.model.respone.LessonResponse;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.repository.LessonCategoryRepository;
import com.javaweb.course.repository.LessonRepository;
import com.javaweb.course.service.LessonService;
import com.javaweb.course.untils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;


    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LessonCategoryRepository lessonCategoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void update(Integer id, LessonDto lessonDto) {

    }

    //  Thêm lessonRepository.deleteById(id);
    @Override
    public void delete(Integer id) {
        lessonRepository.deleteById(id);
    }


    @Override
    public LessonResponse findById(Integer id) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(LessonDto lessonDto) {

        Course course = courseRepository.findById(lessonDto.getCourseId()).orElse(null);
        if (Objects.isNull(course)) {
            throw new RuntimeException(String.format("course has id: %s not found", lessonDto.getCourseId()));
        }
        LessonCategory lessonCategory = lessonCategoryRepository.findById(lessonDto.getLessonCategoryId()).orElse(null);
        if (Objects.isNull(lessonCategory)) {
            throw new RuntimeException(String.format("lessonCategory has id: %s not found", lessonDto.getLessonCategoryId()));
        }

//        String videoUrl = course.getCourseName() + "/" + lessonCategory.getName() + "/" + lessonDto.getLessonName() + '.' + file.getOriginalFilename();

        Lesson lesson = modelMapper.map(lessonDto, Lesson.class);
        lesson.setCreatedAt(Helper.getNowMillisAtUtc());
        lesson.setCreatedAt(Helper.getNowMillisAtUtc());

        lessonRepository.save(lesson);

    }

    public List<LessonResponse> findAll(Integer courseId, Integer lessonCategoryId) {
        List<Lesson> lessons = lessonRepository.findAllByCourseIdAndLessonCategoryId(courseId, lessonCategoryId);
        return lessons.stream().map(LessonResponse::new).toList();
    }

    @Override
    public void updates2(LessonDto lessonDto) {
        Lesson lesson = lessonRepository.findById(lessonDto.getId()).orElse(null);
        lesson.setDescription(lessonDto.getDescription());
        lesson.setDuration(lessonDto.getDuration());
        lesson.setState(lessonDto.getState());
        lesson.setUpdatedAt(Helper.getNowMillisAtUtc());
        lesson.setLessonName(lessonDto.getLessonName());
        lessonRepository.save(lesson);
    }
}
