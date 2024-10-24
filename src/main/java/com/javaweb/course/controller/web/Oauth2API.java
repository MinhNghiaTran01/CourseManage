package com.javaweb.course.controller.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/success")
public class Oauth2API {

    @GetMapping
    public String success() {
        return "success";
    }
}
