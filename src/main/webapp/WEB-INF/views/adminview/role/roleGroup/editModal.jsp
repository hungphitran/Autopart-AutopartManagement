<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-header">
    <h5 class="modal-title" id="EditModalLabel">Sửa Nhóm Quyền</h5>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>
<div class="modal-body">
	<form id="editRoleGroupForm" action="${pageContext.request.contextPath}/admin/role/edit.htm" method="POST">
        <div class="form-group">
            <label for="editRoleGroupId">Mã Nhóm Quyền</label>
            <input type="text" class="form-control" id="editRoleGroupId" name="roleGroupId" value="${roleGroup.roleGroupId}" readonly required>
        </div>
        <div class="form-group">
            <label for="editRoleGroupName">Tên Nhóm Quyền</label>
            <input type="text" class="form-control" id="editRoleGroupName" name="roleGroupName" placeholder="Nhập tên nhóm quyền" value="${roleGroup.roleGroupName}" required>
        </div>
        <div class="form-group">
            <label for="editDescription">Mô Tả</label>
            <textarea class="form-control" id="editDescription" name="description" placeholder="Nhập mô tả" rows="3">${roleGroup.description}</textarea>
        </div>
        <div class="form-group">
		   <label for="status"><strong>Trạng Thái:</strong></label>
		   <select class="form-control" id="status" name="status" required>
		       <option value="Active" ${roleGroup.status == 'Active' ? 'selected' : ''}>Hoạt động</option>
		       <option value="Inactive" ${roleGroup.status == 'Inactive' ? 'selected' : ''}>Ngừng hoạt động</option>
		    </select>
		</div>
	</form>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Hủy</button>
    <button type="submit" form="editRoleGroupForm" class="btn btn-primary">Lưu</button>
</div>