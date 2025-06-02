package com.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.RoleGroup_DAO;
import com.entity.Account;
import com.entity.RoleGroup;

public class PermissionFilter implements Filter {

    @Autowired
    private RoleGroup_DAO roleGroupDao;

    private static final List<String> ALL_PERMISSIONS = Arrays.asList(
        "THONG_KE",
        "PHIEU_NHAP_XEM", "PHIEU_NHAP_THEM",
        "QUAN_LY_SAN_PHAM_XEM", "QUAN_LY_SAN_PHAM_THEM", "QUAN_LY_SAN_PHAM_SUA", "QUAN_LY_SAN_PHAM_XOA",
        "DANH_MUC_SAN_PHAM_XEM", "DANH_MUC_SAN_PHAM_THEM", "DANH_MUC_SAN_PHAM_SUA", "DANH_MUC_SAN_PHAM_XOA",
        "QUAN_LY_NHAN_HANG_XEM", "QUAN_LY_NHAN_HANG_THEM", "QUAN_LY_NHAN_HANG_SUA", 
        "DANH_SACH_KHACH_HANG_XEM", 
        "DANH_SACH_NHAN_VIEN_XEM", "DANH_SACH_NHAN_VIEN_THEM", "DANH_SACH_NHAN_VIEN_SUA", 
        "QUAN_LY_BAI_VIET_XEM", "QUAN_LY_BAI_VIET_THEM", "QUAN_LY_BAI_VIET_SUA", "QUAN_LY_BAI_VIET_XOA",
        "QUAN_LY_KHUYEN_MAI_XEM", "QUAN_LY_KHUYEN_MAI_THEM", "QUAN_LY_KHUYEN_MAI_SUA", "QUAN_LY_KHUYEN_MAI_XOA",
        "QUAN_LY_DON_HANG_XEM", "QUAN_LY_DON_HANG_THEM", "QUAN_LY_DON_HANG_SUA",
        "DANH_SACH_TAI_KHOAN_XEM", "DANH_SACH_TAI_KHOAN_SUA", 
        "CAI_DAT_CHUNG_XEM", "CAI_DAT_CHUNG_SUA",
        "NHOM_QUYEN_XEM", "NHOM_QUYEN_THEM", "NHOM_QUYEN_SUA", "NHOM_QUYEN_XOA", "PHAN_QUYEN"
    );

    private static final Map<String, String> PERMISSION_MAP = new HashMap<>();

