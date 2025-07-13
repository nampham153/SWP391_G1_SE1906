/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.common;
import dao.CartDAO;
import dao.CartItemDAO;
import dao.ItemDAO;
import dao.ProductComponentDAO;
import model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
/**
 *
 * @author namp0
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private final CartDAO cartDAO = new CartDAO();
    private final CartItemDAO cartItemDAO = new CartItemDAO();
    private final ItemDAO itemDAO = new ItemDAO();
    private final ProductComponentDAO productComponentDAO = new ProductComponentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            Map<String, CartItem> sessionCart = (Map<String, CartItem>) session.getAttribute("cart");
            if (sessionCart == null || sessionCart.isEmpty()) {
                request.setAttribute("cartItems", null);
                request.setAttribute("cartTotal", BigDecimal.ZERO);
            } else {
                BigDecimal total = BigDecimal.ZERO;
                List<CartItem> items = new ArrayList<>();

                for (CartItem cartItem : sessionCart.values()) {
                    Item item = itemDAO.getItemById(cartItem.getItemId());

                    if (cartItem.getItemId().startsWith("P")) {
                        BigDecimal pcTotal = productComponentDAO.getTotalPriceByVariant(cartItem.getItemId(), cartItem.getVariantSignature());
                        item.setPrice(pcTotal);
                    }

                    cartItem.setItemDetail(item);
                    total = total.add(item.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
                    items.add(cartItem);
                }

                request.setAttribute("cartItems", items);
                request.setAttribute("cartTotal", total);
            }

        } else {
            String phone = acc.getPhone();
            Cart cart = cartDAO.getCartByCustomerId(phone);
            if (cart == null) {
                request.setAttribute("cartItems", null);
                request.setAttribute("cartTotal", BigDecimal.ZERO);
            } else {
                List<CartItem> items = cartItemDAO.getItemsInCart(cart.getCartId());
                BigDecimal total = BigDecimal.ZERO;

                for (CartItem item : items) {
                    Item fullItem = itemDAO.getItemById(item.getItemId());

                    if (item.getItemId().startsWith("P")) {
                        BigDecimal pcTotal = productComponentDAO.getTotalPriceByVariant(item.getItemId(), item.getVariantSignature());
                        fullItem.setPrice(pcTotal);
                    }

                    item.setItemDetail(fullItem);
                    total = total.add(fullItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }

                request.setAttribute("cartItems", items);
                request.setAttribute("cartTotal", total);
            }
        }

        request.setAttribute("pageContent1", "cart.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        boolean isGuest = (acc == null);

        String itemId = request.getParameter("itemId");
        String variantSignature = request.getParameter("variantSignature") != null ? request.getParameter("variantSignature") : "";
        String action = request.getParameter("action");
        int quantity = 1;

        try {
            quantity = Integer.parseInt(request.getParameter("currentQty"));
        } catch (NumberFormatException ignored) {
        }

        String cartKey = itemId + (variantSignature.isEmpty() ? "" : "|" + variantSignature);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (isGuest) {
            @SuppressWarnings("unchecked")
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new HashMap<>();
            }

            CartItem existingItem = cart.get(cartKey);

            switch (action) {
                case "add" -> {
                    Item item = itemDAO.getItemById(itemId);

                    // Tính tổng quantity tất cả biến thể cùng itemId trong giỏ session
                    int totalQtyForItem = 0;
                    for (CartItem ci : cart.values()) {
                        if (ci.getItemId().equals(itemId)) {
                            totalQtyForItem += ci.getQuantity();
                        }
                    }

                    int currentQty = existingItem != null ? existingItem.getQuantity() : 0;
                    int totalQtyAfterAdd = totalQtyForItem - currentQty + (currentQty + 1);

                    if (item.getStock() < totalQtyAfterAdd) {
                        response.getWriter().write("{\"error\":\"Chỉ còn " + item.getStock() + " sản phẩm trong kho.\"}");
                        return;
                    }

                    if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + 1);
                    } else {
                        CartItem newItem = new CartItem();
                        newItem.setItemId(itemId);
                        newItem.setVariantSignature(variantSignature);
                        newItem.setQuantity(1);
                        newItem.setItemDetail(item);
                        cart.put(cartKey, newItem);
                    }
                }

                case "removeOne" -> {
                    if (existingItem != null) {
                        if (existingItem.getQuantity() > 1) {
                            existingItem.setQuantity(existingItem.getQuantity() - 1);
                        } else {
                            cart.remove(cartKey);
                        }
                    }
                }

                case "remove" ->
                    cart.remove(cartKey);
            }

            session.setAttribute("cart", cart);

        } else {
            String customerId = acc.getPhone();
            Cart cart = cartDAO.getCartByCustomerId(customerId);
            if (cart == null) {
                int newCartId = cartDAO.createCart(customerId);
                cart = new Cart(newCartId, customerId, null);
            }

            int cartId = cart.getCartId();
            CartItem existingItem = cartItemDAO.getCartItem(cartId, itemId, variantSignature);
            // Lấy tất cả biến thể cùng itemId trong giỏ hàng của user
            switch (action) {
                case "add" -> {
                    List<CartItem> allVariants = cartItemDAO.getItemsInCart(cartId);

                    int totalQtyForItem = 0;
                    for (CartItem ci : allVariants) {
                        if (ci.getItemId().equals(itemId)) {
                            totalQtyForItem += ci.getQuantity();
                        }
                    }

                    int currentQty = existingItem != null ? existingItem.getQuantity() : 0;
                    int totalQtyAfterAdd = totalQtyForItem - currentQty + (currentQty + 1);

                    Item item = itemDAO.getItemById(itemId);
                    if (item.getStock() < totalQtyAfterAdd) {
                        response.getWriter().write("{\"error\":\"Chỉ còn " + item.getStock() + " sản phẩm trong kho.\"}");
                        return;
                    }

                    cartItemDAO.addOrUpdateItem(cartId, itemId, variantSignature, 1);
                }

                case "removeOne" -> {
                    if (existingItem != null) {
                        int currentQty = existingItem.getQuantity();
                        if (currentQty > 1) {
                            cartItemDAO.updateQuantity(cartId, itemId, variantSignature, currentQty - 1);
                        } else {
                            cartItemDAO.removeItem(cartId, itemId, variantSignature);
                        }
                    }
                }

                case "remove" ->
                    cartItemDAO.removeItem(cartId, itemId, variantSignature);
            }
        }

        response.getWriter().write("{\"status\":\"ok\"}");
    }
}
