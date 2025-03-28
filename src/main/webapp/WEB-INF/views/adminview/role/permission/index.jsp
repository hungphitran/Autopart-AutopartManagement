<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Phân Quyền</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
    
    <style>
	    .roleName:hover {
	        cursor: pointer;
	    }
	</style>
</head>
<body id="page-top">
    <div id="wrapper">
        <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
        
        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

                <div class="container-fluid" id="container-wrapper">
                    <div class="col-lg-12" id="form-permissions">
                        <div class="card mb-4">
                            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">Phân quyền</h6>
                            </div>
                            <div class="p-3">
                                <form action="${pageContext.request.contextPath}/admin/role/updatePermissions.htm" method="POST">
                                    <div data-records="${roleGroup}">
                                        <table class="table table-sm">
                                            <thead>
                                                <tr>
                                                    <th>Tính năng</th>
                                                    <c:forEach items="${roleGroup}" var="role" varStatus="loop">
                                                        <th class="text-center roleName" onclick="toggleColumn(${loop.index})">${role.roleGroupName}</th>
                                                    </c:forEach>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Hidden row for IDs -->
                                                <tr class="d-none" data-name="id">
                                                    <td></td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="hidden" name="roleGroupIds" value="${role.roleGroupId}">
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Thống kê -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Thống kê</b></td></tr>
                                                <tr data-name="THONG_KE">
                                                    <td>Xem thống kê</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="THONG_KE" 
                                                                   ${role.permissions.contains('THONG_KE') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Phiếu nhập -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Phiếu nhập</b></td></tr>
                                                <tr data-name="PHIEU_NHAP_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="PHIEU_NHAP_XEM" 
                                                                   ${role.permissions.contains('PHIEU_NHAP_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="PHIEU_NHAP_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="PHIEU_NHAP_THEM" 
                                                                   ${role.permissions.contains('PHIEU_NHAP_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Quản lý sản phẩm -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Quản lý sản phẩm</b></td></tr>
                                                <tr data-name="QUAN_LY_SAN_PHAM_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_SAN_PHAM_XEM" 
                                                                   ${role.permissions.contains('QUAN_LY_SAN_PHAM_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_SAN_PHAM_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_SAN_PHAM_THEM" 
                                                                   ${role.permissions.contains('QUAN_LY_SAN_PHAM_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_SAN_PHAM_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_SAN_PHAM_SUA" 
                                                                   ${role.permissions.contains('QUAN_LY_SAN_PHAM_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_SAN_PHAM_XOA">
                                                    <td>Xóa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_SAN_PHAM_XOA" 
                                                                   ${role.permissions.contains('QUAN_LY_SAN_PHAM_XOA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Danh mục sản phẩm -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Danh mục sản phẩm</b></td></tr>
                                                <tr data-name="DANH_MUC_SAN_PHAM_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_MUC_SAN_PHAM_XEM" 
                                                                   ${role.permissions.contains('DANH_MUC_SAN_PHAM_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="DANH_MUC_SAN_PHAM_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_MUC_SAN_PHAM_THEM" 
                                                                   ${role.permissions.contains('DANH_MUC_SAN_PHAM_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="DANH_MUC_SAN_PHAM_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_MUC_SAN_PHAM_SUA" 
                                                                   ${role.permissions.contains('DANH_MUC_SAN_PHAM_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="DANH_MUC_SAN_PHAM_XOA">
                                                    <td>Xóa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_MUC_SAN_PHAM_XOA" 
                                                                   ${role.permissions.contains('DANH_MUC_SAN_PHAM_XOA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Quản lý nhãn hàng -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Quản lý nhãn hàng</b></td></tr>
                                                <tr data-name="QUAN_LY_NHAN_HANG_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_NHAN_HANG_XEM" 
                                                                   ${role.permissions.contains('QUAN_LY_NHAN_HANG_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_NHAN_HANG_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_NHAN_HANG_THEM" 
                                                                   ${role.permissions.contains('QUAN_LY_NHAN_HANG_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_NHAN_HANG_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_NHAN_HANG_SUA" 
                                                                   ${role.permissions.contains('QUAN_LY_NHAN_HANG_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Danh sách khách hàng -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Danh sách khách hàng</b></td></tr>
                                                <tr data-name="DANH_SACH_KHACH_HANG_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_SACH_KHACH_HANG_XEM" 
                                                                   ${role.permissions.contains('DANH_SACH_KHACH_HANG_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Danh sách nhân viên -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Danh sách nhân viên</b></td></tr>
                                                <tr data-name="DANH_SACH_NHAN_VIEN_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_SACH_NHAN_VIEN_XEM" 
                                                                   ${role.permissions.contains('DANH_SACH_NHAN_VIEN_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="DANH_SACH_NHAN_VIEN_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_SACH_NHAN_VIEN_THEM" 
                                                                   ${role.permissions.contains('DANH_SACH_NHAN_VIEN_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="DANH_SACH_NHAN_VIEN_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_SACH_NHAN_VIEN_SUA" 
                                                                   ${role.permissions.contains('DANH_SACH_NHAN_VIEN_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Quản lý bài viết -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Quản lý bài viết</b></td></tr>
                                                <tr data-name="QUAN_LY_BAI_VIET_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_BAI_VIET_XEM" 
                                                                   ${role.permissions.contains('QUAN_LY_BAI_VIET_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_BAI_VIET_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_BAI_VIET_THEM" 
                                                                   ${role.permissions.contains('QUAN_LY_BAI_VIET_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_BAI_VIET_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_BAI_VIET_SUA" 
                                                                   ${role.permissions.contains('QUAN_LY_BAI_VIET_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_BAI_VIET_XOA">
                                                    <td>Xóa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_BAI_VIET_XOA" 
                                                                   ${role.permissions.contains('QUAN_LY_BAI_VIET_XOA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Quản lý khuyến mãi -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Quản lý khuyến mãi</b></td></tr>
                                                <tr data-name="QUAN_LY_KHUYEN_MAI_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_KHUYEN_MAI_XEM" 
                                                                   ${role.permissions.contains('QUAN_LY_KHUYEN_MAI_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_KHUYEN_MAI_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_KHUYEN_MAI_THEM" 
                                                                   ${role.permissions.contains('QUAN_LY_KHUYEN_MAI_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_KHUYEN_MAI_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_KHUYEN_MAI_SUA" 
                                                                   ${role.permissions.contains('QUAN_LY_KHUYEN_MAI_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_KHUYEN_MAI_XOA">
                                                    <td>Xóa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_KHUYEN_MAI_XOA" 
                                                                   ${role.permissions.contains('QUAN_LY_KHUYEN_MAI_XOA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Quản lý đơn hàng -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Quản lý đơn hàng</b></td></tr>
                                                <tr data-name="QUAN_LY_DON_HANG_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_DON_HANG_XEM" 
                                                                   ${role.permissions.contains('QUAN_LY_DON_HANG_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_DON_HANG_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_DON_HANG_THEM" 
                                                                   ${role.permissions.contains('QUAN_LY_DON_HANG_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="QUAN_LY_DON_HANG_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="QUAN_LY_DON_HANG_SUA" 
                                                                   ${role.permissions.contains('QUAN_LY_DON_HANG_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Danh sách tài khoản -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Danh sách tài khoản</b></td></tr>
                                                <tr data-name="DANH_SACH_TAI_KHOAN_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_SACH_TAI_KHOAN_XEM" 
                                                                   ${role.permissions.contains('DANH_SACH_TAI_KHOAN_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="DANH_SACH_TAI_KHOAN_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="DANH_SACH_TAI_KHOAN_SUA" 
                                                                   ${role.permissions.contains('DANH_SACH_TAI_KHOAN_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Cài đặt chung -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Cài đặt chung</b></td></tr>
                                                <tr data-name="CAI_DAT_CHUNG_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="CAI_DAT_CHUNG_XEM" 
                                                                   ${role.permissions.contains('CAI_DAT_CHUNG_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="CAI_DAT_CHUNG_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="CAI_DAT_CHUNG_SUA" 
                                                                   ${role.permissions.contains('CAI_DAT_CHUNG_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Nhóm quyền -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Nhóm quyền</b></td></tr>
                                                <tr data-name="NHOM_QUYEN_XEM">
                                                    <td>Xem</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="NHOM_QUYEN_XEM" 
                                                                   ${role.permissions.contains('NHOM_QUYEN_XEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="NHOM_QUYEN_THEM">
                                                    <td>Thêm mới</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="NHOM_QUYEN_THEM" 
                                                                   ${role.permissions.contains('NHOM_QUYEN_THEM') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="NHOM_QUYEN_SUA">
                                                    <td>Chỉnh sửa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="NHOM_QUYEN_SUA" 
                                                                   ${role.permissions.contains('NHOM_QUYEN_SUA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                                <tr data-name="NHOM_QUYEN_XOA">
                                                    <td>Xóa</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="NHOM_QUYEN_XOA" 
                                                                   ${role.permissions.contains('NHOM_QUYEN_XOA') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>

                                                <!-- Phân quyền -->
                                                <tr><td colspan="${roleGroup.size() + 1}"><b>Phân quyền</b></td></tr>
                                                <tr data-name="PHAN_QUYEN">
                                                    <td>Quản lý phân quyền</td>
                                                    <c:forEach items="${roleGroup}" var="role">
                                                        <td class="text-center">
                                                            <input type="checkbox" 
                                                                   name="permissions[${role.roleGroupId}]" 
                                                                   value="PHAN_QUYEN" 
                                                                   ${role.permissions.contains('PHAN_QUYEN') ? 'checked' : ''}>
                                                        </td>
                                                    </c:forEach>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="d-flex justify-content-end mt-3">
							            <button type="submit" class="btn btn-primary" id="button-submit">Cập nhật</button>
							        </div>
                                </form>
                            </div>
                        </div>
                    </div>

                    <!-- Modal Logout -->
                    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModal" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabelLogout">Đăng xuất</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p>Bạn có muốn đăng xuất không?</p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
                                    <a href="login.html" class="btn btn-primary">Đăng xuất</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Scroll to top -->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
    <script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
    
    <script>
	    function toggleColumn(columnIndex) {
	        const rows = document.querySelectorAll('tbody tr:not(.d-none)');
	        
	        // Tìm hàng đầu tiên có checkbox
	        let firstCheckbox = null;
	        let shouldCheck = true;
	        for (let row of rows) {
	            const cells = row.getElementsByTagName('td');
	            if (cells.length > columnIndex + 1) {
	                firstCheckbox = cells[columnIndex + 1].querySelector('input[type="checkbox"]');
	                if (firstCheckbox) {
	                    shouldCheck = !firstCheckbox.checked;
	                    break;
	                }
	            }
	        }
	
	        // Duyệt qua tất cả các hàng để tích/bỏ tích
	        rows.forEach(row => {
	            const cells = row.getElementsByTagName('td');
	            if (cells.length > columnIndex + 1) {
	                const checkbox = cells[columnIndex + 1].querySelector('input[type="checkbox"]');
	                if (checkbox) {
	                    checkbox.checked = shouldCheck;
	                }
	            }
	        });
	    }
	</script>
</body>
</html>