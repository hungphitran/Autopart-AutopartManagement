<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html>
<html>
<head>
<title>Chi tiết đơn hàng</title>
<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style>
.container {
	display: flex;
	justify-content: space-between;
	margin-top:150px;
}

.order-details {
	position: fixed;
	width: 40%;
}

.product-list {
	width: 50%;
}

body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background-color: #f4f4f4;
	color: #333;
	margin: 0;
	padding: 0;
}

.order-details {
	margin: 10px;
	padding: 20px;
	background-color: white;
	border-radius: 8px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.order-details p {
	margin: 8px 0;
}

.order-details strong {
	display: inline-block;
	width: 150px;
	margin-left: 10%;
}

.product-list {
	list-style: none;
	padding: 0;
	margin-left:50%;
	margin-top:10px;
}

.product-item {
	background-color: white;
	border-radius: 8px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	margin-bottom: 10px;
	padding: 15px;
}

.product-item strong {
	display: inline-block;
	width: 120px;
	color: #007bff;
}
.btn {
	padding: 5px 5px;
	margin: 5px 0;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
	width: 100%;
}

.btn:hover{
	color: blue;
}

.nav-btn{
		background-color:  orange;
	color: white;
}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/mixins/header.jsp" />


	<div class="container">
		<div class="order-details">
			<p>
				<strong>Tên khách hàng:</strong> ${userName}
			</p>
			<p>
				<strong>Số điện thoại:</strong> ${user.phone }
			</p>
			<p>
				<strong>Địa chỉ:</strong> ${order.shipAddress }
			</p>
			<p>
				<strong>Tổng:</strong> ${order.totalCost }
			</p>
			<p>
				<strong>Mã giảm giá:</strong> ${order.discountId }
			</p>
			<p>
				<strong>Ngày đặt hàng:</strong> ${order.orderDate }
			</p>
			<p>
				<strong>Trạng thái:</strong> ${order.status}
			</p>
		</div>

		<ul class="product-list">
		<c:forEach items="${products}" var="p">
		
			<li class="product-item">
				<p>
					<strong>Tên sản phẩm:</strong> ${p.productName }
				</p>
				<p>
					<strong>Mã sản phẩm:</strong>${p.productId }
				</p>
				<p>
					<strong>Số lượng:</strong> ${p.amount}
				</p>
				<p>
					<strong>Đơn giá:</strong> ${p.unitPrice }
				</p>
				<p>
					<strong> <button class="btn nav-btn">Xem sản phẩm</button> </strong>
				</p>
			</li>
		</c:forEach>
		</ul>
	</div>
</body>
</html>
