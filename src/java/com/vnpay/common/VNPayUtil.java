package com.vnpay.common;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

public class VNPayUtil {

    public static String buildRedirectUrl(HttpServletRequest request, int orderId, long amount) {
        try {
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String orderType = "other";
            long vnp_Amount = amount * 100;

            String vnp_TxnRef = String.valueOf(orderId);
            String vnp_IpAddr = Config.getIpAddress(request);
            String vnp_TmnCode = Config.vnp_TmnCode;

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
            vnp_Params.put("vnp_CurrCode", "VND");
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl + "?orderId=" + orderId);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            for (String name : fieldNames) {
                String value = vnp_Params.get(name);
                if (value != null && !value.isEmpty()) {
                    hashData.append(name).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
                    query.append(URLEncoder.encode(name, StandardCharsets.US_ASCII))
                         .append('=')
                         .append(URLEncoder.encode(value, StandardCharsets.US_ASCII))
                         .append('&');
                }
            }

            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            query.append("vnp_SecureHash=").append(vnp_SecureHash);
            return Config.vnp_PayUrl + "?" + query.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

