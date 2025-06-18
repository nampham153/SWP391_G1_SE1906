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

        String[] publicPaths = {
            "/home",
            "/login",
            "/register",
            "/index.jsp",
            "/css/",
            "/js/",
            "/img/",
            "/images/",
            "/fonts/",
            "/vendor/",
            "/public/"
        };

        // Cho phép nếu là tài nguyên tĩnh hoặc trong danh sách public
        boolean isPublic = false;
        for (String path : publicPaths) {
            if (uri.contains(path)) {
                isPublic = true;
                break;
            }
        }

        if (isPublic || uri.matches(".*\\.(css|js|png|jpg|jpeg|gif|svg|ico|woff2?)$")) {
            chain.doFilter(request, response);
            return;
        }

        // Nếu chưa đăng nhập
        if (role == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Phân quyền theo role
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

        chain.doFilter(request, response);
    }
}
