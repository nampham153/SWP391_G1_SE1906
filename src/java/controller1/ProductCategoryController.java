/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller1;

import dao.ProductCategoryDAO;
import jakarta.servlet.annotation.WebServlet;
import model.ProductCategory;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productCategory")
public class ProductCategoryController extends HttpServlet {
    private ProductCategoryDAO dao = new ProductCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action)) {
            List<ProductCategory> list = dao.getAllProductCategories();
            request.setAttribute("productCategories", list);
            request.getRequestDispatcher("/productCategory.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductCategory pc = dao.getProductCategoryById(id);
            request.setAttribute("productCategory", pc);
            request.getRequestDispatcher("/productCategory.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteProductCategory(id);
            response.sendRedirect("productCategory?action=list");
        } else {
            request.getRequestDispatcher("/productCategory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String categoryName = request.getParameter("categoryName");
            dao.createProductCategory(categoryName);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String categoryName = request.getParameter("categoryName");
            dao.updateProductCategory(id, categoryName);
        }
        response.sendRedirect("productCategory?action=list");
    }
}
