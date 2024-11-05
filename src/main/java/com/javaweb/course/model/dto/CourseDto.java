package com.javaweb.course.model.dto;

import com.javaweb.course.entity.type.State;
import lombok.Data;

import java.util.List;

@Data
public class CourseDto {

    private String code;

    private String courseName;

    private String description;

    private Long price;

    private State state;

    List<String> benefits;
}
