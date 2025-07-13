/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import dao.AccountDAO;
import dao.AccountVerificationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AccountVerificationCode;

/**
 *
 * @author namp0
 */
@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageContent1", "verify.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = (String) request.getSession().getAttribute("pendingPhone");
        String code = request.getParameter("code");

        AccountVerificationDAO verificationDao = new AccountVerificationDAO();
        AccountVerificationCode stored = verificationDao.getVerificationCode(phone);

        if (stored != null) {
            if (code.equals(stored.getCode()) && System.currentTimeMillis() <= stored.getExpireAt().getTime()) {
                new AccountDAO().setAccountVerified(phone);
                request.getSession().removeAttribute("pendingPhone");
                response.sendRedirect("login");
                return;
            }
        }

        request.setAttribute("message", "Mã xác thực sai hoặc đã hết hạn.");
        request.setAttribute("pageContent1", "verify.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
