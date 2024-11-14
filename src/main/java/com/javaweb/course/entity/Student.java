package com.javaweb.course.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "user_id")
    private Integer id;  

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

    private String facebook;

    private String github;

    private String linkedin;

    @OneToOne
    @MapsId 
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;


}
