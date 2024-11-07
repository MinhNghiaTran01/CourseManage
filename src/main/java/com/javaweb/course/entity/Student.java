package com.javaweb.course.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "user_id")
    private Integer id;  // Sử dụng trường userId vừa làm khóa chính, vừa là khóa ngoại

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount_paid")
    private Integer totalAmountPaid;

    @Column(name = "total_course_registered")
    private Integer totalCourseRegistered;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    private String fullName;

    @OneToOne
    @MapsId // Ánh xạ khóa chính với khóa ngoại (user_id)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;


}
