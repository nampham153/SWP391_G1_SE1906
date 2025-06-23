package controller.customer;

import dao.CartDAO;
import dao.CartItemDAO;
import dao.ItemDAO;
import dao.CustomerAddressDAO;
import dao.CustomerOrderDAO;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.math.BigDecimal;
import java.util.*;

import model.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();
    private final CartItemDAO cartItemDAO = new CartItemDAO();
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        List<CartItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        if (acc == null) {
            // Guest cart
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (CartItem cartItem : cart.values()) {
                    Item fullItem = itemDAO.getItemById(cartItem.getItemId());
                    cartItem.setItemDetail(fullItem);
                    BigDecimal itemTotal = fullItem.getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                    total = total.add(itemTotal);
                    items.add(cartItem);
                }
            }
        } else {
            Cart cart = cartDAO.getCartByCustomerId(acc.getPhone());
            if (cart != null) {
                List<CartItem> cartItems = cartItemDAO.getItemsInCart(cart.getCartId());
                for (CartItem item : cartItems) {
                    Item fullItem = itemDAO.getItemById(item.getItemId());
                    item.setItemDetail(fullItem);
                    BigDecimal itemTotal = fullItem.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()));
                    total = total.add(itemTotal);
                    items.add(item);
                }
            }
        }

        request.setAttribute("cartItems", items);
        request.setAttribute("cartTotal", total);
        request.setAttribute("pageContent1", "checkout.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession();
    Account account = (Account) session.getAttribute("account");

    List<CartItem> cartItems = new ArrayList<>();
    BigDecimal total = BigDecimal.ZERO;

    // Lấy giỏ hàng
    if (account == null) {
        // Guest cart
        Map<String, CartItem> guestCart = (Map<String, CartItem>) session.getAttribute("cart");
        if (guestCart == null || guestCart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        for (CartItem cartItem : guestCart.values()) {
            Item fullItem = itemDAO.getItemById(cartItem.getItemId());
            cartItem.setItemDetail(fullItem);
            BigDecimal itemTotal = fullItem.getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            total = total.add(itemTotal);
            cartItems.add(cartItem);
        }

    } else {
        // Logged-in cart
        Cart cart = cartDAO.getCartByCustomerId(account.getPhone());
        if (cart == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        cartItems = cartItemDAO.getItemsInCart(cart.getCartId());
        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        for (CartItem item : cartItems) {
            Item fullItem = itemDAO.getItemById(item.getItemId());
            item.setItemDetail(fullItem);
            BigDecimal itemTotal = fullItem.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
    }

    // Lấy thông tin form
    String address = request.getParameter("address");
    String phone = request.getParameter("phone");
    String email = request.getParameter("email");
    String note = request.getParameter("note");

    // Tạo đơn hàng
    CustomerOrder order = new CustomerOrder();
    order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
    order.setOrderAddress(address);
    order.setOrderPhone(phone);
    order.setOrderEmail(email);
    order.setShippingFee(BigDecimal.ZERO);
    order.setAdditionalFee(BigDecimal.ZERO);
    order.setNote(note);
    order.setOrderStatus(true);
    order.setCustomerId(account != null && account.getRoleId() == 1 ? account.getPhone() : null);
    order.setTotal(total);

    new CustomerOrderDAO().insertCustomerOrder(order);

    // Nếu là người dùng, lưu địa chỉ nếu chưa có
    if (account != null && account.getRoleId() == 1) {
        CustomerAddressDAO addrDAO = new CustomerAddressDAO();
        if (!addrDAO.hasAddress(account.getPhone())) {
            CustomerAddress addr = new CustomerAddress();
            addr.setCustomerAddress(address);
            addr.setCustomerId(account.getPhone());
            addrDAO.insert(addr);
        }
    }

    // Xoá giỏ hàng khỏi session nếu là guest
    if (account == null) {
        session.removeAttribute("cart");
    }

    response.sendRedirect("order-success.jsp");
}
}

