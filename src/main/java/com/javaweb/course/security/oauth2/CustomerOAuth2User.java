package com.javaweb.course.security.oauth2;

import com.javaweb.course.entity.Role;
import com.javaweb.course.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class CustomerOAuth2User implements OAuth2User {

    private OAuth2User oauth2User;
    private User user;

    public CustomerOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
//        this.user = user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.<String>getAttribute("email");
    }

    public String getSub() {
        return oauth2User.<String>getAttribute("sub");
    }

    public OAuth2User getOauth2User() {
        return oauth2User;
    }

    public void setOauth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
