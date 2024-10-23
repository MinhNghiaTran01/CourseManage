package com.javaweb.course.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class AuthDTO {
    @NotNull @Email @Length(min = 5, max = 50)
    private String email;

    @NotNull @Length(min = 5, max = 10)
    private String password;

    // getters and setters are not shown...


    public @NotNull @Email @Length(min = 5, max = 50) String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @Email @Length(min = 5, max = 50) String email) {
        this.email = email;
    }

    public @NotNull @Length(min = 5, max = 10) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Length(min = 5, max = 10) String password) {
        this.password = password;
    }
}