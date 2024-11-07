package com.javaweb.course.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationCourseDTO {

    private Long registrationDate;
    private String paymentTransactionId;
    private Integer courseId;
    private Integer userId;
}
