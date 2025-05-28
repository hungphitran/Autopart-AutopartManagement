<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="modal-header">
    <h5 class="modal-title" id="exampleModalCenterTitle">Sửa Thông Tin Tài Khoản</h5>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close" aria-hidden="true">
        <span>&times;</span>
    </button>
</div>
<div class="modal-body">
    <form:form id="editAccountForm" modelAttribute="account" action="${pageContext.request.contextPath}/admin/account/edit.htm" method="post">
        <div class="form-group">
            <label for="email"><strong>Email:</strong></label>
            <form:input type="text" class="form-control" path="email" readonly="true" required="true"/>
        </div>
        <div class="form-group">
            <label for="password"><strong>Mật Khẩu:</strong></label>
            <form:input type="password" class="form-control" path="password" required="true"/>
        </div>
        <div class="form-group">
            <label for="permission"><strong>Nhóm Quyền:</strong></label>
            <form:select path="permission" class="form-control mb-3" required="true">
                <form:option value="" label="-- Chọn nhóm quyền --" disabled="true"/>
                <form:options items="${roleGroup}" itemValue="roleGroupId" itemLabel="roleGroupName"/>
            </form:select>
        </div>
        <div class="form-group">
            <label for="status"><strong>Trạng Thái:</strong></label>
            <form:select path="status" class="form-control" required="true">
                <form:option value="Active" label="Hoạt động"/>
                <form:option value="Inactive" label="Ngừng hoạt động"/>
            </form:select>
        </div>
    </form:form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-outline-secondary" data-dismiss="modal">Đóng</button>
    <button type="submit" form="editAccountForm" class="btn btn-primary">Lưu Thay Đổi</button>
</div>