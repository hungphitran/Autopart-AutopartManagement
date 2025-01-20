<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
	
	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<link href="<c:url value="/resources/css/login.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
</head>
<body>

	<div class="header">
			<a href="/autopart">
				<img src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="Home" width="100" height="100">
			</a>
	</div>


	<div id="container">
		<div id="content" class="col">
				<div class="col-sm-6">
					<div class="card mb-3">
						<div class="card-body">
							<form id="form-login" action="/autopart/login.htm" method="post" data-oc-toggle="ajax">
								<h1>Login</h1>
								<p style ="color:red;">${message }</p>
								<div class="mb-3">
									<label for="input-email" class="col-form-label">E-Mail Address</label>
									<input type="text" name="email" value="" placeholder="E-Mail Address" id="input-email" class="form-control">
								</div>
								<div class="mb-3">
									<label for="input-password" class="col-form-label">Password</label>
									<input type="password" name="password" value="" placeholder="Password" id="input-password" class="form-control">
									<a href="forgot.html">Forgotten Password</a>
								</div>
								<button type="submit" class="btn btn-danger">Login</button>
								<a href="/autopart/register.htm" class="btn btn-primary">Register</a>	
							</form>
						</div>
					</div>
				</div>
		</div>
	</div>

</body>
</html>