package com.javaweb.course.model.respone;

import com.javaweb.course.entity.Course;
import com.javaweb.course.enums.State;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseResponse {

    private Integer id;

    private String code;

    private String courseName;
    ;

    private String description;

    private Long price;

    private State state;

    
    private byte[] image;

    private String folderId;

    public CourseResponse(Course course) {

        this.id = course.getId();
        this.code = course.getCode();
        this.courseName = course.getCourseName();
        this.description = course.getDescription();
        this.price = course.getPrice();
        this.state = course.getState();

        this.image = course.getImage();
        this.folderId = course.getFolderId();
    }
}
