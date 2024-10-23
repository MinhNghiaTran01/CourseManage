package com.javaweb.course.service;

import com.javaweb.course.security.oauth2.CustomerOAuth2User;


public interface UserService {
    public void processOAuthPostLogin(CustomerOAuth2User oauthUser);

}
