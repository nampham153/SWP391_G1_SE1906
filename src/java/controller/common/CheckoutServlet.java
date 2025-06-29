package controller.common;

import com.vnpay.common.VNPayUtil;
import dao.CartDAO;
import dao.CartItemDAO;
import dao.CustomerAddressDAO;
import dao.CustomerOrderDAO;
import dao.ItemDAO;
import dao.ProductComponentDAO;
import model.*;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.math.BigDecimal;
import java.util.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();
    private final CartItemDAO cartItemDAO = new CartItemDAO();
    private final ItemDAO itemDAO = new ItemDAO();
    private final ProductComponentDAO productComponentDAO = new ProductComponentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        List<CartItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        if (acc == null) {
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            if (cart != null) {
                for (CartItem cartItem : cart.values()) {
                    Item fullItem = itemDAO.getItemById(cartItem.getItemId());

                    if (cartItem.getItemId().startsWith("P")) {
                        BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(cartItem.getItemId());
                        fullItem.setPrice(pcTotal);
                    }

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

                    if (item.getItemId().startsWith("P")) {
                        BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(item.getItemId());
                        fullItem.setPrice(pcTotal);
                    }

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

        if (account == null) {
            Map<String, CartItem> guestCart = (Map<String, CartItem>) session.getAttribute("cart");
            if (guestCart == null || guestCart.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            for (CartItem cartItem : guestCart.values()) {
                Item fullItem = itemDAO.getItemById(cartItem.getItemId());

                if (cartItem.getItemId().startsWith("P")) {
                    BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(cartItem.getItemId());
                    fullItem.setPrice(pcTotal);
                }

                cartItem.setItemDetail(fullItem);
                BigDecimal itemTotal = fullItem.getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                total = total.add(itemTotal);
                cartItems.add(cartItem);
            }

        } else {
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

                if (item.getItemId().startsWith("P")) {
                    BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(item.getItemId());
                    fullItem.setPrice(pcTotal);
                }

                item.setItemDetail(fullItem);
                BigDecimal itemTotal = fullItem.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);
            }
        }

        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String note = request.getParameter("note");
        String paymentMethod = request.getParameter("payment"); // "cod" hoặc "bank"

        CustomerOrder order = new CustomerOrder();
        order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
        order.setOrderAddress(address);
        order.setOrderPhone(phone);
        order.setOrderEmail(email);
        order.setShippingFee(BigDecimal.ZERO);
        order.setAdditionalFee(BigDecimal.ZERO);
        order.setNote(note);
        order.setCustomerId(account != null && account.getRoleId() == 1 ? account.getPhone() : null);
        order.setTotal(total);

        int status = "cod".equals(paymentMethod) ? 1 : 0;
        order.setOrderStatus(status);

        CustomerOrderDAO orderDAO = new CustomerOrderDAO();
        int orderId = orderDAO.insertCustomerOrderReturnId(order); // Lưu đơn và lấy ID

        if (account != null && account.getRoleId() == 1) {
            CustomerAddressDAO addrDAO = new CustomerAddressDAO();
            if (!addrDAO.hasAddress(account.getPhone())) {
                CustomerAddress addr = new CustomerAddress();
                addr.setCustomerAddress(address);
                addr.setCustomerId(account.getPhone());
                addrDAO.insert(addr);
            }
        }

        if (account == null) {
            session.removeAttribute("cart");
        }

        if ("cod".equals(paymentMethod)) {
            response.sendRedirect("order-success.jsp");
        } else {
            String paymentUrl = VNPayUtil.buildRedirectUrl(request, orderId, total.longValue());
            response.sendRedirect(paymentUrl);
        }
    }
}
