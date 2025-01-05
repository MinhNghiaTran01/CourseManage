package com.javaweb.course.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class StudentDTO {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String description;
    private Long totalAmountPaid;
    private Long totalCourseRegistered;
    private String username;
    private List<String> roles;
    private String facebook;
    private String linkedin;
    private String github;
}
