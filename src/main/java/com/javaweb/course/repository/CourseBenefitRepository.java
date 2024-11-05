package com.javaweb.course.repository;

import com.javaweb.course.entity.CourseBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseBenefitRepository extends JpaRepository<CourseBenefit, Integer> {
}
