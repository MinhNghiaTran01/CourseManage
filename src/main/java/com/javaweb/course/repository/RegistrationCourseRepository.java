package com.javaweb.course.repository;

import com.javaweb.course.entity.RegistrationCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegistrationCourseRepository extends JpaRepository<RegistrationCourse, Integer> {

    @Query("SELECT r FROM RegistrationCourse r WHERE r.course.id = :courseId AND r.user.id = :userId")
    RegistrationCourse findByCourseIdAndUserId(@Param("courseId") Integer courseId,@Param("userId") Integer userId);
}
