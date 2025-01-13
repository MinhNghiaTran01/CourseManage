package com.javaweb.course.controller.admin;

import com.javaweb.course.entity.PaymentVnpay;
import com.javaweb.course.entity.RegistrationCourse;
import com.javaweb.course.entity.User;
import com.javaweb.course.model.dto.PaymentVnpayAdminDTO;
import com.javaweb.course.model.dto.PaymentVnpayDTO;
import com.javaweb.course.service.PaymentVnPayService;
import com.javaweb.course.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("v1/admin/paymentVnpay")
public class PaymentVnPayAdminController {

    @Autowired
    private PaymentVnPayService paymentVnPayService;

    @Autowired
    private UserService userService;

    @GetMapping("payments")
    public ResponseEntity<List<PaymentVnpayAdminDTO>> findPayments() {

        List<PaymentVnpay> paymentVnpayList = paymentVnPayService.findAll();
        List<PaymentVnpayAdminDTO> paymentVnpayAdminDTOS = new ArrayList<>();
        for(PaymentVnpay paymentVnpay : paymentVnpayList){
            User user = userService.findById(paymentVnpay.getUserId());

            PaymentVnpayAdminDTO paymentVnpayAdminDTO = PaymentVnpayAdminDTO.builder().paymentDate(paymentVnpay.getPaymentDate())
                    .paymentState(paymentVnpay.getPaymentState())
                    .emailRegisterCourse(paymentVnpay.getEmailRegisterCourse())
                    .amount(paymentVnpay.getAmount())
                    .bankCode(paymentVnpay.getBankCode())
                    .transactionId(paymentVnpay.getTransactionId())
                    .userName(user.getUsername())
                    .build();
            paymentVnpayAdminDTOS.add(paymentVnpayAdminDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(paymentVnpayAdminDTOS);
    }
}
