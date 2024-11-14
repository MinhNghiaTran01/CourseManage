package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Role;
import com.javaweb.course.entity.User;
import com.javaweb.course.enums.AuthProvider;
import com.javaweb.course.model.dto.UserDTO;
import com.javaweb.course.repository.RoleRepository;
import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.service.UserService;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

//    @Override
//    public void processOAuth2PostLogin(CustomerOAuth2User oauthUser) {
//        User existUser = userRepository.findByUsername(oauthUser.getName());
//
//        Collection<? extends GrantedAuthority> authorities = oauthUser.getAuthorities();
//        LOGGER.info(authorities.size() + " authorities");
//        for (GrantedAuthority authority : authorities) {
//            LOGGER.info("Authority: " + authority.getAuthority());
//        }
//
//        if (existUser == null) {
//            User newUser = new User();
//            newUser.setUsername(oauthUser.getName());
//            newUser.setAuthProvider(AuthProvider.GOOGLE);
//            newUser.setStatus(true);
//            newUser.setSub(oauthUser.getSub());
//            userRepository.save(newUser);
//        }
//    }


    public Set<Role> generateRole() {
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
    public User getUserByUserName(String username) {
        try {
            User user = userRepository.findByUsername(username);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkRegisterd(UserDTO userDTO) {

        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void delete(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
