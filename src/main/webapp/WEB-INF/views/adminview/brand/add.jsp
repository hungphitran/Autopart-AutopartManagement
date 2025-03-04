<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Thêm nhãn hàng</title>

	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
</head>
<body id="page-top">
	<div id="wrapper">
	    <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
	    
	    <div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
	    		<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

				<div class="container-fluid" id="container-wrapper">
                    <div class="card mb-4">
                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary">Thêm Nhãn Hàng</h6>
                        </div>
                        <div class="card-body">
                            <form:form action="${pageContext.request.contextPath}/admin/brand/add.htm" method="post" modelAttribute="brand">
                                <div class="row">
                                    <div class="col-lg-12">
                                    	 <div class="form-group">
                                            <label for="brandId">Mã nhãn hàng<span class="required-text">*</span></label>
                                            <input required type="text" class="form-control" id="brandId" name="brandId" value="${nextBrandId}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="brandName">Tên nhãn hàng <span class="required-text">*</span></label>
                                            <input type="text" class="form-control" id="brandName" name="brandName" placeholder="Nhập tên nhãn hàng" required>
                                        </div>
                                        <div class="form-group d-flex">
                                            <label for="status" class="mr-4">Trạng thái hoạt động</label>
                                            <div class="custom-control custom-switch ml-4">
                                                <input type="checkbox" class="custom-control-input" name="status" id="status" value="Active" checked>
                                                <label class="custom-control-label" for="status">Hoạt động</label>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-primary">Lưu nhãn hàng</button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
		
                    <!-- Modal Logout -->
                    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabelLogout"
                        aria-hidden="true">
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

	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
	<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
</body>
</html>
