<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Nhập mã OTP</title>
	
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
	
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
</head>
<body>
	<div class="wrapper">
		<form id="form-enter-otp" action="/autopart/otpVerify.htm" method="post" data-oc-toggle="ajax">
			<h2>Nhập mã OTP</h2>

			<div class="input-field">
				<input type="text" name="otp" required maxlength="6">
				<label>Nhập mã OTP</label>
			</div>
			
			<span style="color:red;"> ${message} </span>

			<button type="submit">Xác nhận</button>
			
			<div class="register">
				<p>Chưa nhận được mã? <a href="/autopart/forgot-password.htm">Gửi lại</a></p>
			</div>
		</form>
	</div>
</body>
</html>