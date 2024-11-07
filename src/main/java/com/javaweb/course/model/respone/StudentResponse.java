package com.javaweb.course.model.respone;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentResponse {
    private Integer id;
    private String phoneNumber;
    private String address;
    private String fullName;
    private String username;
    private String roles;
    private String description;
    private Integer totalCourseRegistered;
    private Integer totalAmountPaid;

}
