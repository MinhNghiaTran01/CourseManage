package com.javaweb.course.service;

import com.javaweb.course.entity.LessonCategory;
import com.javaweb.course.model.dto.LessonCategoryDto;
import com.javaweb.course.model.respone.LessonCategoryResponse;

import java.util.List;

public interface LessonCategoryService extends BaseService<LessonCategory, LessonCategoryDto, LessonCategoryResponse> {

  List<LessonCategoryResponse> findByCourseId(Integer courseId);
}