    static {
        // Ánh xạ URL với quyền
        PERMISSION_MAP.put("/admin/statistic", "THONG_KE");

        // Quản lý sản phẩm
        PERMISSION_MAP.put("/admin/product", "QUAN_LY_SAN_PHAM_XEM");
        PERMISSION_MAP.put("/admin/product/add", "QUAN_LY_SAN_PHAM_THEM");
        PERMISSION_MAP.put("/admin/product/edit", "QUAN_LY_SAN_PHAM_SUA");
        PERMISSION_MAP.put("/admin/product/delete", "QUAN_LY_SAN_PHAM_XOA");

        // Danh mục sản phẩm
        PERMISSION_MAP.put("/admin/product-groups", "DANH_MUC_SAN_PHAM_XEM");
        PERMISSION_MAP.put("/admin/product-groups/add", "DANH_MUC_SAN_PHAM_THEM");
        PERMISSION_MAP.put("/admin/product-groups/edit", "DANH_MUC_SAN_PHAM_SUA");
        PERMISSION_MAP.put("/admin/product-groups/delete", "DANH_MUC_SAN_PHAM_XOA");

        // Quản lý nhãn hàng
        PERMISSION_MAP.put("/admin/brand", "QUAN_LY_NHAN_HANG_XEM");
        PERMISSION_MAP.put("/admin/brand/add", "QUAN_LY_NHAN_HANG_THEM");
        PERMISSION_MAP.put("/admin/brand/edit", "QUAN_LY_NHAN_HANG_SUA");

        // Danh sách khách hàng
        PERMISSION_MAP.put("/admin/customer", "DANH_SACH_KHACH_HANG_XEM");

        // Danh sách nhân viên
        PERMISSION_MAP.put("/admin/employee", "DANH_SACH_NHAN_VIEN_XEM");
        PERMISSION_MAP.put("/admin/employee/add", "DANH_SACH_NHAN_VIEN_THEM");
        PERMISSION_MAP.put("/admin/employee/edit", "DANH_SACH_NHAN_VIEN_SUA");

        // Quản lý bài viết
        PERMISSION_MAP.put("/admin/blog", "QUAN_LY_BAI_VIET_XEM");
        PERMISSION_MAP.put("/admin/blog/add", "QUAN_LY_BAI_VIET_THEM");
        PERMISSION_MAP.put("/admin/blog/edit", "QUAN_LY_BAI_VIET_SUA");
        PERMISSION_MAP.put("/admin/blog/delete", "QUAN_LY_BAI_VIET_XOA");

        // Quản lý khuyến mãi
        PERMISSION_MAP.put("/admin/discount", "QUAN_LY_KHUYEN_MAI_XEM");
        PERMISSION_MAP.put("/admin/discount/add", "QUAN_LY_KHUYEN_MAI_THEM");
        PERMISSION_MAP.put("/admin/discount/edit", "QUAN_LY_KHUYEN_MAI_SUA");
        PERMISSION_MAP.put("/admin/discount/delete", "QUAN_LY_KHUYEN_MAI_XOA");

        // Quản lý đơn hàng
        PERMISSION_MAP.put("/admin/order", "QUAN_LY_DON_HANG_XEM");
        PERMISSION_MAP.put("/admin/order/add", "QUAN_LY_DON_HANG_THEM");
        PERMISSION_MAP.put("/admin/order/edit", "QUAN_LY_DON_HANG_SUA");

        // Danh sách tài khoản
        PERMISSION_MAP.put("/admin/account", "DANH_SACH_TAI_KHOAN_XEM");
        PERMISSION_MAP.put("/admin/account/edit", "DANH_SACH_TAI_KHOAN_SUA");

        // Cài đặt chung
        PERMISSION_MAP.put("/admin/generalSettings", "CAI_DAT_CHUNG_XEM");
        PERMISSION_MAP.put("/admin/generalSettings/update", "CAI_DAT_CHUNG_SUA");

        // Nhóm quyền
        PERMISSION_MAP.put("/admin/role", "NHOM_QUYEN_XEM");
        PERMISSION_MAP.put("/admin/role/add", "NHOM_QUYEN_THEM");
        PERMISSION_MAP.put("/admin/role/edit", "NHOM_QUYEN_SUA");
        PERMISSION_MAP.put("/admin/role/delete", "NHOM_QUYEN_XOA");

        // Phiếu nhập
        PERMISSION_MAP.put("/admin/import", "PHIEU_NHAP_XEM");
        PERMISSION_MAP.put("/admin/import/add", "PHIEU_NHAP_THEM");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Chỉ áp dụng cho các URL bắt đầu bằng /admin
        if (!requestURI.startsWith(contextPath + "/admin")) {
            chain.doFilter(request, response);
            return;
        }

        // Bỏ qua kiểm tra quyền cho trang đăng nhập, access-denied và tài nguyên tĩnh
        if (requestURI.startsWith(contextPath + "/resources/") ||
            requestURI.equals(contextPath + "/admin/login.htm") ||
            requestURI.equals(contextPath + "/admin/logout.htm") ||
            requestURI.equals(contextPath + "/admin/access-denied.htm")) {
            chain.doFilter(request, response);
            return;
        }

        // Bỏ qua kiểm tra quyền cho các trang cá nhân (profile)
        if (requestURI.startsWith(contextPath + "/admin/profile")) {
            chain.doFilter(request, response);
            return;
        }

        // Lấy thông tin tài khoản từ session
        Account account = (session != null) ? (Account) session.getAttribute("account") : null;
        List<String> permissions = (session != null) ? (List<String>) session.getAttribute("permissions") : null;

        // Tìm quyền yêu cầu cho URL
        String requiredPermission = null;
        for (Map.Entry<String, String> entry : PERMISSION_MAP.entrySet()) {
            if (requestURI.startsWith(contextPath + entry.getKey())) {
                requiredPermission = entry.getValue();
                break;
            }
        }

        // Nếu không tìm thấy quyền yêu cầu, cho phép truy cập
//        if (requiredPermission == null) {
//            chain.doFilter(request, response);
//            return;
//        }

        // Kiểm tra xem người dùng có quyền cần thiết hay không
        if (permissions == null || !permissions.contains(requiredPermission)) {
            httpResponse.sendRedirect(contextPath + "/admin/access-denied.htm");
            return;
        }

        // Nếu có quyền, cho phép truy cập
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}