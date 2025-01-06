package com.javaweb.course.controller.web;

import com.javaweb.course.entity.User;
import com.javaweb.course.model.dto.ProfileDTO;
import com.javaweb.course.model.dto.UserDTO;
import com.javaweb.course.model.respone.AuthResponse;
import com.javaweb.course.security.jwt.JwtTokenUtil;
import com.javaweb.course.service.AuthService;
import com.javaweb.course.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/user/auth")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    JwtTokenUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDTO userDTO) {
        return authService.resolveLogin(userDTO);
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody ProfileDTO profileDTO) {
        try {
            if (profileDTO != null) {
                UserDTO userDTO = UserDTO.builder()
                        .username(profileDTO.getEmail())
                        .sub(profileDTO.getId())
                        .build();
                boolean isRegisterd = userService.checkRegisterd(userDTO);

                if (!isRegisterd) {
                    userService.resgisterAccountGoogle(userDTO,profileDTO.getName());
                }
                User user = userService.getUserByUserName(userDTO.getUsername());
                String accessToken = jwtUtil.generateAccessToken(userDTO);
                AuthResponse response = new AuthResponse(user.getId(), user.getUsername(), user.getUsername(), accessToken, user.getRoles());
                return ResponseEntity.ok().body(response);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registration Account Google failed");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO userDTO) {
        try {
            boolean success = userService.userRegister(userDTO);

            if (success) {
                User user = userService.getUserByUserName(userDTO.getUsername());
                String accessToken = jwtUtil.generateAccessToken(userDTO);
                AuthResponse response = new AuthResponse(user.getId(), user.getUsername(), user.getUsername(), accessToken,user.getRoles());
                return ResponseEntity.ok().body(response);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registration failed");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
