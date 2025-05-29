<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Thống kê</title>

<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link
	href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/admincss/base.css" />"
	rel="stylesheet">
</head>
<body>
<%-- Alert handling (equivalent to include ../mixins/alert.pug) --%>
<c:if test="${not empty message}">
	<div id="alertContainer" class="alert alert-info">${message}</div>
</c:if>



<div id="wrapper">
	<jsp:include page="/WEB-INF/mixins/adminnav.jsp" />

	<div id="content-wrapper" class="d-flex flex-column">
		<div id="content">
			<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />
			<div class="container-fluid" id="container-wrapper">

				<%-- Main content (equivalent to block main) --%>				<div
					class="d-sm-flex align-items-center justify-content-between mb-4">
					<h1 class="h3 mb-0 text-gray-800">Báo cáo sản phẩm</h1>
					<div class="btn-group">
                        <a class="btn btn-secondary mr-2" href="/autopart/admin/statistic.htm">
                            <i class="fas fa-chart-bar mr-1"></i>
                            Quay lại thống kê
                        </a>
                        <button class="btn btn-success" onclick="exportProductExcel()">
                            <i class="fas fa-file-excel mr-1"></i>
                            Xuất Excel
                        </button>
                    </div>
				</div>

				<div class="card mb-4">
					<div
						class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
						<h6 class="m-0 font-weight-bold">Chọn khoảng thời gian</h6>
					</div>
					<div class="card-body">
						<form class="form-inline" action="${prefixAdmin}/admin/product-report"
							method="get">
							<div class="form-group mx-sm-3 mb-2">
								<label for="fromDate" class="mr-2">Từ ngày:</label> <input type="date"
									class="form-control" id="fromDate" name="fromDate"
									value="${fromDate}">
							</div>
							<div class="form-group mx-sm-3 mb-2">
								<label for="toDate" class="mr-2">Đến ngày:</label> <input
									type="date" class="form-control" id="toDate" name="toDate"
									value="${toDate}">
							</div>							<button type="submit" class="btn btn-primary mb-2">Lọc
								dữ liệu</button>
						</form>
					</div>
				</div>

				<div class="card mb-4">
					<div class="card-header py-3">
						<h6 class="m-0 font-weight-bold text-primary">Danh sách sản
							phẩm</h6>
					</div>
					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-bordered">
								<thead class="thead-light">
									<tr>
										<th>Mã sản phẩm</th>
										<th>Tên sản phẩm</th>
										<th>Giá bán</th>
										<th>Số lượng tồn kho</th>
										<th>Số lượng đã bán</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${productSales}">
										<tr>
											<td>${item.key.productId}</td>
											<td>${item.key.productName}</td>
											<td>
												<%-- Equivalent to +formatCurrency(item.key.salePrice) --%> <fmt:formatNumber
													value="${item.key.salePrice}" type="currency"
													currencyCode="VND" />
											</td>
											<td>${item.key.stock}</td>
											<td>${item.value}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
	<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
	
	<script>
	    function exportProductExcel() {
	        const fromDate = document.getElementById('fromDate').value;
	        const toDate = document.getElementById('toDate').value;
	        
	        if (!fromDate || !toDate) {
	            alert('Vui lòng chọn khoảng thời gian trước khi xuất báo cáo');
	            return;
	        }
	        
	        const url = `/autopart/admin/export-excel.htm?reportType=product&fromDate=${fromDate}&toDate=${toDate}`;
	        
	        // Create a temporary link to trigger download
	        const link = document.createElement('a');
	        link.href = url;
	        link.download = '';
	        document.body.appendChild(link);
	        link.click();
	        document.body.removeChild(link);
	    }
	</script>
</html>