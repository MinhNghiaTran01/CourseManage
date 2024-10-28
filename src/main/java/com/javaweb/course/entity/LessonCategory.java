package com.javaweb.course.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lesson_category")
@Data
public class LessonCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

}
