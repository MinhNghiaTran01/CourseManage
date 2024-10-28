package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Role;
import com.javaweb.course.entity.User;
import com.javaweb.course.enums.AuthProvider;
import com.javaweb.course.model.dto.UserDTO;
import com.javaweb.course.repository.RoleRepository;
import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.security.oauth2.CustomerOAuth2User;
import com.javaweb.course.service.UserService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void processOAuth2PostLogin(CustomerOAuth2User oauthUser) {
        User existUser = userRepository.findByUsername(oauthUser.getName());

        Collection<? extends GrantedAuthority> authorities = oauthUser.getAuthorities();
        LOGGER.info(authorities.size() + " authorities");
        for (GrantedAuthority authority : authorities) {
            LOGGER.info("Authority: " + authority.getAuthority());
        }

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(oauthUser.getName());
            newUser.setAuthProvider(AuthProvider.GOOGLE);
            newUser.setStatus(true);
            newUser.setSub(oauthUser.getSub());
            userRepository.save(newUser);
        }
    }


    public Set<Role> generateRole(){
        Role role = roleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }

    @Override
    public boolean userRegister(UserDTO userDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        User user = modelMapper.map(userDTO, User.class);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setRoles(generateRole());
        try {
            User savedUser = userRepository.save(user);
            return savedUser != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean resgisterAccountGoogle(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setAuthProvider(AuthProvider.LOCAL);
        user.setRoles(generateRole());
        try {
            User savedUser = userRepository.save(user);
            return savedUser != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public UserDTO getUserByUserName(String username) {
        try {
            User user = userRepository.findByUsername(username);
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return userDTO;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkRegisterd(UserDTO userDTO) {

        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            return false;
        }
        else{
            return true;
        }
    }

}
