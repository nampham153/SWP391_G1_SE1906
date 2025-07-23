/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
/**
 *
 * @author namp0
 */
@WebServlet("/build-pc")
public class BuildPCServlet extends HttpServlet {

    private final ComponentCategoryDAO categoryDAO = new ComponentCategoryDAO();
    private final ItemDAO itemDAO = new ItemDAO();
        // 1. Lấy danh sách danh mục linh kiện
        // 2. Lấy linh kiện mặc định rẻ nhất theo từng danh mục
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ComponentCategory> componentCategories = categoryDAO.getAll();
        request.setAttribute("componentCategories", componentCategories);

        Map<Integer, Item> defaultComponents = new HashMap<>();
        for (ComponentCategory category : componentCategories) {
            Item defaultItem = itemDAO.getDefaultItemByCategory(category.getCategoryId());
            if (defaultItem != null) {
                defaultComponents.put(category.getCategoryId(), defaultItem);
            }
        }
        request.setAttribute("defaultComponents", defaultComponents);
        request.setAttribute("pageContent1", "build-pc.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Hiển thị trang Build PC với danh mục và cấu hình mặc định";
    }
}
