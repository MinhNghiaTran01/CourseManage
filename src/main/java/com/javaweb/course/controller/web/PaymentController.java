package com.javaweb.course.controller.web;

import com.javaweb.course.model.dto.PaymentVnpayDTO;
import com.javaweb.course.model.dto.RegistrationCourseDTO;
import com.javaweb.course.model.respone.ResponseObject;
import com.javaweb.course.model.respone.VNPayResponse;
import com.javaweb.course.repository.RegistrationCourseRepository;
import com.javaweb.course.service.PaymentVnPayService;
import com.javaweb.course.service.RegistrationCourseService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("user/payment-vnpay")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private PaymentVnPayService paymentService;

    @Autowired
    private RegistrationCourseService registrationCourseService;

    private Logger logger = Logger.getLogger(PaymentController.class);

    @GetMapping("/vn-pay")
    public ResponseObject<VNPayResponse> pay(HttpServletRequest request) throws UnsupportedEncodingException {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }

    private String paymentMethod;
    private Long amount;
    private String paymentState;
    private String transactionId;
    private Long paymentDate;
    private String bankCode;
    private String orderId;

    @Transactional(rollbackOn = Exception.class)
    @GetMapping("/vn-pay-callback")
    public ResponseObject<?> payCallbackHandler(HttpServletRequest request) throws UnsupportedEncodingException {
        boolean checksum = paymentService.checkSum(request);
        PaymentVnpayDTO paymentVnpayDTO = PaymentVnpayDTO.builder()
                .paymentMethod(request.getParameter("vnp_CardType"))
                .amount(Long.parseLong(request.getParameter("vnp_Amount"))) // Chuyển đổi amount từ String sang Long
                .paymentState(request.getParameter("vnp_TransactionStatus"))
                .transactionId(request.getParameter("vnp_TransactionNo"))
                .paymentDate(Long.parseLong(request.getParameter("vnp_PayDate"))) // Chuyển đổi paymentDate từ String sang Long
                .bankCode(request.getParameter("vnp_BankCode"))
                .orderId(request.getParameter("vnp_TxnRef"))
                .build();
        RegistrationCourseDTO registrationCourseDTO = RegistrationCourseDTO.builder()
                .paymentTransactionId(request.getParameter("vnp_TransactionNo"))
                .registrationDate(Long.parseLong(request.getParameter("vnp_PayDate")))
                .userId(Integer.parseInt(request.getParameter("userId")))
                .courseId(Integer.parseInt(request.getParameter("courseId")))
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
                .code("00") // Đặt giá trị mặc định hoặc tùy chỉnh theo yêu cầu
                .message("Transaction successful") // Đặt thông báo mặc định hoặc tùy chỉnh
                .build();

        paymentService.checkTransactionState(request);
        if (!checksum) {
            return new ResponseObject<>(HttpStatus.FAILED_DEPENDENCY, "Checksum failed", null);
        }

        if (!paymentService.save(paymentVnpayDTO)) {
            throw new RuntimeException("Failed to save PaymentVnpayDTO");
        }

        if (!registrationCourseService.save(registrationCourseDTO)) {
            throw new RuntimeException("Failed to save RegistrationCourseDTO");
        }
        return new ResponseObject<>(HttpStatus.OK, "Transaction successful", vnPayResponse);
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

}