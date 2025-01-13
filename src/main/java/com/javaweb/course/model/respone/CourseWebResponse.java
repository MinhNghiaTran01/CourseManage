package com.javaweb.course.model.respone;


import lombok.Data;

import java.util.List;

@Data
public class CourseWebResponse {
    private Integer courseId;

    private List<LessonCategoryWebResponse> lessonCategoryWebResponses;
}
