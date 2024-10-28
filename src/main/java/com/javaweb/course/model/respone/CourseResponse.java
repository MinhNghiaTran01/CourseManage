package com.javaweb.course.model.respone;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.type.State;
import lombok.Data;

import javax.persistence.Column;

@Data
public class CourseResponse {
    private String code;

    private String courseName;;

    private String description;

    private Long price;

    private State state;

    public CourseResponse(Course course) {
        this.code = course.getCode();
        this.courseName = course.getCourseName();
        this.description = course.getDescription();
        this.price = course.getPrice();
        this.state = course.getState();
    }
}
