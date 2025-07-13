package controller.common;

import context.DBContext;
import dao.*;
import model.*;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
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
    BigDecimal pcTotal = productComponentDAO.getTotalPriceByVariant(cartItem.getItemId(), cartItem.getVariantSignature());
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

        if (items.isEmpty()) {
            response.sendRedirect("cart");
            return;
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
                respondWithError(response, "Giỏ hàng trống.");
                return;
            }

            for (CartItem cartItem : guestCart.values()) {
                Item fullItem = itemDAO.getItemById(cartItem.getItemId());
                if (cartItem.getItemId().startsWith("P")) {
    BigDecimal pcTotal = productComponentDAO.getTotalPriceByVariant(cartItem.getItemId(), cartItem.getVariantSignature());
    fullItem.setPrice(pcTotal);
                }
                cartItem.setItemDetail(fullItem);
                total = total.add(fullItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                cartItems.add(cartItem);
            }

        } else {
            Cart cart = cartDAO.getCartByCustomerId(account.getPhone());
            if (cart == null) {
                respondWithError(response, "Không tìm thấy giỏ hàng.");
                return;
            }

            cartItems = cartItemDAO.getItemsInCart(cart.getCartId());
            if (cartItems == null || cartItems.isEmpty()) {
                respondWithError(response, "Giỏ hàng đang trống.");
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
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String noteInput = request.getParameter("note");
        String paymentMethod = request.getParameter("payment");

        String title = request.getParameter("title");
        String firstName = request.getParameter("firstName");
        String middleName = request.getParameter("middleName");
        String lastName = request.getParameter("lastName");
        String zipcode = request.getParameter("zipcode");
        String country = request.getParameter("country");
        String province = request.getParameter("province");
        String fax = request.getParameter("fax");

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

        boolean inserted = orderDAO.insertItemOrderBatch(orderId, cartItems);
        if (!inserted) {
            respondWithError(response, "Không thể tạo chi tiết đơn hàng.");
            return;
        }
        if (account != null && account.getRoleId() == 1) {
            CustomerAddressDAO addrDAO = new CustomerAddressDAO();
            if (!addrDAO.hasAddress(account.getPhone())) {
                CustomerAddress addr = new CustomerAddress();
                addr.setCustomerAddress(address);
                addr.setCustomerId(account.getPhone());
                addrDAO.insert(addr);
            }
        }
        if ("cod".equalsIgnoreCase(paymentMethod)) {
            try (Connection conn = new DBContext().getConnection()) {
                conn.setAutoCommit(false);

                boolean success = orderDAO.decreaseStockIfEnough(conn, cartItems);
                if (!success) {
                    conn.rollback();
                    respondWithError(response, "Một số sản phẩm không đủ hàng.");
                    return;
                }

                if (account == null) {
                    session.removeAttribute("cart");
                } else {
                    Cart cart = cartDAO.getCartByCustomerId(account.getPhone());
                    if (cart != null) {
                        cartItemDAO.clearCart(cart.getCartId());
                    }
                }

                order.setOrderStatus(1); 
                order.setOrderId(orderId);
                orderDAO.updateOrderStatus(order);
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                respondWithError(response, "Lỗi khi xử lý đơn hàng COD.");
                return;
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"orderId\":" + orderId + ", \"amount\":" + total.longValue() + "}");
    }

    private void respondWithError(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\":\"" + msg + "\"}");
    }
}
