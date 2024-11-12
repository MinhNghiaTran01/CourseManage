package com.javaweb.course.model.dto;

import com.javaweb.course.entity.User;
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
    private Integer totalAmountPaid;
    private Integer totalCourseRegistered;
    private String username;
    private List<String> roles;
    private String facebook;
    private String linkedin;
    private String github;
}
