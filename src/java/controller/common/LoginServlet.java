/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import context.DBContext;
import dao.DAOWrapper;
import dao.StaffDAO;
import model.Account;
import model.Customer;
import model.Staff;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

/**
 *
 * @author namp0
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("valid", true);
        request.setAttribute("pageContent1", "login.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        Account account = DAOWrapper.accountDAO.getAccount(phone, password);
        if (account == null) {
            request.setAttribute("message", "Số điện thoại hoặc mật khẩu không đúng.");
            forwardToLogin(request, response);
            return;
        }

        if (account.getStatus() != 1) {
            request.setAttribute("message", "Tài khoản của bạn đã bị khóa.");
            forwardToLogin(request, response);
            return;
        }

        Customer customer = DAOWrapper.customerDAO.getCustomerById(phone);
        if (customer != null && !customer.getStatus()) {
            request.setAttribute("message", "Tài khoản của bạn đã bị khóa.");
            forwardToLogin(request, response);
            return;
        }

        try (Connection conn = new DBContext().getConnection()) {
            StaffDAO staffDAO = new StaffDAO(conn);
            Staff staff = staffDAO.getById(phone);

            if (staff != null && !staff.getStatus()) {
                request.setAttribute("message", "Tài khoản nhân viên đã bị khóa.");
                forwardToLogin(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Đã xảy ra lỗi khi kiểm tra trạng thái tài khoản.");
            forwardToLogin(request, response);
            return;
        }

        if (!account.isVerified()) {
            request.setAttribute("message", "Tài khoản của bạn chưa được xác thực qua email.");
            forwardToLogin(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("account", account);
        session.setAttribute("role", account.getRoleId());
        session.setAttribute("customer", customer);

        response.sendRedirect(request.getContextPath() + "/home");
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("valid", false);
        request.setAttribute("pageContent1", "login.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
