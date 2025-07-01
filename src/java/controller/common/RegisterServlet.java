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

        // Validate phone
        if (phone == null || !phone.matches("0\\d{10}")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ (bắt đầu bằng 0 và đủ 11 chữ số).");
            forwardWithFormData(request, response);
            return;
        }

        // Validate password
        if (password == null || password.trim().length() < 6 || password.contains(" ")) {
            request.setAttribute("message", "Mật khẩu phải có ít nhất 6 ký tự và không chứa khoảng trắng.");
            forwardWithFormData(request, response);
            return;
        }

        // Validate name
        if (name == null || name.trim().length() < 3) {
            request.setAttribute("message", "Tên phải có ít nhất 3 ký tự.");
            forwardWithFormData(request, response);
            return;
        }

        String trimmedName = name.trim();
        int spaceCount = (int) trimmedName.chars().filter(c -> c == ' ').count();
        if (spaceCount > 5) {
            request.setAttribute("message", "Tên không được chứa quá 5 khoảng trắng.");
            forwardWithFormData(request, response);
            return;
        }

        if (!trimmedName.matches("^[\\p{L} ]+$")) {
            request.setAttribute("message", "Tên chỉ được chứa chữ cái và khoảng trắng.");
            forwardWithFormData(request, response);
            return;
        }

        // Validate email
        if (email == null || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            request.setAttribute("message", "Email không hợp lệ.");
            forwardWithFormData(request, response);
            return;
        }

        // Validate birthdate
        if (birthdate == null || birthdate.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập ngày sinh.");
            forwardWithFormData(request, response);
            return;
        }

        Date dob;
        try {
            dob = Date.valueOf(birthdate);
            Date today = new Date(System.currentTimeMillis());
            if (dob.after(today)) {
                request.setAttribute("message", "Ngày sinh không được lớn hơn ngày hiện tại.");
                forwardWithFormData(request, response);
                return;
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "Định dạng ngày sinh không hợp lệ. Định dạng đúng: yyyy-MM-dd");
            forwardWithFormData(request, response);
            return;
        }

        // Validate gender
        if (!"0".equals(genderStr) && !"1".equals(genderStr)) {
            request.setAttribute("message", "Giới tính không hợp lệ.");
            forwardWithFormData(request, response);
            return;
        }
        boolean gender = "1".equals(genderStr);

        // Create account
        boolean accountCreated = accountDao.createAccount(phone, password, 1);
        if (!accountCreated) {
            request.setAttribute("message", "Số điện thoại đã tồn tại.");
            forwardWithFormData(request, response);
            return;
        }

        // Create customer
        Customer customer = new Customer();
        customer.setCustomerId(phone);
        customer.setCustomerName(trimmedName);
        customer.setCustomerEmail(email);
        customer.setCustomerBirthDate(dob);
        customer.setCustomerGender(gender);
        customer.setStatus(true);

        boolean customerCreated = customerDao.insertCustomer(customer);
        if (!customerCreated) {
            request.setAttribute("message", "Đăng ký thất bại. Có thể tài khoản đã được đăng ký.");
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
