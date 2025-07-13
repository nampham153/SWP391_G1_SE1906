/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;
import dao.CustomerOrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CustomerOrder;

/**
 *
 * @author namp0
 */
@WebServlet("/checkout-success")
public class CheckoutSuccessServlet extends HttpServlet {

    private final CustomerOrderDAO orderDAO = new CustomerOrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("orderId");
        if (idParam == null) {
            response.sendRedirect("home");
            return;
        }

        try {
            int orderId = Integer.parseInt(idParam);
            CustomerOrder order = orderDAO.getOrderById(orderId);
            if (order == null) {
                request.setAttribute("message", "Không tìm thấy đơn hàng.");
            } else {
                request.setAttribute("order", order);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Mã đơn hàng không hợp lệ.");
        }

        request.setAttribute("pageContent1", "checkout-success.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
