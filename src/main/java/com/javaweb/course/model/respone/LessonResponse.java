package com.javaweb.course.model.respone;

import com.javaweb.course.entity.Lesson;
import com.javaweb.course.enums.State;
import lombok.Data;
// Thieu id
@Data
public class LessonResponse {
//  Thêm id , để khi sửa có id mà gửi về
    private Integer id;

    private String lessonName;

    private String description;

    private State state;

    private String duration;

    private Integer courseId;
//  Thêm lessonCategoryId
    private Integer lessonCategoryId;

    private String videoUrl;

    public LessonResponse(Lesson lesson) {
        this.lessonName = lesson.getLessonName();
        this.description = lesson.getDescription();
        this.state = lesson.getState();
        this.duration = lesson.getDuration();
        this.courseId = lesson.getCourseId();
        this.id = lesson.getId();
        this.lessonCategoryId = lesson.getLessonCategoryId();
        this.videoUrl = lesson.getVideoUrl();
    }
}
