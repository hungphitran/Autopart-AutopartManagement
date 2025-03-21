<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Đơn Hàng</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">

    <style>
        .selected-products {
            max-height: 350px;
            overflow-y: auto;
        }
        
        .selected-item {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 5px;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .selected-item span {
            flex-grow: 1;
        }
        
        #totalCostlbl {
            text-transform: uppercase;
            font-weight: bold;
            color: #ff6f61;
        }
        
        .order-info-column {
            padding: 15px;
        }
        
        .order-info-column label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .order-info-column span {
            display: block;
            padding: 5px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }
    </style>
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
                            <h6 class="m-0 font-weight-bold text-primary">Chi Tiết Đơn Hàng</h6>
                        </div>
                        <div class="card-body" id="submit-form">
                            <h5>Thông Tin Đơn Hàng</h5>
                            <div class="row">
                                <!-- Thông tin đơn hàng chia thành hai cột -->
                                <div class="col-lg-6 order-info-column">
                                    
                                    <div class="form-group">
                                        <label>Mã Đơn Hàng:</label>
                                        <span class="form-control-plaintext">${order.orderId}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Họ Tên Khách Hàng:</label>
                                        <span class="form-control-plaintext">${userName}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Số Điện Thoại Khách Hàng:</label>
                                        <span class="form-control-plaintext">${order.userPhone}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Ngày Đặt Hàng:</label>
                                        <span class="form-control-plaintext"><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy" /></span>
                                    </div>
                                </div>
                                <div class="col-lg-6 order-info-column">
                                    <div class="form-group">
                                        <label>Địa Chỉ Giao Hàng:</label>
                                        <span class="form-control-plaintext">${order.shipAddress}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Mã Giảm Giá:</label>
                                        <span class="form-control-plaintext">
                                            <c:if test="${not empty order.discountId}">
                                             	<c:forEach items="${discounts}" var="discount">
                                             		<c:if test="${discount.discountId eq order.discountId}">
                                             			${discount.discountDesc} - ${discount.discountAmount}% - ${discount.minimumAmount}₫
                                             		</c:if>
                                             	</c:forEach>
                                            </c:if>
                                            <c:if test="${empty order.discountId}">
                                                Không có
                                            </c:if>
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label>Trạng Thái:</label>
                                        <c:choose>
                                        	<c:when test="${order.status == 'Pending'}">
                                        		<span class="form-control-plaintext">Chờ xác nhận</span>
                                        	</c:when>
                                        	<c:when test="${order.status == 'Processing'}">
                                        		<span class="form-control-plaintext">Chờ đóng gói</span>
                                        	</c:when>
                                        	<c:when test="${order.status == 'Shipping'}">
                                        		<span class="form-control-plaintext">Đang giao hàng</span>
                                        	</c:when>
                                        	<c:when test="${order.status == 'Cancelled'}">
                                        		<span class="form-control-plaintext">Đã hủy</span>
                                        	</c:when>
                                        	<c:otherwise>
                                        		<span class="form-control-plaintext">Đã hoàn thành</span>
                                        	</c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="form-group totalCost">
                                        <label>Tổng Tiền:</label>
                                        <span class="form-control-plaintext" id="totalCostlbl"><fmt:formatNumber value="${order.totalCost}" type="number" maxFractionDigits="0" groupingUsed="true" />₫</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row mt-4">
                                <!-- Danh sách chi tiết đơn hàng -->
                                <div class="col-lg-12">
                                    <h5>Chi Tiết Đơn Hàng</h5>
                                    <div class="selected-products" id="selectedProducts">
                                        <c:forEach items="${order.orderDetails}" var="detail" varStatus="loop">
                                            <div class="selected-item">
                                                <div>
                                                    <div>${detail.productName}</div>
                                                    <div>Giá: <fmt:formatNumber value="${detail.unitPrice}" type="number" maxFractionDigits="0" groupingUsed="true" />₫</div>
                                                    <div>Số Lượng: ${detail.amount}</div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${order.status == 'Pending'}">
                                <div class="d-flex justify-content-end mt-3">
								    <button type="button" class="btn btn-primary confirm-btn" data-order-id="${order.orderId}" data-toggle="modal" data-target="#ConfirmModal">Xác Nhận Đơn Hàng</button>
								    <button type="button" class="btn btn-danger ml-2 cancelled-btn" data-order-id="${order.orderId}" data-toggle="modal" data-target="#CancelledModal">Hủy Đơn Hàng</button>
								    <a href="${pageContext.request.contextPath}/admin/order.htm?status=confirm" class="btn btn-secondary ml-2">Quay Lại</a>
								</div>
                            </c:if>
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
					
					<!-- Confirmation form  -->
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
								<p>Xác nhận đơn hàng?</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
               					<button type="button" class="btn btn-primary confirm-order-btn" data-order-id="${order.orderId}" data-order-status="${order.status}">Xác Nhận</button>
							</div>
						</div>
						</div>
					</div>
					
					<!-- Cancelled form  -->
					<div class="modal fade" id="CancelledModal" tabindex="-1" role="dialog" aria-labelledby="CancelledModal" aria-hidden="true">
						<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="CancelledModal">Hủy đơn hàng</h5>
								<button type="button" class="close" data-dismiss="modal" aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<p>Bạn có muốn hủy đơn hàng?</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
               					<button type="button" class="btn btn-primary cancel-order-btn" data-order-id="${order.orderId}" data-order-status="${order.status}">Xác Nhận</button>
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
    
    <script>
        function formatCurrency(amount) {
            var number = Number(amount);
            if (isNaN(number)) {
                return "0₫";
            }
            var formattedNumber = number.toLocaleString('vi-VN', {
                minimumFractionDigits: 0, 
                maximumFractionDigits: 0 
            });
            formattedNumber = formattedNumber.replace(/\./g, ',');
            return formattedNumber + '₫';
        }
        
        $(document).ready(function() {
            $("#submit-form").on('click', '.confirm-btn', function() {
                let orderId = $(this).data('order-id');
                // Mở modal khi nhấp vào nút "Xác Nhận Đơn Hàng"
                $('#ConfirmModal').modal('show');
                // Lưu orderId vào data của nút confirm-order-btn trong modal để sử dụng sau
                $('#ConfirmModal .confirm-order-btn').data('order-id', orderId);
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
                    	window.location.href = '${pageContext.request.contextPath}/admin/order.htm?status=processing';
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                        $('#ConfirmModal .modal-body').html('<p class="text-danger">Có lỗi xảy ra khi xác nhận đơn hàng.</p>');
                        $('#ConfirmModal .modal-footer').html('<button type="button" class="btn btn-danger" data-dismiss="modal">Đóng</button>');
                    }
                });
            });
            
            $("#submit-form").on('click', '.cancelled-btn', function() {
                let orderId = $(this).data('order-id');
                // Mở modal khi nhấp vào nút "Xác Nhận Đơn Hàng"
                $('#CancelledModal').modal('show');
                // Lưu orderId vào data của nút confirm-order-btn trong modal để sử dụng sau
                $('#CancelledModal .cancel-order-btn').data('order-id', orderId);
            });

            // Xử lý khi nhấp vào nút "Xác Nhận" trong modal
            $('#CancelledModal').on('click', '.cancel-order-btn', function() {
                let orderId = $(this).data('order-id');

                $.ajax({
                    url: '${pageContext.request.contextPath}/admin/order/changeStatus.htm',
                    type: 'POST',
                    data: { 
                    	orderId: orderId,
                    	status: "Cancelled"
                    },
                    success: function(response) {
                    	$('#CancelledModal').modal('hide');
                    	window.location.href = '${pageContext.request.contextPath}/admin/order.htm?status=history';
                    },
                    error: function(xhr, status, error) {
                        console.error('Error:', error);
                        $('#ConfirmModal .modal-body').html('<p class="text-danger">Có lỗi xảy ra khi hủy đơn hàng.</p>');
                        $('#ConfirmModal .modal-footer').html('<button type="button" class="btn btn-danger" data-dismiss="modal">Đóng</button>');
                    }
                });
            });
        });
    </script>
</body>
</html>