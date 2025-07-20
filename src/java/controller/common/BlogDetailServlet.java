/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import dao.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;

/**
 *
 * @author namp0
 */
@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/blog-detail"})
public class BlogDetailServlet extends HttpServlet {

    private BlogDAO blogDAO = new BlogDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String id = request.getParameter("blogId");
        if (id == null) {
            response.sendRedirect("blog");
            return;
        }

        try {
            int blogId = Integer.parseInt(id);
            Blog blog = blogDAO.getBlogById(blogId);
            if (blog == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            request.setAttribute("blog", blog);
        request.setAttribute("pageContent1", "blog-detail.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("blog");
        }
    }
}
