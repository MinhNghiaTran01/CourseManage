package com.javaweb.course.security.handeler;

import com.javaweb.course.security.oauth2.CustomerOAuth2User;
import com.javaweb.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomerOAuth2User oauthUser = (CustomerOAuth2User) authentication.getPrincipal();
        userService.processOAuth2PostLogin(oauthUser);
        // thực hiện chuyển hướng người dùng đến URL ban đầu được lưu trong session
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
