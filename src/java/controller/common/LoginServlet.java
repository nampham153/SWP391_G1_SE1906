package controller.common;

import dao.DAOWrapper;
import model.Account;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

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

    if (account != null) {
        HttpSession session = request.getSession();
        session.setAttribute("account", account);
        session.setAttribute("role", account.getRoleId());

        int role = account.getRoleId();
        System.out.println("Login success - role: " + role);

        switch (role) {
            case 2: // Admin
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                break;
            case 3: // Staff
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                break;
            default: // Customer
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }

    } else {
        request.setAttribute("valid", false);
        request.setAttribute("pageContent1", "login.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}


    @Override
    public String getServletInfo() {
        return "Login servlet with role-based session setup";
    }
}
