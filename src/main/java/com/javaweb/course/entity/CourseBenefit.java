package com.javaweb.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CourseBenefit {

    @Id
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "benefits", columnDefinition = "TEXT")
    private String benefits;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}