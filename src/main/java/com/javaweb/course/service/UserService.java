package com.javaweb.course.service;

import com.javaweb.course.model.dto.UserDTO;
import com.javaweb.course.security.oauth2.CustomerOAuth2User;

import javax.validation.Valid;


public interface UserService {
    public void processOAuth2PostLogin(CustomerOAuth2User oauthUser);

    public boolean userRegister(@Valid UserDTO userDTO);

    @Valid UserDTO getUserByUserName(String username);
}
