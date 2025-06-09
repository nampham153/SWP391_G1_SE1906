package controller1;

import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet("/ProductController")
public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO dao;

    public ProductController() {
        super();
        dao = new ProductDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            String id = request.getParameter("id");

            if ("delete".equals(action) && id != null) {
                dao.deleteProduct(id);
            } else if ("edit".equals(action) && id != null) {
                Product product = dao.getProductById(id);
                request.setAttribute("product", product);
            }

            request.setAttribute("list", dao.getAllProductsWithItems());
            request.getRequestDispatcher("product.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String categoryIdStr = request.getParameter("categoryId");
        String itemName = request.getParameter("itemName");
        String stockStr = request.getParameter("stock");
        String priceStr = request.getParameter("price");
        String viewsStr = request.getParameter("views");

        if (categoryIdStr != null && !categoryIdStr.isEmpty() && itemName != null && !itemName.isEmpty() &&
            stockStr != null && !stockStr.isEmpty() && priceStr != null && !priceStr.isEmpty() &&
            viewsStr != null && !viewsStr.isEmpty()) {
            int categoryId = Integer.parseInt(categoryIdStr);
            int stock = Integer.parseInt(stockStr);
            double price = Double.parseDouble(priceStr);
            int views = Integer.parseInt(viewsStr);
            if ("add".equals(action)) {
                dao.createProduct(id, categoryId, itemName, stock, price, views);
            } else if ("edit".equals(action) && id != null) {
                dao.updateProduct(id, categoryId, itemName, stock, price, views);
            }
        }
        doGet(request, response); // Cập nhật lại danh sách
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}