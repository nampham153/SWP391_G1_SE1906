/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.BrandDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Brand;


/**
 *
 * @author namp0
 */
@WebServlet("/admin/brand")
public class BrandServlet extends HttpServlet {

    private final BrandDAO dao = new BrandDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        if (action == null) action = "list";

        switch (action) {
            case "edit":
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    Brand b = dao.getBrandById(id);
                    request.setAttribute("editBrand", b);
                }
                break;
            case "delete":
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    dao.deleteBrand(id);
                }
                response.sendRedirect("brand");
                return;
            default:
                List<Brand> list = dao.getAllBrands();
                request.setAttribute("brands", list);
                break;
        }

        request.getRequestDispatcher("brand-manage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String idStr = request.getParameter("id");
        String name = request.getParameter("name");
        String action = request.getParameter("action");

        try {
            if (name == null || name.trim().isEmpty()) {
                request.setAttribute("error", "Tên brand không được để trống.");
                request.setAttribute("brands", dao.getAllBrands());
                request.getRequestDispatcher("brand-manage.jsp").forward(request, response);
                return;
            }

            Brand brand = new Brand();
            brand.setBrandName(name.trim());

            if ("edit".equals(action)) {
                int id = Integer.parseInt(idStr);
                brand.setBrandId(id);
                dao.updateBrand(brand);
            } else {
                dao.insertBrand(brand);
            }

            response.sendRedirect("brand");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi xử lý dữ liệu: " + e.getMessage());
            request.setAttribute("brands", dao.getAllBrands());
            request.getRequestDispatcher("brand-manage.jsp").forward(request, response);
        }
    }
}