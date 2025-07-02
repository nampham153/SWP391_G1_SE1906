package controller.common;

import dao.DAOWrapper;
import model.Account;
import model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

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

        if (!account.isVerified()) {
            request.setAttribute("message", "Tài khoản của bạn chưa được xác thực qua email.");
            forwardToLogin(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("account", account);
        session.setAttribute("role", account.getRoleId());
        Customer customer = DAOWrapper.customerDAO.getCustomerById(phone);
        session.setAttribute("customer", customer);

        response.sendRedirect(request.getContextPath() + "/home");
    }

    private void forwardToLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("valid", false);
        request.setAttribute("pageContent1", "login.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Login servlet with role-based session setup and verification check";
    }
}
