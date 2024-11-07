//package com.javaweb.course.security.oauth2;
//
//import com.javaweb.course.entity.Role;
//import com.javaweb.course.entity.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//
//import java.util.*;
//
//public class CustomerOAuth2User implements OAuth2User {
//
//    private OAuth2User oauth2User;
//    private String oauth2ClientName;
//
//    public CustomerOAuth2User(OAuth2User oauth2User, String oauth2ClientName) {
//        this.oauth2User = oauth2User;
//        this.oauth2ClientName = oauth2ClientName;
//    }
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return oauth2User.getAttributes();
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        oauth2User.getAuthorities().forEach(ga -> authorities.add(ga));
//        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//        return authorities;
//    }
//
//    @Override
//    public String getName() {
//        return oauth2User.getAttribute("name");
//    }
//
//    public String getEmail() {
//        return oauth2User.<String>getAttribute("email");
//    }
//
//    public String getSub() {
//        return oauth2User.<String>getAttribute("sub");
//    }
//
//    public OAuth2User getOauth2User() {
//        return oauth2User;
//    }
//
//    public void setOauth2User(OAuth2User oauth2User) {
//        this.oauth2User = oauth2User;
//    }
//
//    public String getOauth2ClientName() {
//        return this.oauth2ClientName;
//    }
//}
