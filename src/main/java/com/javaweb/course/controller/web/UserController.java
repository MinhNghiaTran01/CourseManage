package com.javaweb.course.controller.web;

import com.javaweb.course.entity.Student;
import com.javaweb.course.entity.User;
import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String index() {
        User user = new User();
        user.setUsername("name");
        Student student = new Student();
        student.setAddress("Hanoi");
        student.setUser(user);
        user.setStudent(student);
        userRepository.save(user);
        return "hello";
    }

    @PostMapping(value = "/upload")
    public String solveUpload(@RequestParam("file") MultipartFile file) {
        return "hello";
    }
}
