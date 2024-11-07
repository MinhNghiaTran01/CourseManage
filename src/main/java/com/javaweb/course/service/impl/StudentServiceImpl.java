package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Student;
import com.javaweb.course.repository.StudentRepository;
import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public void update(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }
}
