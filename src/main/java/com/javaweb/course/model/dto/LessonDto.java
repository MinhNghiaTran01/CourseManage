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

    public LessonDto(Integer courseId, String duration, String description) {
        this.courseId = courseId;
        this.duration = duration;
        this.description = description;
    }
}
