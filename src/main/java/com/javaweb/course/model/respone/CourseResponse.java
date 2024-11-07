package com.javaweb.course.model.respone;

import com.javaweb.course.entity.Course;
import com.javaweb.course.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@NoArgsConstructor
public class CourseResponse {

    private Integer id;

    private String code;

    private String courseName;;

    private String description;

    private Long price;

    private State state;

//  thêm thuộc tính image
    private byte[] image;

    public CourseResponse(Course course) {
//      thêm id để cung cấp id khi xóa
        this.id = course.getId();
        this.code = course.getCode();
        this.courseName = course.getCourseName();
        this.description = course.getDescription();
        this.price = course.getPrice();
        this.state = course.getState();
//      thêm đoạn code để gán image
        this.image = course.getImage();
    }
}
