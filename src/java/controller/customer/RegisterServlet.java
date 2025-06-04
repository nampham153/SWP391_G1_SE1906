package controller.customer;

import dao.AccountDAO;
import dao.DAOWrapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import model.Customer;
import model.Role;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String phone = request.getParameter("phone");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String birthdateStr = request.getParameter("birthdate");
            String genderStr = request.getParameter("gender");

            // Validate input
            if (phone == null || name == null || name.isEmpty() || email == null || email.isEmpty()
                    || password == null || password.isEmpty() || birthdateStr == null || birthdateStr.isEmpty()) {
                out.print("{\"success\": false, \"message\": \"All fields are required!\"}");
                return;
            }

            // Validate email format
            if (!email.matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
                out.print("{\"success\": false, \"message\": \"Invalid email format!\"}");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdate;
            try {
                birthdate = new Date(sdf.parse(birthdateStr).getTime());
            } catch (ParseException e) {
                out.print("{\"success\": false, \"message\": \"Invalid date format! Please use YYYY-MM-DD.\"}");
                return;
            }

            boolean gender = "1".equals(genderStr);

            //AccountDAO accountDAO = new AccountDAO();

            if (DAOWrapper.customerDAO.getCustomerByEmail(email) != null) {
                out.print("{\"success\": false, \"message\": \"Email already exists!\"}");
                return;
            }
            
            Role role = DAOWrapper.roleDAO.getRoleByName("customer");
            boolean accountStatus = DAOWrapper.accountDAO.createAccount(phone, password, role.getRoleId());
            Customer customer = new Customer(phone, name, email, birthdate, gender, true);
            boolean customerStatus = DAOWrapper.customerDAO.insertCustomer(customer);
            
            if (accountStatus && customerStatus) {
                out.print("{\"success\": true, \"message\": \"Registration successful! You can now login.\"}");
            } else {
                DAOWrapper.customerDAO.deleteCustomer(phone);
                DAOWrapper.accountDAO.deleteAccount(phone);
                out.print("{\"success\": false, \"message\": \"Registration failed due to server error. Please try again.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"success\": false, \"message\": \"An unexpected error occurred: " + e.getMessage() + "\"}");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles user registration";
    }
}
