package com.javaweb.course.service;

import com.javaweb.course.entity.Role;

public interface RoleService {
    Role findByName(String roleName);
}
