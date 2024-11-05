package com.javaweb.course.model.respone;

import lombok.Data;

import java.util.List;

@Data
public class LessonCategoryWebResponse {
  private Integer lessonCategoryId;

  private List<LessonResponse> lessonResponseList;
}
