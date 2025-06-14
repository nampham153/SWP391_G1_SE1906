package controller.common;

import dao.AccountDAO;
import dao.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Customer;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final AccountDAO accountDao = new AccountDAO();
    private final CustomerDAO customerDao = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageContent1", "register.jsp");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthdate = request.getParameter("birthdate");
        String genderStr = request.getParameter("gender");

        // Validate
        if (phone == null || !phone.matches("0\\d{10}")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ (bắt đầu bằng 0, đủ 10 số).");
            forwardWithFormData(request, response);
            return;
        }

        if (password == null || password.length() < 6) {
            request.setAttribute("message", "Mật khẩu phải có ít nhất 6 ký tự.");
            forwardWithFormData(request, response);
            return;
        }

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("message", "Tên không được để trống.");
            forwardWithFormData(request, response);
            return;
        }

        if (email == null || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            request.setAttribute("message", "Email không hợp lệ.");
            forwardWithFormData(request, response);
            return;
        }

        Date dob = null;
        try {
            dob = Date.valueOf(birthdate);
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "Ngày sinh không hợp lệ.");
            forwardWithFormData(request, response);
            return;
        }

        boolean gender = "1".equals(genderStr);

        boolean accountCreated = accountDao.createAccount(phone, password, 1);
        if (!accountCreated) {
            request.setAttribute("message", "Số điện thoại đã tồn tại.");
            forwardWithFormData(request, response);
            return;
        }
        Customer customer = new Customer();
        customer.setCustomerId(phone);
        customer.setCustomerName(name);
        customer.setCustomerEmail(email);
        customer.setCustomerBirthDate(dob);
        customer.setCustomerGender(gender);
        customer.setStatus(true);

        boolean customerCreated = customerDao.insertCustomer(customer);
        if (!customerCreated) {
            request.setAttribute("message", "Đăng ký thất bại. Có thể tài khoản đã được đăng kí.");
            forwardWithFormData(request, response);
            return;
        }
        response.sendRedirect("login");
    }

    private void forwardWithFormData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageContent1", "register.jsp");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
