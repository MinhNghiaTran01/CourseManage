package com.javaweb.course.model.dto;

import com.javaweb.course.enums.State;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LessonDto {

    //  thêm id
    private Integer id;

    private String lessonName;

    private String description;

    private State state;

    private String duration;

    private Integer courseId;

    private Integer lessonCategoryId;

    private String folderId;


    public LessonDto(String lessonName, String description, State state, String duration,
                     Integer courseId, Integer lessonCategoryId, String folderId) {
        this.lessonName = lessonName;
        this.description = description;
        this.state = state;
        this.duration = duration;
        this.courseId = courseId;
        this.lessonCategoryId = lessonCategoryId;
        this.folderId = folderId;
    }
}
