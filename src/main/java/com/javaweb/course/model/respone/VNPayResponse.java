package com.javaweb.course.model.respone;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VNPayResponse {
    private String code;
    private String message;
    private String paymentUrl;
    private String vnpAmount;
    private String vnpBankCode;
    private String vnpBankTranNo;
    private String vnpCardType;
    private String vnpOrderInfo;
    private String vnpPayDate;
    private String vnpResponseCode;
    private String vnpTmnCode;
    private String vnpTransactionNo;
    private String vnpTransactionStatus;
    private String vnpTxnRef;
    private String vnpSecureHash;

}