package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String method = httpRequest.getMethod();

        // Chỉ áp dụng cho các URL bắt đầu bằng /admin
        if (!requestURI.startsWith(contextPath + "/admin")) {
            chain.doFilter(request, response);
            return;
        }

        // Cho phép truy cập tài nguyên tĩnh và trang đăng nhập
        if (requestURI.startsWith(contextPath + "/resources/") ||
            requestURI.equals(contextPath + "/admin/login.htm")) {
            chain.doFilter(request, response);
            // Không hủy session ngay sau yêu cầu đăng nhập (POST)
            if (requestURI.equals(contextPath + "/admin/login.htm") && "POST".equalsIgnoreCase(method)) {
                return;
            }
            return;
        }

        // Kiểm tra trạng thái đăng nhập
        boolean isLoggedIn = (session != null && session.getAttribute("account") != null);

        if (!isLoggedIn) {
            httpResponse.sendRedirect(contextPath + "/admin/login.htm");
            return;
        }

        // Tiếp tục xử lý yêu cầu
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}
}