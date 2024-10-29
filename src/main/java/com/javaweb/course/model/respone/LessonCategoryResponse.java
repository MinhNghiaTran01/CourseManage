package com.javaweb.course.model.respone;

import com.javaweb.course.entity.LessonCategory;
import com.javaweb.course.entity.type.State;
import lombok.Data;

@Data
public class LessonCategoryResponse {

  private Integer id;

  private Integer courseId;

  private String name;

  private State state;

  public LessonCategoryResponse(LessonCategory lessonCategory) {
    this.id = lessonCategory.getId();
    this.name = lessonCategory.getName();
    this.state = lessonCategory.getState();
    this.courseId = lessonCategory.getCourseId();
  }
}
