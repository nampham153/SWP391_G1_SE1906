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

@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action != null && action.equals("register")) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String birthdateStr = request.getParameter("birthdate");
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthdate = sdf.parse(birthdateStr);

                AccountDAO dao = new AccountDAO();

                if (dao.checkEmailExist(email)) {
                    request.setAttribute("error", "Email đã được sử dụng");
                    request.getRequestDispatcher("login.html").forward(request, response);
                    return;
                }

                boolean success = dao.register(name, email, password, birthdate, gender);

                if (success) {
                    request.setAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
                    request.getRequestDispatcher("login.html").forward(request, response);
                } else {
                    request.setAttribute("error", "Đăng ký thất bại. Vui lòng thử lại.");
                    request.getRequestDispatcher("login.html").forward(request, response);
                }

            } catch (ParseException e) {
                e.printStackTrace();
                request.setAttribute("error", "Ngày sinh không hợp lệ");
                request.getRequestDispatcher("login.html").forward(request, response);
            }
        } else {
            response.sendRedirect("login.html");
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
}
