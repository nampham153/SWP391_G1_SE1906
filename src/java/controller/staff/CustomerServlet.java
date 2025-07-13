package controller.staff;

import dao.CustomerDAO;
import model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/staff/customer")
public class CustomerServlet extends HttpServlet {

    private final CustomerDAO dao = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "edit":
                if (id != null) {
                    Customer c = dao.getCustomerById(id);
                    request.setAttribute("editCustomer", c);
                    request.setAttribute("formAction", "edit");
                }
                request.setAttribute("pageContent", "/admin/customer-form.jsp");
                request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
                return;

            case "delete":
                if (id != null) {
                    dao.changeStatusToInactiveIfActive(id);
                }
                response.sendRedirect("customer");
                return;

            case "add":
                request.setAttribute("formAction", "add");
                request.setAttribute("pageContent", "/admin/customer-form.jsp");
                request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
                return;

            default:
                List<Customer> customers = dao.getAllCustomers();
                request.setAttribute("customers", customers);
                request.setAttribute("customerCount", customers.size());
                request.setAttribute("pageContent", "/admin/customer-manage.jsp");
                request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = Optional.ofNullable(request.getParameter("action")).orElse("add");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthStr = request.getParameter("birthDate");
        String genderStr = request.getParameter("gender");
        String statusStr = request.getParameter("status");

        boolean gender = "1".equals(genderStr);
        boolean status = "1".equals(statusStr);

        Map<String, String> errors = new HashMap<>();

        // Validate ID
        if (id == null || id.trim().isEmpty()) {
            errors.put("id", "ID (Số điện thoại) không được để trống.");
        } else if (!id.matches("\\d{9,12}")) {
            errors.put("id", "ID phải là số điện thoại hợp lệ (9-12 chữ số).");
        }

        // Validate Name
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Tên không được để trống.");
        } else {
            name = name.trim();
            if (!name.matches("[\\p{L} ]{3,}")) {
                errors.put("name", "Tên phải chứa ít nhất 3 ký tự và chỉ gồm chữ cái và khoảng trắng.");
            }
        }

        // Validate Email
if (email == null || email.trim().isEmpty()) {
    errors.put("email", "Email không được để trống.");
} else {
    email = email.trim().toLowerCase();
    if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
        errors.put("email", "Email không hợp lệ. Phải đúng định dạng ví dụ: example@gmail.com");
    }
}


        // Validate birth date
        java.sql.Date birthDate = null;
        if (birthStr != null && !birthStr.trim().isEmpty()) {
            try {
                java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthStr);
                if (utilDate.after(new java.util.Date())) {
                    errors.put("birthDate", "Ngày sinh không được lớn hơn ngày hiện tại.");
                } else {
                    birthDate = new java.sql.Date(utilDate.getTime());
                }
            } catch (Exception e) {
                errors.put("birthDate", "Ngày sinh không đúng định dạng yyyy-MM-dd.");
            }
        } else {
            errors.put("birthDate", "Ngày sinh không được để trống.");
        }

        Customer customer = new Customer(id, name, email, birthDate, gender, status);

        try {
            Customer existing = dao.getCustomerById(id);

            if ("add".equals(action)) {
                if (existing != null) {
                    errors.put("id", "Customer ID này đã tồn tại.");
                } else if (!dao.accountExists(id)) {
                    errors.put("id", "Account ID không tồn tại trong hệ thống.");
                }

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("editCustomer", customer);
                    request.setAttribute("formAction", action);
                    request.setAttribute("pageContent", "/admin/customer-form.jsp");
                    request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
                    return;
                }

                dao.insertCustomer(customer);

            } else if ("edit".equals(action)) {
                if (existing == null) {
                    errors.put("id", "Không tìm thấy khách hàng để cập nhật.");
                }

                if (!errors.isEmpty()) {
                    request.setAttribute("errors", errors);
                    request.setAttribute("editCustomer", customer);
                    request.setAttribute("formAction", action);
                    request.setAttribute("pageContent", "/admin/customer-form.jsp");
                    request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
                    return;
                }

                dao.updateCustomer(customer);
            }

            response.sendRedirect("customer");

        } catch (Exception e) {
            errors.put("general", "Lỗi xử lý dữ liệu: " + e.getMessage());
            request.setAttribute("errors", errors);
            request.setAttribute("editCustomer", customer);
            request.setAttribute("formAction", action);
            request.setAttribute("pageContent", "/admin/customer-form.jsp");
            request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
        }
    }
}
