<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Thông tin tài khoản</title>

<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">	
	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/admincss/profile.css" />"
	rel="stylesheet">
</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/mixins/adminnav.jsp" />

		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />
				<div class="container-fluid" id="container-wrapper">
					<div class="col-lg-12">
						<div class="card mb-4">
							<div
								class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
							</div>
							
							
							<div class="form-actions">
								<button class="btn btn-save" id="edit" onclick="editAccount()"><i class="fa-solid fa-pen-to-square"></i> Chỉnh
									sửa thông tin</button>
								<button class="btn btn-save" id="back" onclick="showInfoForm()">
								<i class="fa-solid fa-left-long"></i> Quay lại</button>
								<button class="btn btn-change-password" id="change-pass"
									onclick="showPassForm()"><i class="fa-solid fa-unlock-keyhole"></i> Đổi mật khẩu</button>
							</div>

							<form:form action="/autopart/admin/profile/edit.htm"
								method="post" modelAttribute="employee" class="user-form">
								<div class="form-group">
									<form:label path="fullName">Họ và tên :</form:label>
									<form:input path="fullName" name="fullName" value="${fullName}"
										class="form-input" readonly="true" />
								</div>
								<div class="form-group">
									<form:label path="gender"> Giới tính :</form:label>
									<form:select path="gender" id="gender" disabled="true">
										<form:option value="male">Nam</form:option>
										<form:option value="female">Nữ</form:option>
									</form:select>
								</div>
								<div class="form-group">
									<form:label path="phone">Số điện thoại :</form:label>
									<form:input path="phone" name="phone" value="${phone}"
										class="form-input" readonly="true" />
								</div>
								<div class="form-group">
									<form:label path="email">Email :</form:label>
									<form:input path="email" name="email" value="${email}"
										class="form-input" readonly="true" />
								</div>
								<div class="form-group">
									<form:label path="birthDate">Ngày sinh :</form:label>
									<form:input type="date" path="birthDate" name="birthDate"
										value="${birthDate}" class="form-input" readonly="true" />
								</div>
								<div class="form-group">
									<form:label path="address">Địa chỉ :</form:label>
									<form:input path="address" name="address" value="${address}"
										class="form-input" readonly="true" />
								</div>
								<div class="form-group">
									<form:label path="educationLevel"> Trình độ giáo dục :</form:label>
									<form:input path="educationLevel" name="educationLevel"
										value="${educationLevel}" class="form-input" readonly="true" />
								</div>
							</form:form>

							<form class="pass-form" id="passForm" method="post" action="/autopart/admin/profile/changepass.htm">
								<h2>Đổi mật khẩu</h2>
								<div class="pass-inputs">
									<input type="password" id="pass" name="pass"
										placeholder="Nhập mật khẩu hiện tại" required><br> <input
										type="password" id="newpass" placeholder="Nhập mật khẩu mới" name="newpass"
										oninput="validateInputs()" required><br> <input
										type="password" id="confirmpass" name="confirmpass"
										placeholder="Nhập lại mật khẩu mới" oninput="validateInputs()" required>
									<p id="warning" style="color: red; display: none;"> Hai ô
										nhập không khớp!</p>
								</div>
								<div class="pass-actions">
									<button type="submit" class="btn btn-save"
										onclick="submitPasswordChange()">Xác nhận</button>
								</div>
							</form>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script src="<c:url value="/resources/js/adminjs/profile.js" />"></script>
	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
	<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
	