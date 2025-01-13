package com.javaweb.course.service;

import com.javaweb.course.entity.PaymentVnpay;
import com.javaweb.course.model.dto.PaymentVnpayDTO;
import com.javaweb.course.model.respone.ResponseObject;
import com.javaweb.course.model.respone.VNPayResponse;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PaymentVnPayService {
    public VNPayResponse createVnPayPayment(HttpServletRequest request) throws UnsupportedEncodingException;

    public Boolean checkSum(HttpServletRequest request) throws UnsupportedEncodingException;

    boolean checkTransactionState(HttpServletRequest request) throws UnsupportedEncodingException;

    Boolean save(PaymentVnpayDTO paymentVnpayDTO);

    PaymentVnpay findById(Integer id);

//    List<PaymentVnpayDTO> findAll(Integer userId);
    ResponseObject<?> servicePayCallbackHandler(HttpServletRequest request);

    List<PaymentVnpay> findAll();
}

