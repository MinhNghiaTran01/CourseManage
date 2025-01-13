package com.javaweb.course.repository;

import com.javaweb.course.entity.User;

public interface UserCustomeRepository {
    public User findByUsername(String userName);
}
