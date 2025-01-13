package com.javaweb.course.repository;

import com.javaweb.course.entity.PaymentVnpay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentVnpayRepository extends JpaRepository<PaymentVnpay, Integer> {
    PaymentVnpay findByTransactionId(String transactionId);
}
