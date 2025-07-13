/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.common;
import dao.CartItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Account;
import model.CartItem;
import java.io.IOException;
import java.util.Map;
/**
 *
 * @author namp0
 */
@WebServlet("/cart-size")
public class CartSizeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        int totalQuantity = 0;
        if (account != null) {
            CartItemDAO dao = new CartItemDAO();
            totalQuantity = dao.countItemsByCustomerId(account.getPhone());
        } else {
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (CartItem item : cart.values()) {
                    totalQuantity += item.getQuantity();
                }
            }
        }

        response.setContentType("text/plain");
        response.getWriter().print(totalQuantity);
    }
}
