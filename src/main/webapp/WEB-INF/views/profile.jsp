
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
  <div class="sidebar">
	<form:form method="POST" action="/autopart/account/edit.htm"
			 modelAttribute="customer" data-oc-toggle="ajax">
			<h1>Tài khoản</h1>

			<p>
				<form:input type="hidden" path="cartId"/>
			</p>
			<p class="input-field" >
								<label>Họ và tên</label>
				<form:input type="text" path="fullName" disabled="true"/>
			</p>

			<p class="input-field">
							<label>Số điện thoại</label>
			
				<form:input type="phone" path="phone" disabled="true"/>
			</p>
			<p class="input-field">
							<label>Địa chỉ</label>
			
				<form:input type="text" path="address" disabled="true" />
			</p>
			<p>
							<label>Trạng thái</label>
							<c:if test="${customer.status == 'Active'}"><label style="color:green;"> Đang hoạt động</label></c:if>
							<c:if test="${status == 'Inactive'}"><label>Ngưng hoạt động</label></c:if>
				<form:input path="status" type="hidden"/>
			</p>
			
			<div class="buttons">	
				<button type="submit" class="button edit-btn" id="edit-btn">Chỉnh sửa</button>
			</div>
		</form:form>

    <div class="menu">
      <a  class="order choosed">Thông tin đơn hàng</a>
      <a  class="setting">Đổi mật khẩu</a>
      <a  href ="/autopart/product/cart.htm" >Giỏ hàng</a>
      <a href= "/autopart/">Trang chủ</a>
      <a href = "/autopart/logout.htm" ><button class="button delete-btn" >Đăng xuất</button></a>
      
    </div>
  </div>

  <div class="content">
    <div class="orders">
      <h3>Lịch sử đơn hàng</h3>
      <div class="order-item">
        <p><strong>Order ID:</strong> 1001</p>
        <p><strong>Date:</strong> 2024-02-01</p>
        <p><strong>Total:</strong> $150.00</p>
        <p><strong>Status:</strong> Shipped</p>
      </div>
      <div class="order-item">
        <p><strong>Order ID:</strong> 1002</p>
        <p><strong>Date:</strong> 2024-02-10</p>
        <p><strong>Total:</strong> $85.00</p>
        <p><strong>Status:</strong> Processing</p>
      </div>
      <div class="order-item">
        <p><strong>Order ID:</strong> 1003</p>
        <p><strong>Date:</strong> 2024-02-15</p>
        <p><strong>Total:</strong> $230.00</p>
        <p><strong>Status:</strong> Delivered</p>
      </div>
    </div>
    <form action="/autopart/account/changepass.htm" method="post"  class="change-password hide">
      <h3>Đổi mật khẩu</h3>
      <input type="password" name="pass" placeholder="Mật khẩu hiện tại">
      <input type="password" name="newpass" placeholder="Mật khẩu mới">
      <input type="password" placeholder="Xác nhận mật khẩu mới">
      <button>Cập nhật</button>
    </form>
  </div>
</body>

<script type="text/javascript" src="<c:url value="/resources/js/profile.js" />"></script>
</html>