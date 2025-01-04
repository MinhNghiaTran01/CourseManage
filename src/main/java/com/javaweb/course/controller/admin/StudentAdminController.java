package com.javaweb.course.controller.admin;

import com.javaweb.course.entity.Role;
import com.javaweb.course.entity.Student;
import com.javaweb.course.entity.User;
import com.javaweb.course.enums.State;
import com.javaweb.course.model.dto.StudentDTO;
import com.javaweb.course.model.respone.StudentResponse;
import com.javaweb.course.service.RoleService;
import com.javaweb.course.service.StudentService;
import com.javaweb.course.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/v1/admin/student")
@RestController
public class StudentAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;


    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        List<Student> students = studentService.getAll();
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (Student student : students) {
            User user = student.getUser();
            if(user.getState()== State.INACTIVE||user.getState()==State.DELETED) continue;
            Set<Role> roles = user.getRoles();
            StringBuilder roleNameBuilder = new StringBuilder();
            roles.forEach(role -> {
                roleNameBuilder.append(role.getName().substring(5)).append(", ");
            });

            String roleName = roleNameBuilder.length() > 0
                    ? roleNameBuilder.substring(0, roleNameBuilder.length() - 2)
                    : "";

            StudentResponse studentResponse = StudentResponse
                    .builder()
                    .id(student.getId())
                    .phoneNumber(student.getPhoneNumber() != null ? student.getPhoneNumber() : "")
                    .address(student.getAddress() != null ? student.getAddress() : "")
                    .fullName(student.getFullName() != null ? student.getFullName() : "")
                    .totalAmountPaid(student.getTotalAmountPaid())
                    .totalCourseRegistered(student.getTotalCourseRegistered())
                    .username(user.getUsername() != null ? user.getUsername() : "")
                    .roles(roleName)
                    .build();
            studentResponseList.add(studentResponse);

        }
        return ResponseEntity.ok().body(studentResponseList);
    }

//    @PostMapping
//    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {
//        try {
//            Set<Role> roles = userDTO.getRoles().stream().map(roleName -> {
//                Role role = roleRepository.findByName("ROLE_" + roleName);
//                return role;
//            }).collect(Collectors.toSet());
//            User user = modelMapper.map(userDTO, User.class);
//            user.setRoles(roles);
//
//            userService.createUser(user);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
//        }
//    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody StudentDTO studentDTO) {
        try {
            Set<Role> roles = studentDTO.getRoles().stream().map(roleName -> {
                Role role = roleService.findByName("ROLE_" + roleName);
                return role;
            }).collect(Collectors.toSet());

            User user = userService.findByUsername(studentDTO.getUsername());
            user.setRoles(roles);

            Student existingStudent = studentService.findById(user.getId());
            if (existingStudent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
            }
            modelMapper.map(studentDTO, existingStudent);

            existingStudent.setUser(user);
            user.setStudent(existingStudent);

            studentService.update(existingStudent);
            userService.update(user);
            return ResponseEntity.status(HttpStatus.OK).body("Update successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    e.getCause() != null ? e.getCause().getMessage() : e.getMessage()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        User user = userService.findById(Integer.valueOf(id));
        if (user != null) {
            user.setState(State.DELETED);
            userService.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body("Delete successful");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }
    }

}
