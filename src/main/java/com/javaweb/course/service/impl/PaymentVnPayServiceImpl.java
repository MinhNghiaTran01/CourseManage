package com.javaweb.course.service.impl;

import com.javaweb.course.config.VNPAYConfig;
import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.PaymentVnpay;
import com.javaweb.course.entity.Student;
import com.javaweb.course.model.dto.PaymentVnpayDTO;
import com.javaweb.course.model.dto.RegistrationCourseDTO;
import com.javaweb.course.model.respone.ResponseObject;
import com.javaweb.course.model.respone.VNPayResponse;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.repository.PaymentVnpayRepository;
import com.javaweb.course.repository.StudentRepository;
import com.javaweb.course.service.PaymentVnPayService;
import com.javaweb.course.service.RegistrationCourseService;
import com.javaweb.course.utils.VNPayUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentVnPayServiceImpl implements PaymentVnPayService {

    private final VNPAYConfig vnPayConfig;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentVnpayRepository paymentVnpayRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationCourseService registrationCourseService;

    public VNPayResponse createVnPayPayment(HttpServletRequest request) throws UnsupportedEncodingException {

        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();

        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);

        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return VNPayResponse.builder()
                .message("success")
                .paymentUrl(paymentUrl).build();

    }

    public Boolean checkSum(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> fields = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            if (values.length > 0 && values[0] != null && !values[0].isEmpty()) {
                fields.put(URLEncoder.encode(key, StandardCharsets.US_ASCII), URLEncoder.encode(values[0], StandardCharsets.US_ASCII));
            }
        });
        fields.remove("courseId");
        fields.remove("userId");
        fields.remove("emailRegisterCourse");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        String signValue = VNPayUtil.hashAllFields(fields);

        if (signValue.equals(vnp_SecureHash)) {
            // Kiểm tra trạng thái giao dịch
            return true;

        } else {
            return false;
        }
    }

    public ResponseObject<VNPayResponse> checkTransactionState(HttpServletRequest request) throws UnsupportedEncodingException {
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        if ("00".equals(transactionStatus)) {
            return new ResponseObject<>(HttpStatus.OK, "Success", VNPayResponse.builder()
                    .message("Thành công")
                    .vnpAmount(request.getParameter("vnp_Amount"))
                    .vnpBankCode(request.getParameter("vnp_BankCode"))
                    .vnpBankTranNo(request.getParameter("vnp_BankTranNo"))
                    .vnpCardType(request.getParameter("vnp_CardType"))
                    .vnpOrderInfo(request.getParameter("vnp_OrderInfo"))
                    .vnpPayDate(request.getParameter("vnp_PayDate"))
                    .vnpResponseCode(request.getParameter("vnp_ResponseCode"))
                    .vnpTransactionNo(request.getParameter("vnp_TransactionNo"))
                    .vnpTransactionStatus(request.getParameter("vnp_TransactionStatus"))
                    .vnpSecureHash(request.getParameter("vnp_SecureHash"))
                    .vnpTmnCode(request.getParameter("vnp_TmnCode"))
                    .vnpTxnRef(request.getParameter("vnp_TxnRef"))
                    .build());
        } else {
            // Giao dịch không thành công
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Transaction Failed", null);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Boolean save(PaymentVnpayDTO paymentVnpayDTO) {
        try {
            PaymentVnpay paymentVnpay = paymentVnpayRepository.findByTransactionId(paymentVnpayDTO.getTransactionId());
            if (paymentVnpay == null) {
                paymentVnpay = modelMapper.map(paymentVnpayDTO, PaymentVnpay.class);
                paymentVnpayRepository.save(paymentVnpay);
                return true;
            } else return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PaymentVnpay findById(Integer id) {
        try {
            return paymentVnpayRepository.findById(id).orElse(null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseObject<?> servicePayCallbackHandler(HttpServletRequest request) {
        String emailRegisterCourse = request.getParameter("emailRegisterCourse");
        Integer userId = Integer.valueOf(request.getParameter("userId"));
        Integer courseId = Integer.valueOf(request.getParameter("courseId"));

        Student student = studentRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        if(student == null) {
            return new ResponseObject<>(HttpStatus.NOT_FOUND, "Student not found", HttpStatus.NOT_FOUND);
        }

        if(course == null) {
            return new ResponseObject<>(HttpStatus.NOT_FOUND, "Course not found", HttpStatus.NOT_FOUND);
        }

        boolean checksum = checkSum(request);
        PaymentVnpayDTO paymentVnpayDTO = PaymentVnpayDTO.builder()
                .userId(userId)
                .paymentMethod(request.getParameter("vnp_CardType"))
                .amount(Long.parseLong(request.getParameter("vnp_Amount")))
                .paymentState(request.getParameter("vnp_TransactionStatus"))
                .transactionId(request.getParameter("vnp_TransactionNo"))
                .paymentDate(Long.parseLong(request.getParameter("vnp_PayDate")))
                .bankCode(request.getParameter("vnp_BankCode"))
                .orderId(request.getParameter("vnp_TxnRef"))
                .emailRegisterCourse(emailRegisterCourse)
                .build();

        RegistrationCourseDTO registrationCourseDTO = RegistrationCourseDTO.builder()
                .paymentTransactionId(request.getParameter("vnp_TransactionNo"))
                .registrationDate(Long.parseLong(request.getParameter("vnp_PayDate")))
                .userId(userId)
                .courseId(courseId)
                .build();


        VNPayResponse vnPayResponse = VNPayResponse.builder()
                .vnpAmount(request.getParameter("vnp_Amount"))
                .vnpBankCode(request.getParameter("vnp_BankCode"))
                .vnpBankTranNo(request.getParameter("vnp_BankTranNo"))
                .vnpCardType(request.getParameter("vnp_CardType"))
                .vnpOrderInfo(request.getParameter("vnp_OrderInfo"))
                .vnpPayDate(request.getParameter("vnp_PayDate"))
                .vnpResponseCode(request.getParameter("vnp_ResponseCode"))
                .vnpTmnCode(request.getParameter("vnp_TmnCode"))
                .vnpTransactionNo(request.getParameter("vnp_TransactionNo"))
                .vnpTransactionStatus(request.getParameter("vnp_TransactionStatus"))
                .vnpTxnRef(request.getParameter("vnp_TxnRef"))
                .vnpSecureHash(request.getParameter("vnp_SecureHash"))
                .code("00")
                .emailRegisterCourse(emailRegisterCourse)
                .message("Transaction successful")
                .build();

        checkTransactionState(request);

        if (!checksum) {
            return new ResponseObject<>(HttpStatus.FAILED_DEPENDENCY, "Checksum failed", null);
        }

        if (!save(paymentVnpayDTO)) {
            return new ResponseObject<>(HttpStatus.FAILED_DEPENDENCY, "paymentVnpayDTO failed", null);
        }

        if (!registrationCourseService.save(registrationCourseDTO)) {
            return new ResponseObject<>(HttpStatus.FAILED_DEPENDENCY, "registrationCourseDTO failed", null);
        }

        student.setTotalAmountPaid(course.getPrice() + student.getTotalAmountPaid());
        student.setTotalCourseRegistered(student.getTotalCourseRegistered() + 1L);
        studentRepository.save(student);
        return new ResponseObject<>(HttpStatus.OK, "Transaction successful", vnPayResponse);
    }

    @Override
    public List<PaymentVnpay> findAll() {
        return paymentVnpayRepository.findAll();
    }

//    @Override
//    public List<PaymentVnpayDTO> findAll(Integer userId) {
//        List<PaymentVnpay> paymentVnpays = paymentVnpayRepository.findB
//    }
}