<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-header">
    <h5 class="modal-title" id="exampleModalCenterTitle">Chi Tiết Thông Tin Khách Hàng</h5>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close" aria-hidden="true">
        <span>&times;</span>
    </button>
</div>
<div class="modal-body">
    <p><strong>Họ và Tên:</strong> ${customer.fullName}</p>
    <p><strong>Số Điện Thoại:</strong> ${customer.email}</p>
    <p><strong>Địa Chỉ:</strong> ${customer.address}</p>
    <p><strong>Trạng Thái:</strong> ${customer.status}</p>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-outline-primary" data-dismiss="modal" aria-label="Close">Đóng</button>
</div>