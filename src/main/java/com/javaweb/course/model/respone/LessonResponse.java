package com.javaweb.course.model.respone;

import com.javaweb.course.entity.type.State;
import lombok.Data;

@Data
public class LessonResponse {
    private String lessonName;

    private String description;

    private State state;

    private String duration;

    private Integer courseId;
}
