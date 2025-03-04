<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="modal-header">
    <h5 class="modal-title" id="exampleModalCenterTitle">Chi Tiết Khuyến Mãi</h5>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close" aria-hidden="true">
        <span>&times;</span>
    </button>
</div>
<div class="modal-body">
    <p><strong>Mã Khuyến Mãi:</strong> ${discount.discountId}</p>
    <p><strong>Mô Tả Khuyến Mãi:</strong> ${discount.discountDesc}</p>
    <p><strong>Phần Trăm Khuyến Mãi:</strong> ${discount.discountAmount}%</p>
    <p><strong>Số Tiền Tối Thiểu:</strong> <fmt:formatNumber value="${discount.minimumAmount}" type="number" maxFractionDigits="0" groupingUsed="true"/> ₫</p>
    <p><strong>Số Lượng:</strong> ${discount.usageLimit}</p>
    <p><strong>Ngày Bắt Đầu:</strong> <fmt:formatDate value="${discount.applyStartDate}" pattern="dd/MM/yyyy" /></p>
    <p><strong>Ngày Bắt Đầu:</strong> <fmt:formatDate value="${discount.applyEndDate}" pattern="dd/MM/yyyy" /></p>
    <p><strong>Trạng Thái:</strong> 
        <c:choose>
            <c:when test="${discount.status == 'Active'}">
                <span class="badge badge-success">Hoạt động</span>
            </c:when>
            <c:otherwise>
                <span class="badge badge-danger">Ngừng hoạt động</span>
            </c:otherwise>
        </c:choose>
    </p>
    
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-outline-primary" data-dismiss="modal" aria-label="Close">Close</button>
</div>