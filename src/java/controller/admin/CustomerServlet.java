package controller.admin;

import dao.CustomerDAO;
import model.Customer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/admin/customer")
public class CustomerServlet extends HttpServlet {

    private final CustomerDAO dao = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action == null) action = "list";

        switch (action) {
            case "edit":
                if (id != null) {
                    Customer c = dao.getCustomerById(id);
                    request.setAttribute("editCustomer", c);
                }
                break;
            case "delete":
                if (id != null) dao.changeStatusToInactiveIfActive(id);
                response.sendRedirect("customer");
                return;
            case "search":
                String name = request.getParameter("name");
                String statusStr = request.getParameter("status");
                Boolean statusFilter = null;
                if (statusStr != null && !statusStr.isEmpty()) statusFilter = "1".equals(statusStr);
                List<Customer> filtered = dao.searchCustomersByNameAndStatus(name, statusFilter);
                request.setAttribute("customers", filtered);
                request.setAttribute("pageContent", "customer-manage1.jsp");
                request.getRequestDispatcher("layout.jsp").forward(request, response);
                return;
            default:
                List<Customer> list = dao.getAllCustomers();
                request.setAttribute("customers", list);
                break;
        }

        request.setAttribute("pageContent", "customer-manage1.jsp");
        request.getRequestDispatcher("layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "add";

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

            if (id == null || id.trim().isEmpty()) {
                request.setAttribute("error", "ID không được để trống.");
                request.setAttribute("customers", dao.getAllCustomers());
                request.setAttribute("pageContent", "customer-manage1.jsp");
                request.getRequestDispatcher("layout.jsp").forward(request, response);
                return;
            }

            Customer customer = new Customer(id, name, email, birthDate, gender, status);
            Customer existing = dao.getCustomerById(id);

            if ("add".equals(action)) {
                if (existing != null) {
                    request.setAttribute("error", "Account ID này đã được đăng ký trong Customer.");
                    request.setAttribute("customers", dao.getAllCustomers());
                    request.setAttribute("pageContent", "customer-manage1.jsp");
                    request.getRequestDispatcher("layout.jsp").forward(request, response);
                    return;
                }
                if (!dao.accountExists(id)) {
                    request.setAttribute("error", "Account ID không tồn tại trong hệ thống.");
                    request.setAttribute("customers", dao.getAllCustomers());
                    request.setAttribute("pageContent", "customer-manage1.jsp");
                    request.getRequestDispatcher("layout.jsp").forward(request, response);
                    return;
                }
                dao.insertCustomer(customer);
            } else if ("edit".equals(action)) {
                if (existing == null) {
                    request.setAttribute("error", "Không tìm thấy khách hàng để cập nhật.");
                    request.setAttribute("customers", dao.getAllCustomers());
                    request.setAttribute("pageContent", "customer-manage1.jsp");
                    request.getRequestDispatcher("layout.jsp").forward(request, response);
                    return;
                }
                dao.updateCustomer(customer);
            }

            response.sendRedirect("customer");

        } catch (Exception e) {
            request.setAttribute("error", "Lỗi xử lý dữ liệu: " + e.getMessage());
            request.setAttribute("customers", dao.getAllCustomers());
            request.setAttribute("pageContent", "customer-manage1.jsp");
            request.getRequestDispatcher("layout.jsp").forward(request, response);
        }
    }
}
