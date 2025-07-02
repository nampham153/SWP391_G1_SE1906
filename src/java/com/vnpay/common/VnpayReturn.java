package com.vnpay.common;

import dao.CustomerOrderDAO;
import model.CustomerOrder;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/vnpay_return")
public class VnpayReturn extends HttpServlet {

    private final CustomerOrderDAO orderDao = new CustomerOrderDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        System.out.println("🔁 [VNPayReturn] Servlet triggered.");

        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(fieldName, fieldValue);
                System.out.println("📦 Param: " + fieldName + " = " + fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        String signValue = Config.hashAllFields(fields);
        System.out.println("🔐 Calculated sign = " + signValue);
        System.out.println("🔐 Received sign   = " + vnp_SecureHash);

        boolean isSuccess = false;

        if (signValue.equals(vnp_SecureHash)) {
            System.out.println("✅ [Signature OK] Processing transaction...");

            String orderIdStr = request.getParameter("vnp_TxnRef");
            String vnpStatus = request.getParameter("vnp_TransactionStatus");

            try {
                int orderId = Integer.parseInt(orderIdStr);
                CustomerOrder order = new CustomerOrder();
                order.setOrderId(orderId);

                if ("00".equals(vnpStatus)) {
                    order.setOrderStatus(1); // Thành công
                    isSuccess = true;
                    System.out.println("✅ Transaction Success: OrderId = " + orderId);
                } else {
                    order.setOrderStatus(2); // Thất bại
                    System.out.println("❌ Transaction Failed: OrderId = " + orderId + ", Status = " + vnpStatus);
                }

                System.out.println("🛠️ Updating Order: " + order.getOrderId() + " → Status = " + order.getOrderStatus());
                orderDao.updateOrderStatus(order);

            } catch (NumberFormatException e) {
                System.err.println("⚠️ Lỗi parse orderId: " + orderIdStr);
                e.printStackTrace();
            }

        } else {
            System.err.println("❌ Chữ ký không hợp lệ (VNPay SecureHash mismatch)");
        }

        request.setAttribute("transResult", isSuccess);
        request.getRequestDispatcher("/paymentResult.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles VNPay return and updates CustomerOrder status.";
    }
}
