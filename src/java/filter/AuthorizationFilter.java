/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        Integer role = (session != null) ? (Integer) session.getAttribute("role") : null;

        // Cho phép các trang công khai
        if (uri.endsWith("login") || uri.endsWith("register") || uri.endsWith("index.jsp") || uri.contains("/public/") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".png")) {
            chain.doFilter(request, response);
            return;
        }

        // Nếu chưa đăng nhập
        if (role == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Phân quyền
        if (uri.contains("/admin")) {
            if (role == 2) {
                chain.doFilter(request, response);
            } else {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            }
            return;
        }

        if (uri.contains("/staff")) {
            if (role == 2 || role == 3) {
                chain.doFilter(request, response);
            } else {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            }
            return;
        }

        if (uri.contains("/customer")) {
            if (role == 1 || role == 2 || role == 3) {
                chain.doFilter(request, response);
            } else {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            }
            return;
        }

        // Các trang không phân quyền rõ thì cho qua
        chain.doFilter(request, response);
    }
}
