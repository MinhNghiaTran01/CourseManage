package com.javaweb.course.repository;

import com.javaweb.course.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findAllByCourseIdAndLessonCategoryId(int courseId, int lessonCategoryId);
}
