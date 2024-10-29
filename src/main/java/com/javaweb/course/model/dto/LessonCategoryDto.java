package com.javaweb.course.model.dto;

import com.javaweb.course.entity.type.State;
import lombok.Data;

@Data
public class LessonCategoryDto {

  private Integer courseId;

  private String name;

  private State state;
}
