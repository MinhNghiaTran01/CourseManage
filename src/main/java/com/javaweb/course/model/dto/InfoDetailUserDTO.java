package com.javaweb.course.model.dto;

import com.javaweb.course.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class InfoDetailUserDTO {

    private Integer id;
    private String phoneNumber;
    private String address;
    private Integer totalAmountPaid;
    private Integer totalCourseRegistered;
    private String fullName;
    private String username;
    private String password;
    private List<String> roles;
    private String description;
}
