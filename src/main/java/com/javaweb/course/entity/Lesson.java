package com.javaweb.course.entity;

import com.javaweb.course.entity.type.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "course_id")
    private String courseId;

    private String description;

    @Column(name = "lesson_name")
    private String lessonName;

    @Column(name = "video_url")
    private String videoUrl;

    private State state;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

}
