package com.javaweb.course.controller.web;

import com.javaweb.course.model.dto.MyUserDetails;
import com.javaweb.course.model.dto.AuthDTO;
import com.javaweb.course.model.dto.UserDTO;
import com.javaweb.course.model.respone.AuthResponse;
import com.javaweb.course.security.jwt.JwtTokenUtil;
import com.javaweb.course.service.AuthService;
import com.javaweb.course.service.UserService;
import com.javaweb.course.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired JwtTokenUtil jwtUtil;
    @Autowired UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDTO userDTO) {
        return authService.resolveLogin(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO userDTO) {
        try {
            boolean success = userService.userRegister(userDTO);

            if(success) {
                userDTO = userService.getUserByUserName(userDTO.getUsername());
                String accessToken = jwtUtil.generateAccessToken(userDTO);
                AuthResponse response = new AuthResponse(userDTO.getUsername(), accessToken);
                return ResponseEntity.ok().body(response);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registration failed");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
