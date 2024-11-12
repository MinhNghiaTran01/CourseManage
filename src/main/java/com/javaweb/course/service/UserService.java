package com.javaweb.course.service;

import com.javaweb.course.entity.User;
import com.javaweb.course.model.dto.UserDTO;
//import com.javaweb.course.security.oauth2.CustomerOAuth2User;

import javax.validation.Valid;
import java.util.List;


public interface UserService {
//    public void processOAuth2PostLogin(CustomerOAuth2User oauthUser);

    public boolean userRegister(@Valid UserDTO userDTO);

    public User getUserByUserName(String username);

    public boolean resgisterAccountGoogle(UserDTO userDTO);

    public boolean checkRegisterd(UserDTO userDTO);

    public User getUserById(int id);

    public void saveUser(User user);

    public List<User> getAll();

    void createUser(User user);

    void update(User user);

    User findByUsername(String username);

    void delete(User user);

    User findById(Integer id);
}
