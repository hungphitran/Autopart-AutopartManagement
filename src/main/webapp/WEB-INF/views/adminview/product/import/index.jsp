<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Lịch sử phiếu nhập</title>

	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
</head>
<body id="page-top">
	<div id="wrapper">
	    <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
	    
	    <div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
	    		<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

				<div class="container-fluid" id="container-wrapper">
					<div class="col-lg-12">
						<div class="card mb-4">
							<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
								<h6 class="m-0 font-weight-bold text-primary">Lịch Sử Phiếu Nhập</h6>
								<a href="${pageContext.request.contextPath}/admin/product/import/add.htm" class="btn btn-primary">+ Tạo phiếu nhập</a>
							</div>
						  <div class="table-responsive p-3">
							<table class="table align-items-center table-flush" id="dataTable">
								<thead class="thead-light">
									<tr>
										<th>Mã Phiếu Nhập</th>
										<th>Nhân Viên Phụ Trách</th>
										<th>Ngày Nhập Hàng</th>
										<th>Tổng Tiền</th>
										<th>Hoạt Động</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${imports}" var="imp">
										<tr class="product-item">
											<td class="align-middle">${imp.importId}</td>
											<td class="align-middle">${imp.employeeName}</td>
											<td class="align-middle"><fmt:formatDate value="${imp.importDate}" pattern="dd/MM/yyyy" /></td>
											<td class="align-middle"><fmt:formatNumber value="${imp.importCost}" type="number" maxFractionDigits="0" groupingUsed="true" />₫</td>
											<td class="align-middle">
												<a href="${pageContext.request.contextPath}/admin/product/import/detail.htm?importId=${imp.importId}" class="btn btn-sm btn-dark">Chi Tiết</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
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
	
	<script src="<c:url value="/resources/vendor/datatables/jquery.dataTables.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.min.js" />"></script>

	<script>
		$(document).ready(function () {
			$('#dataTable').DataTable({
				"language": {
					"search": "Tìm kiếm",
					"lengthMenu": "Hiển thị _MENU_ dòng",
					"info": "Hiển thị từ _START_ đến _END_ trên tổng số _TOTAL_ dòng",
					"paginate": {
						"previous": "Trước", 
						"next": "Tiếp" 
					}
				}
			}); 
		});
  	</script>
</body>
</html>
