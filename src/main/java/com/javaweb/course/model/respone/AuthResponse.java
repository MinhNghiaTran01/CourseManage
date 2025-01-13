package com.javaweb.course.model.respone;

import com.javaweb.course.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Integer id;
    private String name;
    private String username;
    private String accessToken;
    private Set<Role> roles;
}