package com.javaweb.course.model.respone;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegistrationCourseResponse {

    private int courseId;
    private String courseName;
    private Integer paymentVnpayId;

}
