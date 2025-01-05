package com.javaweb.course.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class InfoDetailUserDTO {

    private Integer id;
    private String phoneNumber;
    private String address;
    private Long totalAmountPaid;
    private Long totalCourseRegistered;
    private String fullName;
    private String username;
    private String password;
    private List<String> roles;
    private String description;
}
