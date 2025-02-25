<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="modal-header">
    <h5 class="modal-title" id="exampleModalCenterTitle">Chi Tiết Nhãn Hàng</h5>
    <button type="button" class="close" data-dismiss="modal" aria-label="Close" aria-hidden="true">
        <span>&times;</span>
    </button>
</div>
<div class="modal-body">
    <p><strong>Tên Nhãn Hàng:</strong> ${brand.brandName}</p>
    <p><strong>Trạng Thái:</strong> 
        <c:choose>
            <c:when test="${brand.status == 'Active'}">
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