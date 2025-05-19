<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Quên mật khẩu</title>
	
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
		<form id="form-forgot-password" action="/autopart/forgot-password.htm" method="post" data-oc-toggle="ajax">
			<h2>Quên mật khẩu</h2>

			<div class="input-field">
				<input type="text" name="email" required>
				<label>Nhập email</label>
			</div>
			
			<span style="color:red;"> ${message} </span>

			<button type="submit">Gửi yêu cầu</button>
			
			<div class="register">
				<p>Đã có tài khoản? <a href="/autopart/login.htm">Đăng nhập</a></p>
			</div>
		</form>
	</div>
</body>
</html>