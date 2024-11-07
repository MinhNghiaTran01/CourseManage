package com.javaweb.course.service.impl;

import com.javaweb.course.config.VNPAYConfig;
import com.javaweb.course.entity.PaymentVnpay;
import com.javaweb.course.model.dto.PaymentVnpayDTO;
import com.javaweb.course.model.respone.ResponseObject;
import com.javaweb.course.model.respone.VNPayResponse;
import com.javaweb.course.repository.PaymentVnpayRepository;
import com.javaweb.course.service.PaymentVnPayService;
import com.javaweb.course.utils.VNPayUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentVnPayServiceImpl implements PaymentVnPayService {

    private final VNPAYConfig vnPayConfig;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PaymentVnpayRepository paymentVnpayRepository;

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
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");
        fields.remove("courseId");
        fields.remove("userId");
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
    public Boolean save(PaymentVnpayDTO paymentVnpayDTO) {
        try {
            PaymentVnpay paymentVnpay = paymentVnpayRepository.findByTransactionId(paymentVnpayDTO.getTransactionId());
            if(paymentVnpay == null) {
                paymentVnpay = modelMapper.map(paymentVnpayDTO, PaymentVnpay.class);
                paymentVnpayRepository.save(paymentVnpay);
                return true;
            }
            else return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}