//package com.javaweb.course.service;
//
//import com.javaweb.course.entity.Course;
//import com.javaweb.course.repository.CourseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//@Component
//public class Lab implements ApplicationRunner {
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Override
//    @Transactional
//    public void run(ApplicationArguments args) throws Exception {
//        Course course = new Course();
//        course.setCourseName("TEST");
//        courseRepository.save(course);
//        System.out.println("absd");
//        throw new RuntimeException("abc");
//    }
//}
