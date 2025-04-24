
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Thông tin tài khoản</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/resources/css/profile.css" />"
	rel="stylesheet">

</head>
<body>
	<!-- Success message -->
	<c:if test="${not empty successMessage}">
		<div class="alert alert-success mt-3" role="alert"
			style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${successMessage}</div>
	</c:if>

	<!-- Error message -->
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger mt-3" role="alert"
			style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${errorMessage}</div>
	</c:if>
	<div class="sidebar">
		<form:form method="POST" action="/autopart/account/edit.htm"
			modelAttribute="customer" data-oc-toggle="ajax">
			<img id="user_icon"
				src="https://i.pinimg.com/474x/89/90/48/899048ab0cc455154006fdb9676964b3.jpg"
				width="100px" height="100px" alt="">

			<p>
				<form:input type="hidden" path="cartId" required="required" />
			</p>
			<p class="input-field">
				<label>Họ và tên</label>
				<form:input type="text" path="fullName" disabled="true"
					required="required" />
			</p>
			<p class="input-field">
				<label>Email</label>

				<form:input type="email" path="email" disabled="true"
					required="required" />
			</p>
			<p class="input-field">
				<label>Số điện thoại</label>

				<form:input type="phone" path="phone" disabled="true"
					required="required" />
			</p>
			<p class="input-field">
				<label>Địa chỉ</label>

				<form:input type="text" path="address" disabled="true"
					required="required" />
			</p>
			<p>
				<label>Trạng thái</label>
				<c:if test="${customer.status == 'Active'}">
					<label style="color: green;"> Đang hoạt động</label>
				</c:if>
				<c:if test="${status == 'Inactive'}">
					<label>Ngưng hoạt động</label>
				</c:if>
				<form:input path="status" type="hidden" required="required" />
			</p>

			<div class="buttons">
				<button type="submit" class="button edit-btn" id="edit-btn">Chỉnh
					sửa</button>
			</div>
		</form:form>

		<div class="menu">
			<a class="order choosed"><i class="fa-solid fa-box-open"></i>
				Thông tin đơn hàng</a> <a class="setting"><i
				class="fa-solid fa-lock"></i> Đổi mật khẩu</a> <a href="/autopart/"><i
				class="fa-solid fa-house"></i> Trang chủ</a> <a
				href="/autopart/logout.htm" class="delete-btn"><i
				class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a>

		</div>
	</div>

	<div class="content">
		<div class="orders">
			<header class="header">
				<h1>Lịch sử đơn hàng</h1>
			</header>
			<c:if test="${orders.size()==0}">
				<h2>Bạn chưa có đơn hàng nào</h2>
			</c:if>
			<c:forEach items="${orders}" var="order">
				<div class="order-item" data-order="${order}">
					<p>
						<strong>Mã đơn hàng: </strong>${order.orderId}</p>
					<p>
						<strong>Ngày: </strong> ${order.orderDate}
					</p>
					<p>
						<strong>Tổng tiền: </strong>${order.totalCost}</p>
					<p>
						<strong>Trạng thái: </strong>
						<c:choose>
							<c:when test="${order.status == 'Pending'}">Chờ xác nhận</c:when>
							<c:when test="${order.status == 'Shipping'}">Đang giao hàng</c:when>
							<c:when test="${order.status == 'Completed'}">Đã hoàn thành</c:when>
							<c:when test="${order.status == 'Processing'}">Đang xử lý</c:when>
						</c:choose>
					</p>
					<a href="/autopart/order.htm?orderId=${order.orderId}"
						class="button nav-btn">Xem chi tiết đơn hàng</a>

				</div>
			</c:forEach>

		</div>

		<form action="/autopart/account/changepass.htm" method="post"
			class="change-password hide">
			<h3>Đổi mật khẩu</h3>
			<div class="form-item">
				<input type="password" name="pass" placeholder="Mật khẩu hiện tại"
					required="required"> <i class="fa-solid fa-eye"></i> <i
					class="fa-solid fa-eye-slash"></i>
			</div>
			<div class="form-item">
				<input type="password" name="newpass" placeholder="Mật khẩu mới"
					required="required"> <i class="fa-solid fa-eye"></i> <i
					class="fa-solid fa-eye-slash"></i>
			</div>
			<div class="form-item">
				<input type="password" name="confirmpass" placeholder="Xác nhận mật khẩu mới"
					required="required">
			</div>
			<button>Cập nhật</button>
		</form>
	</div>

</body>

<script type="text/javascript"
	src="<c:url value="/resources/js/profile.js" />"></script>
</html>