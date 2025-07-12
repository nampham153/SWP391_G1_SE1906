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
                        BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(item.getItemId());
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
        } catch (NumberFormatException ignored) {}

        String cartKey = itemId + (variantSignature.isEmpty() ? "" : "|" + variantSignature);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (isGuest) {
            @SuppressWarnings("unchecked")
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            if (cart == null) cart = new HashMap<>();

            CartItem existingItem = cart.get(cartKey);

            switch (action) {
                case "add" -> {
                    Item item = itemDAO.getItemById(itemId);
                    if (item.getStock() < quantity) {
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

                case "remove" -> cart.remove(cartKey);
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

            switch (action) {
                case "add" -> {
                    int currentQty = existingItem != null ? existingItem.getQuantity() : 0;
                    Item item = itemDAO.getItemById(itemId);
                    if (item.getStock() < currentQty + 1) {
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

                case "remove" -> cartItemDAO.removeItem(cartId, itemId, variantSignature);
            }
        }

        response.getWriter().write("{\"status\":\"ok\"}");
    }
}
