package com.javaweb.course.repository;

import com.javaweb.course.entity.CourseBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseBenefitRepository extends JpaRepository<CourseBenefit, Integer> {

    List<CourseBenefit> findByCourseId(Integer courseId);
}
