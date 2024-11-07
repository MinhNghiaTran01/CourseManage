package com.javaweb.course.service;

import com.javaweb.course.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAll();

    void update(Student student);

    Optional<Student> findById(Integer id);
}
