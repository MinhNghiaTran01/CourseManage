package com.javaweb.course.repository;

import com.javaweb.course.entity.LessonCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonCategoryRepository extends JpaRepository<LessonCategory, Integer> {

    List<LessonCategory> findAllByCourseId(Integer courseId);

    void deleteAllByCourseId(Integer courseId);
}
