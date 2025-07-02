/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.common;

import dao.ItemDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Item;

/**
 *
 * @author namp0
 */
@WebServlet("/getComponentList")
public class GetComponentListServlet extends HttpServlet {

    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        List<Item> items = itemDAO.getItemsByComponentCategory(categoryId, 1000);

        // Build JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("[");
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            String image = (item.getImage() != null) ? item.getImage().getImageContent() : "no-image.png";

            out.print("{");
            out.print("\"id\":\"" + item.getSerialNumber() + "\",");
            out.print("\"name\":\"" + item.getItemName().replace("\"", "") + "\",");
            out.print("\"price\":" + item.getPrice() + ",");
            out.print("\"image\":\"" + image + "\"");
            out.print("}");
            if (i < items.size() - 1) out.print(",");
        }
        out.print("]");
        out.flush();
    }
}

