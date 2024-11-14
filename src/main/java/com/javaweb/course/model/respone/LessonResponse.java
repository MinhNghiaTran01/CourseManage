package com.javaweb.course.model.respone;

import com.javaweb.course.entity.Lesson;
import com.javaweb.course.enums.State;
import lombok.Data;


@Data
public class LessonResponse {
    
    private Integer id;

    private String lessonName;

    private String description;

    private State state;

    private String duration;

    private Integer courseId;
    
    private Integer lessonCategoryId;


    private String folderId;

    public LessonResponse(Lesson lesson) {
        this.lessonName = lesson.getLessonName();
        this.description = lesson.getDescription();
        this.state = lesson.getState();
        this.duration = lesson.getDuration();
        this.courseId = lesson.getCourseId();
        this.id = lesson.getId();
        this.lessonCategoryId = lesson.getLessonCategoryId();
        this.folderId = lesson.getFolderId();
    }
}
