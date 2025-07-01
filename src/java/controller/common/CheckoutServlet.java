package controller.common;

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
                    total = total.add(fullItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
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
                    total = total.add(fullItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    items.add(item);
                }
            }
        }
System.out.println("🟡 Số lượng item trong checkout: " + items.size());
for (CartItem ci : items) {
    System.out.println("➡️ " + ci.getItemId() + " x " + ci.getQuantity());
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
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\":\"Giỏ hàng trống.\"}");
                return;
            }

            for (CartItem cartItem : guestCart.values()) {
                Item fullItem = itemDAO.getItemById(cartItem.getItemId());
                if (cartItem.getItemId().startsWith("P")) {
                    BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(cartItem.getItemId());
                    fullItem.setPrice(pcTotal);
                }
                cartItem.setItemDetail(fullItem);
                total = total.add(fullItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                cartItems.add(cartItem);
            }

        } else {
            Cart cart = cartDAO.getCartByCustomerId(account.getPhone());
            if (cart == null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\":\"Không tìm thấy giỏ hàng.\"}");
                return;
            }

            cartItems = cartItemDAO.getItemsInCart(cart.getCartId());
            if (cartItems == null || cartItems.isEmpty()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"error\":\"Giỏ hàng đang trống.\"}");
                return;
            }

            for (CartItem item : cartItems) {
                Item fullItem = itemDAO.getItemById(item.getItemId());
                if (item.getItemId().startsWith("PC")) {
                    BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(item.getItemId());
                    fullItem.setPrice(pcTotal);
                }
                item.setItemDetail(fullItem);
                total = total.add(fullItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        // Lấy thông tin khách
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String noteInput = request.getParameter("note");

        // Lấy các thông tin phụ để ghi vào note
        String title = request.getParameter("title");
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String zipcode = request.getParameter("zipcode");
        String country = request.getParameter("country");
        String province = request.getParameter("province");
        String fax = request.getParameter("fax");
        System.out.println("📦 Address from form = " + address);
        StringBuilder noteBuilder = new StringBuilder();
        if (account == null) {
            noteBuilder.append("Khách vãng lai: ")
                .append(title != null ? title + " " : "")
                .append(firstName != null ? firstName + " " : "")
                .append(middleName != null ? middleName + " " : "")
                .append(lastName != null ? lastName : "")
                .append("\nĐịa chỉ: ").append(address != null ? address : "")
                .append("\nMã bưu điện: ").append(zipcode != null ? zipcode : "")
                .append("\nQuốc gia: ").append(country != null ? country : "")
                .append("\nTỉnh/thành: ").append(province != null ? province : "")
                .append("\nSố fax: ").append(fax != null ? fax : "");
        }

        if (noteInput != null && !noteInput.trim().isEmpty()) {
            if (noteBuilder.length() > 0) noteBuilder.append("\n\n");
            noteBuilder.append("Ghi chú khách: ").append(noteInput.trim());
        }

        String note = noteBuilder.toString();

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
        order.setOrderStatus(0);

        CustomerOrderDAO orderDAO = new CustomerOrderDAO();
        int orderId = orderDAO.insertCustomerOrderReturnId(order);

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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"orderId\":" + orderId + ", \"amount\":" + total.longValue() + "}");
    }
}
