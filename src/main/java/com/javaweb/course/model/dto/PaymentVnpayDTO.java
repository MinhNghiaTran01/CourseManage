package com.javaweb.course.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentVnpayDTO {
    private String paymentMethod;
    private Long amount;
    private String paymentState;
    private String transactionId;
    private Long paymentDate;
    private String bankCode;
    private String orderId;
    private String emailRegisterCourse;
    private Integer userId;
    private String userName;
}
