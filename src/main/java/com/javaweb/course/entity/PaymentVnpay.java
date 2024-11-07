package com.javaweb.course.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class PaymentVnpay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "payment_state", nullable = false)
    private String paymentState;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;


    @Column(name = "payment_date", nullable = false)
    private Long paymentDate;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "bank_code")
    private String bankCode;

    @OneToOne(mappedBy = "paymentVnpay", cascade = CascadeType.ALL)
    @ToString.Exclude
    private RegistrationCourse registrationCourse;

}
