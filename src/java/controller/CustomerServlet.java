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
        if (action == null) action = "list";

        switch (action) {
            case "new":
                request.getRequestDispatcher("/Customer/customer-form.jsp").forward(request, response);
                break;
            case "edit":
                String id = request.getParameter("id");
                Customer c = dao.getCustomerById(id);
                request.setAttribute("customer", c);
                request.getRequestDispatcher("/Customer/customer-form.jsp").forward(request, response);
                break;
            case "delete":
                dao.deleteCustomer(request.getParameter("id"));
                response.sendRedirect("customer");
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

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String birthStr = request.getParameter("birthDate");
        String genderStr = request.getParameter("gender");
        boolean gender = genderStr != null && genderStr.equals("1");
        String statusStr = request.getParameter("status");
        boolean status = statusStr != null && statusStr.equals("1");

        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthStr);
            java.sql.Date birthDate = new java.sql.Date(utilDate.getTime());
            Customer c = new Customer(id, name, email, birthDate, gender, status);

            if (dao.getCustomerById(id) == null) {
                dao.insertCustomer(c);
            } else {
                dao.updateCustomer(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("customer");
    }
}
