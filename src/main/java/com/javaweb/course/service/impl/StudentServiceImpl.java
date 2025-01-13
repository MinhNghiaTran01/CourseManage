package com.javaweb.course.service.impl;

import com.javaweb.course.entity.Student;
import com.javaweb.course.entity.User;
import com.javaweb.course.repository.StudentRepository;
import com.javaweb.course.repository.UserRepository;
import com.javaweb.course.service.StudentService;
import com.javaweb.course.untils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User user = userRepository.findById(student.getId()).orElse(null);
        user.setUpdate_at(Helper.getNowMillisAtUtc());
        student.setUser(user);
        user.setStudent(student);
        userRepository.save(user);
        studentRepository.save(student);
    }

    @Override
    public Student findById(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }
}
