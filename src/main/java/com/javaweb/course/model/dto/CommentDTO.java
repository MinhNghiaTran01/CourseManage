package com.javaweb.course.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String comment;
    private Integer userId;
    private Integer courseId;
    private Integer lessonId;
}
