package com.javaweb.course.controller.web;

import com.javaweb.course.entity.Student;
import com.javaweb.course.model.dto.StudentDTO;
import com.javaweb.course.model.respone.StudentResponse;
import com.javaweb.course.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    private ResponseEntity<StudentResponse> findById(@RequestParam Integer id) {
        Student student = studentService.findById(id);
        StudentResponse studentResponse = StudentResponse.builder()
                .fullName(student.getFullName())
                .facebook(student.getFacebook())
                .github(student.getGithub())
                .description(student.getDescription())
                .linkedin(student.getLinkedin())
                .address(student.getAddress())
                .phoneNumber(student.getPhoneNumber())
                .totalAmountPaid(student.getTotalAmountPaid())
                .totalCourseRegistered(student.getTotalCourseRegistered())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        student.setId(id);
        studentService.update(student);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }
}
