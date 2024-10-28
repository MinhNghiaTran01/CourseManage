package com.javaweb.course.repository;

import com.javaweb.course.entity.LessonCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonCategoryRepository extends JpaRepository<LessonCategory, Integer> {
}
