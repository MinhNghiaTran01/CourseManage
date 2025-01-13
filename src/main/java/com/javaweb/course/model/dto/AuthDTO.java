package com.javaweb.course.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class AuthDTO {
    @NotNull
    @Length(min = 5, max = 50)
    private String username;

    @NotNull
    @Length(min = 5, max = 10)
    private String password;

    public @NotNull @Length(min = 5, max = 50) String getEmail() {
        return username;
    }

    public void setEmail(@NotNull @Length(min = 5, max = 50) String email) {
        this.username = username;
    }

    public @NotNull @Length(min = 5, max = 10) String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Length(min = 5, max = 10) String password) {
        this.password = password;
    }
}