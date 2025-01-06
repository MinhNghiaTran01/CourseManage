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
    private String state;
    private String description;
    private Long totalCourseRegistered;
    private Long totalAmountPaid;
    private String facebook;
    private String linkedin;
    private String github;
}
