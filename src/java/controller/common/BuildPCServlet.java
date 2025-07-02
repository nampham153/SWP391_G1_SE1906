package controller.common;

import dao.ComponentCategoryDAO;
import dao.ItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.ComponentCategory;
import model.Item;

import java.io.IOException;
import java.util.*;

@WebServlet("/build-pc")
public class BuildPCServlet extends HttpServlet {

    private final ComponentCategoryDAO categoryDAO = new ComponentCategoryDAO();
    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy danh sách danh mục linh kiện (CPU, RAM, SSD,...)
        List<ComponentCategory> componentCategories = categoryDAO.getAll();
        request.setAttribute("componentCategories", componentCategories);

        // 2. Lấy linh kiện mặc định rẻ nhất theo từng danh mục
        Map<Integer, Item> defaultComponents = new HashMap<>();
        for (ComponentCategory category : componentCategories) {
            Item defaultItem = itemDAO.getDefaultItemByCategory(category.getCategoryId());
            if (defaultItem != null) {
                defaultComponents.put(category.getCategoryId(), defaultItem);
            }
        }
        request.setAttribute("defaultComponents", defaultComponents);

        // 3. Gửi sang trang JSP (forward)
        request.getRequestDispatcher("build-pc.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Hiển thị trang Build PC với danh mục và cấu hình mặc định";
    }
}
