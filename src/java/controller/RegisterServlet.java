package controller;

import dao.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String birthdateStr = request.getParameter("birthdate");
            String genderStr = request.getParameter("gender");

            // Validate input
            if (name == null || name.isEmpty() || email == null || email.isEmpty()
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
                birthdate = sdf.parse(birthdateStr);
            } catch (ParseException e) {
                out.print("{\"success\": false, \"message\": \"Invalid date format! Please use YYYY-MM-DD.\"}");
                return;
            }

            boolean gender = "1".equals(genderStr);

            AccountDAO accountDAO = new AccountDAO();

            if (accountDAO.checkEmailExist(email)) {
                out.print("{\"success\": false, \"message\": \"Email already exists!\"}");
                return;
            }

            boolean isSuccess = accountDAO.register(name, email, password, birthdate, gender);

            if (isSuccess) {
                out.print("{\"success\": true, \"message\": \"Registration successful! You can now login.\"}");
            } else {
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
