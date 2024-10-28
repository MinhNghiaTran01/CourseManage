package com.javaweb.course.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String sub;
}
