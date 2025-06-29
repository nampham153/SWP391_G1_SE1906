package com.vnpay.common;

import dao.CustomerOrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/vnpay_return")
public class VnPayReturnServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String orderId = request.getParameter("orderId");
        String responseCode = request.getParameter("vnp_ResponseCode");

        if ("00".equals(responseCode)) {
            CustomerOrderDAO orderDAO = new CustomerOrderDAO();
            orderDAO.updateOrderStatus(Integer.parseInt(orderId), 2);
            response.sendRedirect("order-success.jsp");
        } else {
            response.sendRedirect("order-fail.jsp");
        }
    }
}
