package controller;

import dao.CustomerDAO;
import model.Customer;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    CustomerDAO dao = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                request.getRequestDispatcher("/Customer/customer-add.jsp").forward(request, response);
                break;

            case "edit":
                String id = request.getParameter("id");
                Customer c = dao.getCustomerById(id);
                request.setAttribute("customer", c);
                request.getRequestDispatcher("/Customer/customer-edit.jsp").forward(request, response);
                break;

            case "delete":
                dao.deleteCustomer(request.getParameter("id"));
                response.sendRedirect("customer");
                break;
            case "search":
                String searchId = request.getParameter("search");
                System.out.println("Searching for ID: " + searchId);
                Customer found = dao.getCustomerById(searchId);
                if (found != null) {
                    request.setAttribute("customers", List.of(found));
                } else {
                    request.setAttribute("error", "Không tìm thấy khách hàng với ID: " + searchId);
                    request.setAttribute("customers", dao.getAllCustomers());
                }
                request.getRequestDispatcher("/Customer/customer_list.jsp").forward(request, response);
                break;
            default:
                List<Customer> list = dao.getAllCustomers();
                request.setAttribute("customers", list);
                request.getRequestDispatcher("/Customer/customer_list.jsp").forward(request, response);
                break;
        }
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");

    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String birthStr = request.getParameter("birthDate");
    String genderStr = request.getParameter("gender");
    String statusStr = request.getParameter("status");

    boolean gender = "1".equals(genderStr);
    boolean status = "1".equals(statusStr);

    try {
        java.sql.Date birthDate = null;
        if (birthStr != null && !birthStr.isEmpty()) {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthStr);
            birthDate = new java.sql.Date(utilDate.getTime());
        }

        Customer c = new Customer(id, name, email, birthDate, gender, status);

        if (id == null || id.trim().isEmpty()) {
            request.setAttribute("error", "ID không được để trống.");
            request.setAttribute("customer", c);
            // Forward về form add khi lỗi ID trống (thêm mới)
            request.getRequestDispatcher("/Customer/customer-add.jsp").forward(request, response);
            return;
        }

        Customer existing = dao.getCustomerById(id);
        System.out.println("Existing customer: " + existing);

        if (existing == null) {
            boolean accountExists = dao.accountExists(id);
            if (!accountExists) {
                request.setAttribute("error", "Account ID không tồn tại. Vui lòng nhập ID hợp lệ.");
                request.setAttribute("customer", c);
                // Forward về form add nếu account không tồn tại
                request.getRequestDispatcher("/Customer/customer-add.jsp").forward(request, response);
                return;
            }

            dao.insertCustomer(c);
            System.out.println("Inserted new customer: " + c);
        } else {
            dao.updateCustomer(c);
            System.out.println("Updated existing customer: " + c);
        }

        // Redirect về trang danh sách nếu thành công
        response.sendRedirect("customer");

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Lỗi xử lý dữ liệu: " + e.getMessage());
        // Forward về form edit nếu đang sửa mà lỗi
        request.getRequestDispatcher("/Customer/customer-edit.jsp").forward(request, response);
    }
}


}
