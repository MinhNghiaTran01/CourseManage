package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Lesson;
import com.javaweb.course.model.dto.LessonDto;
import com.javaweb.course.model.respone.LessonResponse;
import com.javaweb.course.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {


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
}
