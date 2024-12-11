package com.javaweb.course.service.impl;

import com.javaweb.course.entity.User;
import com.javaweb.course.model.dto.MyUserDetails;
import com.javaweb.course.model.dto.UserDTO;
import com.javaweb.course.model.respone.AuthResponse;
import com.javaweb.course.security.jwt.JwtTokenUtil;
import com.javaweb.course.service.AuthService;
import com.javaweb.course.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtTokenUtil jwtUtil;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> resolveLogin(UserDTO userDTO) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(), userDTO.getPassword())
            );

            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

            userDTO = modelMapper.map(myUserDetails.getUser(), UserDTO.class);

            User user = userService.findByUsername(userDTO.getUsername());

            String accessToken = jwtUtil.generateAccessToken(userDTO);
            AuthResponse response = new AuthResponse(userDTO.getId(), userDTO.getUsername(), userDTO.getUsername(), accessToken,user.getRoles());

            return ResponseEntity.ok().body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
