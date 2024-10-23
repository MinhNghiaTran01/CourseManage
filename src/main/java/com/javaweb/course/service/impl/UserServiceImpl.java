package com.javaweb.course.service.impl;

import com.javaweb.course.entity.User;
import com.javaweb.course.enums.AuthProvider;
import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.security.oauth2.CustomerOAuth2User;
import com.javaweb.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void processOAuthPostLogin(CustomerOAuth2User oauthUser) {
        User existUser = userRepository.findByUsername(oauthUser.getName());

        Collection<? extends GrantedAuthority> authorities = oauthUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            System.out.println("Authority: " + authority.getAuthority());
        }

        if (existUser == null) {
            User newUser = new User();
            newUser.setUserName(oauthUser.getName());
            newUser.setProvider(AuthProvider.GOOGLE);
            newUser.setEnable(true);
//            newUser.setSub(oauthUser.getSub());
//            newUser.setRoles(oauthUser.getUser().getRoles());
            userRepository.save(newUser);
        }
    }
}
