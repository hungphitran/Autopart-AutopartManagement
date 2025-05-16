<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Danh sách đơn hàng</title>

	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
</head>
<body id="page-top">
<!-- Success message -->
	<c:if test="${not empty successMessage}">
		<div class="alert alert-success mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${successMessage}</div>
	</c:if>

	<!-- Error message -->
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${errorMessage}</div>
	</c:if>
	<div id="wrapper">
	    <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
	    
	    <div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
	    		<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

				<div class="container-fluid" id="container-wrapper">
					<div class="col-lg-12">
						<div class="card mb-4">
							<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
								<h6 class="m-0 font-weight-bold text-primary">Danh Sách Đơn Hàng</h6>
							</div>
						  <div class="table-responsive p-3">
							<table class="table align-items-center table-flush" id="dataTable">
								<thead class="thead-light">
									<tr>
										<th>Mã Đơn Hàng</th>
										<th>Email KH</th>
										<th>Địa Chỉ Giao Hàng</th>
										<th>Ngày Đặt Hàng</th>
										<th>Tổng Tiền</th>
										<th>Hoạt Động</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${orders}" var="order">
										<tr class="product-item">
											<td class="align-middle">${order.orderId}</td>
											<td class="align-middle">${order.userEmail}</td>
											<td class="align-middle">${order.shipAddress}</td>
											<td class="align-middle"><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy" /></td>
											<td class="align-middle"><fmt:formatNumber value="${order.totalCost}" type="number" maxFractionDigits="0" groupingUsed="true" />₫</td>
											<td class="align-middle">
												<button type="button" class="btn btn-primary confirm-btn" data-order-id="${order.orderId}" data-order-status="${order.status}" data-toggle="modal" data-target="#ConfirmModal">Giao Hàng</button>
												<a href="${pageContext.request.contextPath}/admin/order/detail.htm?orderId=${order.orderId}" class="btn btn-sm btn-dark">Chi Tiết</a>
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
					
					<!-- Modal Logout -->
					<div class="modal fade" id="ConfirmModal" tabindex="-1" role="dialog" aria-labelledby="ConfirmModal" aria-hidden="true">
						<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="ConfirmModal">Xác nhận</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<p>Xác nhận giao hàng?</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
               					<button type="button" class="btn btn-primary confirm-order-btn" data-order-id="${order.orderId}" data-order-status="${order.status}" >Xác Nhận</button>
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
			
			$("#dataTable").on('click', '.confirm-btn', function() {
                let orderId = $(this).data('order-id');
                let orderStatus = $(this).data('order-status');
                
                $('#ConfirmModal').modal('show');
                $('#ConfirmModal .confirm-order-btn').data('order-id', orderId);
                $('#ConfirmModal .confirm-order-btn').data('order-status', orderStatus);
            });

            // Xử lý khi nhấp vào nút "Xác Nhận" trong modal
            $('#ConfirmModal').on('click', '.confirm-order-btn', function() {
                let orderId = $(this).data('order-id');
                let orderStatus = $(this).data('order-status');

                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/order/changeStatus.htm',
                    type: 'POST',
                    data: { 
                    	orderId: orderId,
                    	status: orderStatus
                    },
                    success: function(response) {
                    	$('#ConfirmModal').modal('hide');
                    	window.location.href = '${pageContext.request.contextPath}/admin/order.htm?status=delivery';
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                        $('#ConfirmModal .modal-body').html('<p class="text-danger">Có lỗi xảy ra khi xác nhận đơn hàng.</p>');
                        $('#ConfirmModal .modal-footer').html('<button type="button" class="btn btn-danger" data-dismiss="modal">Đóng</button>');
                    }
                });
            });
		});
  	</script>
</body>
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    // Get success and error message elements
    const successMessage = document.querySelector('.alert-success');
    const errorMessage = document.querySelector('.alert-danger');
    
    // If success message exists, hide it after 3 seconds
    if (successMessage) {
        setTimeout(function() {
            successMessage.style.transition = 'opacity 0.5s';
            successMessage.style.opacity = '0';
            setTimeout(function() {
                successMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
    
    // If error message exists, hide it after 3 seconds
    if (errorMessage) {
        setTimeout(function() {
            errorMessage.style.transition = 'opacity 0.5s';
            errorMessage.style.opacity = '0';
            setTimeout(function() {
                errorMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
});
</script>
</html>
