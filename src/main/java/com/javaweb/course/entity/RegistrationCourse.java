package com.javaweb.course.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "registration_course")
@Getter
@Setter
@NoArgsConstructor
public class RegistrationCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long registrationDate;

    private String state;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    @ToString.Exclude
    private Course course;

    @OneToOne
    @JoinColumn(name = "payment_vnpay_id", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private PaymentVnpay paymentVnpay;
}
