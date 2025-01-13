package com.javaweb.course.service;

import com.javaweb.course.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAll();

    void update(Student student);

    Student findById(Integer id);
}
