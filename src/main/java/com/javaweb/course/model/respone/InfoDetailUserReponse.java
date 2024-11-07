package com.javaweb.course.model.respone;

import com.javaweb.course.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class InfoDetailUserReponse {
    private Integer id;
    private String phoneNumber;
    private String address;
    private String fullName;
    private String username;
    private String password;
    private String roles;
    private String description;
}
