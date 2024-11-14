package com.javaweb.course.model.dto;

import com.javaweb.course.enums.State;
import lombok.Data;

@Data
public class LessonCategoryDto {

    private Integer id;

    private Integer courseId;

    private String name;

    private State state;

    private String folderIdCourse;

    private String folderId;
}
