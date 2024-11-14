package com.javaweb.course.model.respone;

import lombok.Builder;
import lombok.Data;

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
