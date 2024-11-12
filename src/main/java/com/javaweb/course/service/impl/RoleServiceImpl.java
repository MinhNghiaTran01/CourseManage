package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Role;
import com.javaweb.course.repository.RoleRepository;
import com.javaweb.course.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
