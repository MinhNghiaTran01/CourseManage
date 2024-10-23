package com.javaweb.course.security.oauth2;

import com.javaweb.course.entity.Role;
import com.javaweb.course.entity.User;
import com.javaweb.course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CustomerOAuth2UserService extends DefaultOAuth2UserService {

//    @Autowired
//    private UserRepository userRepository;

//    public Set<Role> setRoleUser(){
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role();
//        role.setName("USER");
//        roles.add(role);
//        return roles;
//    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String email = (String) attributes.get("email");
//
//        User user = userRepository.findByUsername(email);
//        if(user == null){
//            user = new User();
//            user.setRoles(setRoleUser());
//        }
        return new CustomerOAuth2User(super.loadUser(userRequest));
    }
}
