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
        
        .totalCost {
            margin-top: 32px;
        }
        
        #totalCostlbl {
            text-transform: uppercase;
            font-weight: bold;
            color: #ff6f61;
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
                        <div class="card-body">
                            <div class="row">
                                <!-- Thông tin đơn hàng -->
                                <div class="col-lg-12">
                                    <h5>Thông Tin Đơn Hàng</h5>
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
                                        <label>Địa Chỉ Giao Hàng:</label>
                                        <span class="form-control-plaintext">${order.shipAddress}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Mã Giảm Giá:</label>
                                        <span class="form-control-plaintext">
                                            <c:if test="${not empty order.discountId}">
                                                ${discounts[order.discountId].discountDesc} - ${discounts[order.discountId].discountAmount}% - ${discounts[order.discountId].minimumAmount}₫
                                            </c:if>
                                            <c:if test="${empty order.discountId}">
                                                Không có
                                            </c:if>
                                        </span>
                                    </div>
                                    <div class="form-group">
                                        <label>Ngày Đặt Hàng:</label>
                                        <span class="form-control-plaintext"><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy" /></span>
                                    </div>
                                    <div class="form-group">
                                        <label>Trạng Thái:</label>
                                        <span class="form-control-plaintext">${order.status}</span>
                                    </div>
                                    <div class="form-group totalCost">
                                        <label>Tổng Tiền:</label>
                                        <span class="form-control-plaintext" id="totalCostlbl"><fmt:formatNumber value="${order.totalCost}" type="number" maxFractionDigits="0" groupingUsed="true" />₫</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
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
                            <c:if test="${order.status == 'Wait for confirmation'}">
                                <div class="d-flex justify-content-end mt-3">
                                    <a href="${pageContext.request.contextPath}/admin/order/confirm.htm?orderId=${order.orderId}" 
                                       class="btn btn-primary" onclick="return confirm('Bạn có chắc muốn xác nhận đơn hàng này?')">Xác Nhận Đơn Hàng</a>
                                    <a href="${pageContext.request.contextPath}/admin/order/cancel.htm?orderId=${order.orderId}" 
                                       class="btn btn-danger ml-2" onclick="return confirm('Bạn có chắc muốn hủy đơn hàng này?')">Hủy Đơn Hàng</a>
                                    <a href="${pageContext.request.contextPath}/admin/order.htm?status=confirm" class="btn btn-secondary ml-2">Quay Lại</a>
                                </div>
                            </c:if>
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
    </script>
</body>
</html>