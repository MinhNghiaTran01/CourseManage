package com.javaweb.course.model.dto;

import com.javaweb.course.entity.type.State;
import lombok.Data;

@Data
public class LessonDto {

    private String lessonName;

    private String description;

    private State state;

    private String duration;

    private Integer courseId;

    private Integer lessonCategoryId;

    public LessonDto(Integer courseId,Integer lessonCategoryId,String lessonName, String duration, String description) {
        this.lessonCategoryId = lessonCategoryId;
        this.lessonName = lessonName;
        this.courseId = courseId;
        this.duration = duration;
        this.description = description;
    }
}
