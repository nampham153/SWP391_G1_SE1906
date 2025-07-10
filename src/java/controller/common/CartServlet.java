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
                    Item fullItem = itemDAO.getItemById(cartItem.getItemId());

                    if (cartItem.getItemId().startsWith("P")) {
                        BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(cartItem.getItemId());
                        fullItem.setPrice(pcTotal);
                    }

                    cartItem.setItemDetail(fullItem);
                    BigDecimal itemTotal = fullItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                    total = total.add(itemTotal);
                    items.add(cartItem);
                }

                request.setAttribute("cartItems", items);
                request.setAttribute("cartTotal", total);
            }

        } else {
            String cartOwner = acc.getPhone();
            Cart cart = cartDAO.getCartByCustomerId(cartOwner);
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
                    BigDecimal itemTotal = fullItem.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    total = total.add(itemTotal);
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
        String action = request.getParameter("action");

        int currentQtyParam = 0;
        try {
            currentQtyParam = Integer.parseInt(request.getParameter("currentQty"));
        } catch (NumberFormatException e) {
            currentQtyParam = 0;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (isGuest) {
            @SuppressWarnings("unchecked")
            Map<String, CartItem> cart = (Map<String, CartItem>) session.getAttribute("cart");
            if (cart == null) {
                cart = new HashMap<>();
            }

            CartItem existingItem = cart.get(itemId);

            switch (action) {
                case "add": {
                    Item item = itemDAO.getItemById(itemId);

                    if (item.getStock() < currentQtyParam) {
                        response.getWriter().write("{\"error\":\"Sản phẩm chỉ còn " + item.getStock() + " cái trong kho.\"}");
                        return;
                    }

                    if (existingItem != null) {
                        existingItem.setQuantity(currentQtyParam);
                    } else {
                        if (itemId.startsWith("P")) {
                            BigDecimal pcTotal = productComponentDAO.getTotalPriceOfProduct(itemId);
                            item.setPrice(pcTotal);
                        }

                        CartItem newItem = new CartItem();
                        newItem.setItemId(itemId);
                        newItem.setQuantity(1);
                        newItem.setItemDetail(item);
                        cart.put(itemId, newItem);
                    }
                    break;
                }

                case "removeOne":
                    if (existingItem != null) {
                        int currentQty = existingItem.getQuantity();
                        if (currentQty > 1) {
                            existingItem.setQuantity(currentQty - 1);
                        } else {
                            cart.remove(itemId);
                        }
                    }
                    break;

                case "remove":
                    cart.remove(itemId);
                    break;
            }

            session.setAttribute("cart", cart);

        } else {
            String cartOwner = acc.getPhone();
            Cart cart = cartDAO.getCartByCustomerId(cartOwner);
            if (cart == null) {
                int newCartId = cartDAO.createCart(cartOwner);
                cart = new Cart(newCartId, cartOwner, null);
            }

            switch (action) {
                case "add": {
                    CartItem existingItem = cartItemDAO.getCartItem(cart.getCartId(), itemId);
                    int currentQty = existingItem != null ? existingItem.getQuantity() : 0;

                    Item item = itemDAO.getItemById(itemId);
                    if (item.getStock() < currentQtyParam + 1) {
                        response.getWriter().write("{\"error\":\"Sản phẩm chỉ còn " + item.getStock() + " cái trong kho.\"}");
                        return;
                    }

                    cartItemDAO.addOrUpdateItem(cart.getCartId(), itemId, 1);
                    break;
                }

                case "removeOne":
                    CartItem existingItem = cartItemDAO.getCartItem(cart.getCartId(), itemId);
                    if (existingItem != null) {
                        int currentQty = existingItem.getQuantity();
                        if (currentQty > 1) {
                            cartItemDAO.updateQuantity(cart.getCartId(), itemId, currentQty - 1);
                        } else {
                            cartItemDAO.removeItem(cart.getCartId(), itemId);
                        }
                    }
                    break;

                case "remove":
                    cartItemDAO.removeItem(cart.getCartId(), itemId);
                    break;
            }
        }

        response.getWriter().write("{\"status\":\"ok\"}");
    }
}
