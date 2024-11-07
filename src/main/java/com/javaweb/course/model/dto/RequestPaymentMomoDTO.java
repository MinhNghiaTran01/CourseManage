//package com.javaweb.course.model.dto;
//
//import lombok.Data;
//
//import java.util.Date;
//
//@Data
//public class RequestPaymentMomoDTO {
//    private String accessKey = "F8BBA842ECF85";
//    private String secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
//    private String orderInfo = "pay with MoMo";
//    private String partnerCode = "MOMO";
//    private String redirectUrl = "https://webhook.site/b3088a6a-2d17-4f8d-a383-71389a6c600b";
//    private String ipnUrl = "https://webhook.site/b3088a6a-2d17-4f8d-a383-71389a6c600b";
//    private String requestType = "payWithMethod";
//    private Long amount = 50000L;
//    private String orderId = partnerCode + new Date().getTime();
//    private String requestId = orderId;
//    private String extraData = "";
//    private String paymentCode = "T8Qii53fAXyUftPV3m9ysyRhEanUs9KlOPfHgpMR0ON50U10Bh+vZdpJU7VY4z+Z2y77fJHkoDc69scwwzLuW5MzeUKTwPo3ZMaB29imm6YulqnWfTkgzqRaion+EuD7FN9wZ4aXE1+mRt0gHsU193y+yxtRgpmY7SDMU9hCKoQtYyHsfFR5FUAOAKMdw2fzQqpToei3rnaYvZuYaxolprm9+/+WIETnPUDlxCYOiw7vPeaaYQQH0BF0TxyU3zu36ODx980rJvPAgtJzH1gUrlxcSS1HQeQ9ZaVM1eOK/jl8KJm6ijOwErHGbgf/hVymUQG65rHU2MWz9U8QUjvDWA==";
//    private String orderGroupId = "";
//    private Boolean autoCapture = true;
//    private String lang = "vi";
//    private String rawSignature = "accessKey=" + accessKey + "&amount=" + amount + "&extraData=" + extraData + "&ipnUrl=" + ipnUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + partnerCode + "&redirectUrl=" + redirectUrl + "&requestId=" + requestId + "&requestType=" + requestType;
//
//}
