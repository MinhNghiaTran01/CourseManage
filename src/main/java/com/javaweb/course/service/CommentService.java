package com.javaweb.course.service;

import com.javaweb.course.entity.Comment;
import com.javaweb.course.model.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    List<Comment> findByLessonIdAndCourseId(Integer courseId, Integer lessonId);

    void save(CommentDTO commentDTO);
}
