package com.javaweb.course.entity;

import com.javaweb.course.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "lesson_category_id")
    private Integer lessonCategoryId;

    private String description;

    @Column(name = "lesson_name")
    private String lessonName;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private State state;

    private String duration;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(nullable = false, unique = true)
    private String folderId;

}
