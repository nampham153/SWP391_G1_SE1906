/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.common;
import dao.AccountDAO;
import model.Account;
import Until.EmailUtil;
import dao.CustomerDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Random;
/**
 *
 * @author namp0
 */
@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageContent1", "forgotPassword.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");

        Account acc = accountDAO.getAccountByPhone(phone);
        if (acc == null) {
            request.setAttribute("error", "Không tìm thấy tài khoản với số điện thoại này.");
            request.setAttribute("pageContent1", "forgotPassword.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        String email = customerDAO.getEmailByPhone(phone);
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Tài khoản chưa cập nhật email. Vui lòng liên hệ CSKH.");
            request.setAttribute("pageContent1", "forgotPassword.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        String newPassword = generateRandomPassword(8);
        boolean updated = accountDAO.updatePasswordByPhone(phone, newPassword);
        if (!updated) {
            request.setAttribute("error", "Lỗi khi cập nhật mật khẩu. Vui lòng thử lại.");
            request.setAttribute("pageContent1", "forgotPassword.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        boolean sent = EmailUtil.sendOTP(email, "Mật khẩu mới của bạn là: " + newPassword);
        if (sent) {
            request.setAttribute("message", "Mật khẩu mới đã được gửi tới email của bạn.");
        } else {
            request.setAttribute("error", "Gửi email thất bại.");
        }

        request.setAttribute("pageContent1", "forgotPassword.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
