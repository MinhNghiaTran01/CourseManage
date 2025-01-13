package com.javaweb.course.controller.web;

import com.javaweb.course.entity.Course;
import com.javaweb.course.entity.PaymentVnpay;
import com.javaweb.course.entity.RegistrationCourse;
import com.javaweb.course.entity.Student;
import com.javaweb.course.model.dto.PaymentVnpayDTO;
import com.javaweb.course.model.dto.RegistrationCourseDTO;
import com.javaweb.course.model.respone.RegistrationCourseResponse;
import com.javaweb.course.model.respone.ResponseObject;
import com.javaweb.course.model.respone.StudentResponse;
import com.javaweb.course.model.respone.VNPayResponse;
import com.javaweb.course.repository.CourseRepository;
import com.javaweb.course.repository.StudentRepository;
import com.javaweb.course.service.PaymentVnPayService;
import com.javaweb.course.service.RegistrationCourseService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user/payment-vnpay")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentVnPayService paymentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RegistrationCourseService registrationCourseService;

    private Logger logger = Logger.getLogger(PaymentController.class);

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/vn-pay")
    public ResponseObject<VNPayResponse> pay(HttpServletRequest request) throws UnsupportedEncodingException {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<?> payCallbackHandler(HttpServletRequest request) throws UnsupportedEncodingException {
        return paymentService.servicePayCallbackHandler(request);

    }

//    @PostMapping
//    public ResponseEntity<String> save(@RequestBody PaymentVnpayDTO paymentVnpayDTO) {
//        if (paymentService.save(paymentVnpayDTO)) {
//            return ResponseEntity.status(HttpStatus.CREATED).body("Tạo thành công");
//        } else {
//            return new ResponseEntity<>("Failed to save PaymentVnpayDTO. Please check the data and try again."
//                    , HttpStatus.BAD_REQUEST);
//        }
//    }


    @GetMapping("payments")
    public ResponseEntity<List<PaymentVnpayDTO>> findPayments(@RequestParam Integer userId) {
        List<RegistrationCourse> registrationCourses = registrationCourseService.findByUserId(userId);

        List<PaymentVnpayDTO> paymentVnpayDTOs = registrationCourses.stream().map(registrationCourse -> {
            PaymentVnpay paymentVnpay = paymentService.findById(registrationCourse.getPaymentVnpay().getId());
            return PaymentVnpayDTO.builder().paymentDate(paymentVnpay.getPaymentDate())
                    .paymentState(paymentVnpay.getPaymentState())
                    .emailRegisterCourse(paymentVnpay.getEmailRegisterCourse())
                    .amount(paymentVnpay.getAmount())
                    .bankCode(paymentVnpay.getBankCode())
                    .transactionId(paymentVnpay.getTransactionId())
                    .build();
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(paymentVnpayDTOs);
    }
}