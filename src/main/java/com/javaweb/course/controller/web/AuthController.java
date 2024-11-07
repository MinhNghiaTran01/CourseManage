package com.javaweb.course.controller.web;

import com.javaweb.course.model.dto.MyUserDetails;
import com.javaweb.course.model.dto.AuthDTO;
import com.javaweb.course.model.dto.ProfileDTO;
import com.javaweb.course.model.dto.UserDTO;
import com.javaweb.course.model.respone.AuthResponse;
import com.javaweb.course.security.jwt.JwtTokenUtil;
import com.javaweb.course.service.AuthService;
import com.javaweb.course.service.UserService;
import com.javaweb.course.service.impl.AuthServiceImpl;
import org.modelmapper.ModelMapper;
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

@RequestMapping("/user/auth")
@RestController
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired JwtTokenUtil jwtUtil;
    @Autowired UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserDTO userDTO) {
        return authService.resolveLogin(userDTO);
    }

    @PostMapping("/google-login")
    public ResponseEntity<?> googleLogin(@RequestBody ProfileDTO profileDTO) {
        try {
            if(profileDTO!=null){
                UserDTO userDTO = UserDTO.builder()
                                .username(profileDTO.getEmail())
                                        .sub(profileDTO.getId())
                                                .build();
                boolean isRegisterd = userService.checkRegisterd(userDTO);

                if(!isRegisterd) {
                    userService.resgisterAccountGoogle(userDTO);
                }
                userDTO = userService.getUserByUserName(userDTO.getUsername());
                String accessToken = jwtUtil.generateAccessToken(userDTO);
                AuthResponse response = new AuthResponse(userDTO.getId(), userDTO.getUsername(), userDTO.getUsername(), accessToken);
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

            if(success) {
                userDTO = userService.getUserByUserName(userDTO.getUsername());
                String accessToken = jwtUtil.generateAccessToken(userDTO);
                AuthResponse response = new AuthResponse(userDTO.getId(), userDTO.getUsername(), userDTO.getUsername(), accessToken);
                return ResponseEntity.ok().body(response);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registration failed");
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
