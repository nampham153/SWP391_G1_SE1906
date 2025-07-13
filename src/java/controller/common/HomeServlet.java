/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.common;

import dao.BrandDAO;
import dao.ComponentCategoryDAO;
import dao.ItemDAO;
import dao.ProductCategoryDAO;
import java.io.IOException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
/**
 *
 * @author namp0
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ItemDAO dao = new ItemDAO();
        BrandDAO brandDAO = new BrandDAO();
        List<Item> pcItems = dao.getPCItems(10);
        ComponentCategoryDAO categoryDAO = new ComponentCategoryDAO();
        List<ComponentCategory> componentCategories = categoryDAO.getAll();
        Map<Integer, List<Item>> componentItemsByCategory = new HashMap<>();
        for (ComponentCategory cat : componentCategories) {
            List<Item> items = dao.getItemsByComponentCategory(cat.getCategoryId(), 6);
            componentItemsByCategory.put(cat.getCategoryId(), items);
        }
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;
        String productCid = request.getParameter("productCid");
        String componentCid = request.getParameter("componentCid");
        String minParam = request.getParameter("minPrice");
        String maxParam = request.getParameter("maxPrice");

        List<Brand> productBrands;
        List<Brand> componentBrands;
        String searchKeyword = request.getParameter("search");
        List<Item> searchResults = null;

        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            searchResults = dao.searchItemsByName(searchKeyword.trim());
            request.setAttribute("searchKeyword", searchKeyword);
            request.setAttribute("searchResults", searchResults);
        }

        if (minParam != null && maxParam != null) {
            try {
                minPrice = Integer.parseInt(minParam);
                maxPrice = Integer.parseInt(maxParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (productCid != null) {
            try {
                int cid = Integer.parseInt(productCid);
                productBrands = brandDAO.getProductBrandsByCategory(cid);
            } catch (NumberFormatException e) {
                productBrands = brandDAO.getAllProductBrands();
            }
        } else {
            productBrands = brandDAO.getAllProductBrands();
        }

        if (componentCid != null) {
            try {
                int cid = Integer.parseInt(componentCid);
                componentBrands = brandDAO.getComponentBrandsByCategory(cid);
            } catch (NumberFormatException e) {
                componentBrands = brandDAO.getAllComponentBrands();
            }
        } else {
            componentBrands = brandDAO.getAllComponentBrands();
        }

        request.setAttribute("pcItems", pcItems);
        request.setAttribute("componentCategories", componentCategories);
        request.setAttribute("componentItemsByCategory", componentItemsByCategory);
        request.setAttribute("productBrands", productBrands);
        request.setAttribute("componentBrands", componentBrands);
        request.setAttribute("pageContent1", "home.jsp");

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
