package controller1;

import dao.ProductCategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.ProductCategory;

@WebServlet("/ProductCategoryController")
public class ProductCategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductCategoryDAO dao;

    public ProductCategoryController() {
        super();
        dao = new ProductCategoryDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            String keyword = request.getParameter("keyword");

            if ("delete".equals(action)) {
                String categoryIdStr = request.getParameter("categoryId");
                if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                    int categoryId = Integer.parseInt(categoryIdStr);
                    dao.deleteProductCategory(categoryId);
                }
            }

            ArrayList<ProductCategory> list;
            if (keyword != null && !keyword.trim().isEmpty()) {
                list = dao.searchProductCategory(keyword);
            } else {
                list = dao.getAllProductCategory();
            }
            request.setAttribute("list", list);
            request.getRequestDispatcher("productCategory.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String categoryName = request.getParameter("categoryName");
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            dao.createProductCategory(categoryName);
        }
        doGet(request, response); // Cập nhật lại danh sách sau khi thêm hoặc xóa
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}