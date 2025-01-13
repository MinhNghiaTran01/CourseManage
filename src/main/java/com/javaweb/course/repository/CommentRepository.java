package com.javaweb.course.repository;

import com.javaweb.course.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByLessonIdAndCourseId(Integer lessonId, Integer courseId);
}
