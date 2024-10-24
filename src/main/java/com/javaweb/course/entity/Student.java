package com.javaweb.course.entity;

import lombok.Data;

import javax.persistence.*;

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
    private Integer phoneNumber;
    private String address;

    @OneToOne
    @MapsId // Ánh xạ khóa chính với khóa ngoại (user_id)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


}
