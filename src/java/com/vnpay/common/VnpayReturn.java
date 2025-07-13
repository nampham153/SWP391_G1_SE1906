package com.vnpay.common;

import context.DBContext;
import dao.CartDAO;
import dao.CartItemDAO;
import dao.CustomerOrderDAO;
import dao.ItemDAO;
import model.Cart;
import model.CartItem;
import model.CustomerOrder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/vnpay_return")
public class VnpayReturn extends HttpServlet {

    private final CustomerOrderDAO orderDao = new CustomerOrderDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        System.out.println(" [VNPayReturn] Servlet triggered.");

        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                fields.put(fieldName, fieldValue);
                System.out.println(" Param: " + fieldName + " = " + fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        String signValue = Config.hashAllFields(fields);
        boolean isSuccess = false;

        if (signValue.equals(vnp_SecureHash)) {
            System.out.println(" [Signature OK]");

            String orderIdStr = request.getParameter("vnp_TxnRef");
            String vnpStatus = request.getParameter("vnp_TransactionStatus");

            try {
                int orderId = Integer.parseInt(orderIdStr);
                CustomerOrder order = new CustomerOrder();
                order.setOrderId(orderId);

                if ("00".equals(vnpStatus)) {
                    try (Connection conn = new DBContext().getConnection()) {
                        conn.setAutoCommit(false);

                        List<CartItem> items = orderDao.getCartItemsOfOrder(orderId);

                        boolean success = orderDao.decreaseStockIfEnough(conn, items);
                        if (!success) {
                            conn.rollback();
                            throw new SQLException(" Không đủ hàng cho đơn hàng #" + orderId);
                        }

                        String customerId = orderDao.getOrderById(orderId).getCustomerId();
                        if (customerId != null) {
                            CartDAO cartDAO = new CartDAO();
                            CartItemDAO cartItemDAO = new CartItemDAO();
                            Cart cart = cartDAO.getCartByCustomerId(customerId);
                            if (cart != null) {
                                cartItemDAO.clearCart(cart.getCartId());
                                System.out.println("🧹 Đã xóa giỏ hàng của KH đăng nhập: " + customerId);
                            }
                        } else {
                            HttpSession session = request.getSession(false);
                            if (session != null) {
                                session.removeAttribute("cart");
                                System.out.println("🧹 Đã xóa giỏ hàng session của guest");
                            }
                        }

                        conn.commit();
                        order.setOrderStatus(1);
                        isSuccess = true;
                        System.out.println(" Đã xử lý đơn hàng VNPay #" + orderId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println(" Lỗi khi xử lý đơn hàng, rollback...");
                        order.setOrderStatus(2); 
                    }
                } else {
                    order.setOrderStatus(2); 
                    System.out.println(" VNPay trả về lỗi: OrderId = " + orderId + ", Status = " + vnpStatus);
                }

                orderDao.updateOrderStatus(order);

            } catch (NumberFormatException e) {
                System.err.println(" Lỗi định dạng OrderId: " + request.getParameter("vnp_TxnRef"));
                e.printStackTrace();
            }

        } else {
            System.err.println(" Chữ ký không hợp lệ (VNPay SecureHash mismatch)");
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
