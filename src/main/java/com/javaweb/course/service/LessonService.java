package com.javaweb.course.service;

import com.javaweb.course.entity.Lesson;
import com.javaweb.course.model.dto.LessonDto;
import com.javaweb.course.model.respone.CourseResponse;
import com.javaweb.course.model.respone.LessonResponse;

import java.util.List;

public interface LessonService extends BaseService<Lesson, LessonDto, LessonResponse> {
    void updates2(LessonDto lessonDto);

    public List<LessonResponse> findAll(Integer courseId, Integer lessonCategoryId);
}
