<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Login</title>
	
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
	
	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
</head>
<body>
<!-- Success message -->
	<c:if test="${not empty successMessage}">
		<div class="alert alert-success mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${successMessage}</div>
	</c:if>
	
	<div class="wrapper">
		<form id="form-login" action="/autopart/login.htm" method="post" data-oc-toggle="ajax">
			<h2>Đăng nhập</h2>

			<div class="input-field">
				<input type="text" name="phone" required>
				<label>Nhập số email</label>
			</div>

			<div class="input-field">
				<input type="password" name="password" required>
				<label>Nhập mật khẩu</label>
			</div>
			
			<span style="color:red;"> ${message} </span>

			<div class="forget">
				<label for="remember">
					<input type="checkbox" id="remember">
					<p>Remember me</p>
				</label>
				<a href="/autopart/forgot-pasword.htm">Quên mật khẩu?</a>
			</div>

			<button type="submit">Đăng nhập</button>
			
			<div class="register">
				<p>Chưa có tài khoản? <a href="/autopart/register.htm">Đăng ký</a></p>
			</div>
		</form>
	</div>
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

</script>

</html>