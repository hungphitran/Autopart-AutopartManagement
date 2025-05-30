<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Danh sách sản phẩm</title>

<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link
	href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/admincss/base.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />"
	rel="stylesheet">
</head>
<body id="page-top">
	<div id="wrapper">
		<jsp:include page="/WEB-INF/mixins/adminnav.jsp" />

		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

				<div class="container-fluid" id="container-wrapper">
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">Chi Tiết Sản Phẩm</h1>
					</div>

					<div class="row">
						<div class="col-lg-4">
							<!-- Ảnh sản phẩm -->
							<div class="card mb-4">
							    <div class="card-header py-3">
							        <h6 class="m-0 font-weight-bold text-primary">Ảnh Sản Phẩm</h6>
							    </div>
							    <div class="card-body">
							        <c:forEach items="${imgUrls}" var="url">
							            <c:choose>
										    <c:when test="${fn:startsWith(url, 'https')}">
										        <img src="${url}" 
										             alt="Sản phẩm" 
										             class="img-fluid" 
										             style="max-width: 100px; max-height: 100px; margin-right: 10px;">
										    </c:when>
										    <c:otherwise>
										        <img src="${pageContext.request.contextPath}/resources/img/${url}" 
										             alt="Sản phẩm" 
										             class="img-fluid" 
										             style="max-width: 100px; max-height: 100px; margin-right: 10px;">
										    </c:otherwise>
										</c:choose>
							        </c:forEach>
							    </div>
							</div>

							<!-- Thông tin trạng thái -->
							<div class="card mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">Trạng Thái</h6>
								</div>
								<div class="card-body">
									<c:choose>
										<c:when test="${product.status == 'Active'}">
											<div class="mb-3">
												<span class="badge badge-success">Đang hoạt động</span>
											</div>
										</c:when>
										<c:otherwise>
											<div class="mb-3">
												<span class="badge badge-danger">Ngừng hoạt động</span>
											</div>
										</c:otherwise>
									</c:choose>
									<div class="mb-3">
										<a
											href="${pageContext.request.contextPath}/admin/product/edit.htm?productId=${product.productId}"
											class="btn btn-primary btn-block">Chỉnh sửa sản phẩm</a>
									</div>
								</div>
							</div>
						</div>

						<div class="col-lg-8">
							<!-- Thông tin cơ bản -->
							<div class="card mb-4">
								<div
									class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
									<h6 class="m-0 font-weight-bold text-primary">Thông Tin Cơ
										Bản</h6>
								</div>
								<div class="card-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="font-weight-bold">Mã sản phẩm:</label>
												<p>
													<c:out value="${product.productId}" />
												</p>
											</div>
											<div class="form-group">
												<label class="font-weight-bold">Tên sản phẩm:</label>
												<p>
													<c:out value="${product.productName}" />
												</p>
											</div>
											<div class="form-group">
												<label class="font-weight-bold">Danh mục:</label>
												<p>${groupName}</p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="font-weight-bold">Thương hiệu:</label>
												<p>
													<c:out value="${brandName}" />
												</p>
											</div>
											<div class="form-group">
												<label class="font-weight-bold">Đơn vị tính:</label>
												<p>
													<c:out value="${product.unit}" />
												</p>
											</div>
											<div class="form-group">
												<label class="font-weight-bold">Khối lượng:</label>
												<p>
													<c:out value="${product.weight}" />
													kg
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>

							<!-- Thông tin giá & kho -->
							<div class="card mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">Thông Tin
										Giá & Kho</h6>
								</div>
								<div class="card-body">
									<div class="row">
										<div class="col-md-6">
											<div class="form-group">
												<label class="font-weight-bold">Giá bán:</label>
												<p class="text-danger">
													<fmt:formatNumber value="${product.salePrice}"
														type="number" maxFractionDigits="0" groupingUsed="true" />
													₫
												</p>
											</div>
											<div class="form-group">
												<label class="font-weight-bold">Giá nhập:</label>
												<p>
													<fmt:formatNumber value="${product.costPrice}"
														type="number" maxFractionDigits="0" groupingUsed="true" />
													₫
												</p>
											</div>
										</div>
										<div class="col-md-6">
											<div class="form-group">
												<label class="font-weight-bold">Tồn kho:</label>
												<p>
													<c:out value="${product.stock}" />
												</p>
											</div>
										</div>
									</div>
								</div>
							</div>

							<!-- Mô tả sản phẩm -->
							<div class="card">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">Mô Tả Sản
										Phẩm</h6>
								</div>
								<div class="card-body">
									<p>
										<c:out value="${product.description}" />
									</p>
								</div>
							</div>
						</div>
					</div>

					<!-- Modal Logout -->
					<div class="modal fade" id="logoutModal" tabindex="-1"
						role="dialog" aria-labelledby="logoutModal" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabelLogout">Đăng
										xuất</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									<p>Bạn có muốn đăng xuất không?</p>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-outline-primary"
										data-dismiss="modal">Không</button>
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
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
	<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
</body>
</html>
