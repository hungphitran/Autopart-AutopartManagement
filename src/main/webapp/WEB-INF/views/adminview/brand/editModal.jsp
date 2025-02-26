<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-header">
    <h5 class="modal-title" id="exampleModalCenterTitle">Sửa Thông Tin Nhãn Hàng</h5>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close" aria-hidden="true">
        <span>&times;</span>
    </button>
</div>
<div class="modal-body">
    <form id="editBrandForm" action="${pageContext.request.contextPath}/admin/brand/edit.htm" method="post">
        <div class="form-group">
            <label for="brandName"><strong>Tên Nhãn Hàng:</strong></label>
            <input type="text" class="form-control" id="brandName" name="brandName" value="${brand.brandName}" required>
        </div>
        <div class="form-group">
            <label for="status"><strong>Trạng Thái:</strong></label>
            <select class="form-control" id="status" name="status" required>
                <option value="Active" ${brand.status == 'Active' ? 'selected' : ''}>Hoạt động</option>
                <option value="Inactive" ${brand.status == 'Inactive' ? 'selected' : ''}>Ngừng hoạt động</option>
            </select>
        </div>
        <input type="hidden" name="brandId" value="${brand.brandId}">
    </form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Đóng</button>
    <button type="submit" form="editBrandForm" class="btn btn-primary">Lưu Thay Đổi</button>
</div>