package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.Lesson;
import com.javaweb.course.entity.LessonCategory;
import com.javaweb.course.entity.type.State;
import com.javaweb.course.model.dto.LessonDto;
import com.javaweb.course.model.respone.LessonResponse;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.repository.LessonCategoryRepository;
import com.javaweb.course.repository.LessonRepository;
import com.javaweb.course.service.LessonService;
import com.javaweb.course.service.MinIOService;
import com.javaweb.course.untils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
public class LessonServiceImpl implements LessonService {

  @Autowired
  private LessonRepository lessonRepository;

  @Autowired
  private MinIOService minIOService;
  @Autowired
  private CourseRepository courseRepository;
  @Autowired
  private LessonCategoryRepository lessonCategoryRepository;

  @Override
  public void save(LessonDto lessonDto) {

  }

  @Override
  public void update(Integer id, LessonDto lessonDto) {

  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public List<LessonResponse> findAll() {
    return List.of();
  }

  @Override
  public LessonResponse findById(Integer id) {
    return null;
  }

  @Transactional(rollbackFor = Exception.class)
  public void save(LessonDto lessonDto, MultipartFile file) {
    Course course = courseRepository.findById(lessonDto.getCourseId()).orElse(null);
    if (!Objects.isNull(course)) {
      throw new RuntimeException(String.format("course has id: %s not found", lessonDto.getCourseId()));
    }
    LessonCategory lessonCategory = lessonCategoryRepository.findById(lessonDto.getLessonCategoryId()).orElse(null);
    if (!Objects.isNull(lessonCategory)) {
      throw new RuntimeException(String.format("lessonCategory has id: %s not found", lessonDto.getLessonCategoryId()));
    }

    String videoUrl = course.getCourseName() + "/" + lessonCategory.getName() + "/" +lessonDto.getLessonName() + '.' + file.getOriginalFilename();

    Lesson lesson = new Lesson();
    lesson.setCourseId(lessonDto.getCourseId());
    lesson.setLessonCategoryId(lessonDto.getLessonCategoryId());
    lesson.setDescription(lessonDto.getDescription());
    lesson.setLessonName(lessonDto.getLessonName());
    lesson.setDuration(lessonDto.getDuration());
    lesson.setState(State.ACTIVE);
    lesson.setCreatedAt(Helper.getNowMillisAtUtc());
    lesson.setCreatedAt(Helper.getNowMillisAtUtc());

    lessonRepository.save(lesson);

    minIOService.uploadFile(videoUrl, lessonDto, file);

  }

  public List<LessonResponse> findAll(Integer courseId, Integer lessonCategoryId) {
    List<Lesson> lessons = lessonRepository.findAllByCourseIdAndLessonCategoryId(courseId, lessonCategoryId);

    return lessons.stream().map(LessonResponse::new).toList();
  }
}
