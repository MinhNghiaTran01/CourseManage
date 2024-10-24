package com.javaweb.course.service;

import com.javaweb.course.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<?> resolveLogin(UserDTO userDTO);
}
