package com.javaweb.course.entity;

import com.javaweb.course.entity.type.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String code;

    @Column(name = "course_name")
    private String courseName;;

    private String description;

    private Long price;

    private State state;

    @Column(name = "category_id")
    private String categoryId;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name="updated_at")
    private Long updatedAt;
}
