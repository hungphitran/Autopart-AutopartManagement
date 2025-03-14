<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Phiếu Nhập</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">

    <style>
        .detail-table {
            margin-top: 20px;
        }
        .detail-table th, .detail-table td {
            vertical-align: middle;
        }
        .order-info-column {
            padding: 15px;
        }
        .form-group {
            margin-bottom: 15px;
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
                            <h6 class="m-0 font-weight-bold text-primary">Chi Tiết Phiếu Nhập</h6>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>

                            <div class="row">
                                <!-- Cột 1: Thông tin phiếu nhập -->
                                <div class="col-lg-6 order-info-column">
                                    <div class="form-group">
                                        <label>Mã Phiếu Nhập:</label>
                                        <p><strong>${importEntity.importId}</strong></p>
                                    </div>
                                    <div class="form-group">
                                        <label>Nhân Viên Nhập:</label>
                                        <p><strong>${employeeFullName} (SĐT: ${importEntity.employeePhone})</strong></p>
                                    </div>
                                    <div class="form-group">
                                        <label>Ngày Nhập:</label>
                                        <p><strong><fmt:formatDate value="${importEntity.importDate}" pattern="dd/MM/yyyy" /></strong></p>
                                    </div>
                                    <div class="form-group">
                                        <label>Tổng Chi Phí:</label>
                                        <p><strong><fmt:formatNumber value="${importEntity.importCost}" type="currency" currencySymbol="₫" /></strong></p>
                                    </div>
                                </div>
                            </div>

                            <!-- Bảng chi tiết sản phẩm -->
                            <div class="table-responsive detail-table">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Mã Sản Phẩm</th>
                                            <th>Tên Sản Phẩm</th>
                                            <th>Số Lượng</th>
                                            <th>Giá Nhập</th>
                                            <th>Thành Tiền</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${importEntity.importDetails}" var="detail" varStatus="loop">
                                            <c:set var="product" value="${productList.stream().filter(p -> p.productId == detail.id.productId).findFirst().orElse(null)}" />
                                            <tr>
                                                <td>${loop.count}</td>
                                                <td>${detail.id.productId}</td>
                                                <td>${product != null ? product.productName : 'Không xác định'}</td>
                                                <td>${detail.amount}</td>
                                                <td><fmt:formatNumber value="${detail.price}" type="currency" currencySymbol="₫" /></td>
                                                <td><fmt:formatNumber value="${detail.price * detail.amount}" type="currency" currencySymbol="₫" /></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>

                            <div class="d-flex justify-content-end mt-3">
                                <a href="${pageContext.request.contextPath}/admin/product/import.htm" class="btn btn-secondary">Quay Lại</a>
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
</body>
</html>