package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;

import java.io.IOException;
import java.util.Arrays;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI().substring(req.getContextPath().length());

        Account acc = (session != null) ? (Account) session.getAttribute("account") : null;
        Integer role = (acc != null) ? acc.getRoleId() : null;

        System.out.println(">>> AuthorizationFilter is running");
        System.out.println("URI: " + uri);
        System.out.println("Role: " + role);

        String[] exactPublicPaths = {
            "/home",
            "/login",
            "/register",
            "/index",
            "/product-detail",
            "/cart",
            "/checkout",
            "/checkout-success" ,
            "/ajaxServlet" ,
            "/vnpay_return", 
            "/cart-size",
            "/verify",
            "/build-pc",
            "/contact",
            "/getComponentList",
            "/forgot-password"
        };

        String[] prefixPublicPaths = {
            "/css/", "/js/", "/assets/", "/img/", "/images/",
            "/fonts/", "/vendor/", "/public/"
        };

        boolean isExactPublic = Arrays.stream(exactPublicPaths).anyMatch(uri::equals);
        boolean isPrefixPublic = Arrays.stream(prefixPublicPaths).anyMatch(uri::startsWith);
        boolean isStaticResource = uri.matches(".*\\.(css|js|png|jpg|jpeg|gif|svg|ico|woff2?)$");

        boolean isPublic = isExactPublic || isPrefixPublic || isStaticResource;

        if (isPublic) {
            chain.doFilter(request, response);
            return;
        }

        if (role == null) {
            System.out.println(">>> Not logged in. Redirecting to login.");
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (uri.startsWith("/admin")) {
            if (role == 3) {
                chain.doFilter(request, response);
            } else {
                System.out.println(">>> Access denied to admin page");
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            }
            return;
        }
if (uri.startsWith("/api/") || uri.endsWith("Servlet")) {
    chain.doFilter(request, response);
    return;
}
        if (uri.startsWith("/staff")) {
            if (role == 2 || role == 3) {
                chain.doFilter(request, response);
            } else {
                System.out.println(">>> Access denied to staff page");
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            }
            return;
        }

        if (uri.startsWith("/customer")) {
            if (role == 1 || role == 2 || role == 3) {
                chain.doFilter(request, response);
            } else {
                System.out.println(">>> Access denied to customer page");
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied.");
            }
            return;
        }
        chain.doFilter(request, response);
    }
}
