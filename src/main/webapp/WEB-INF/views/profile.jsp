<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thông tin tài khoản</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="<c:url value='/resources/css/profile.css' />" rel="stylesheet">
    <style type="text/css">:root {
    --main-color: #ff6f61;
    --main-color-hover: #fb5748;
    --text-color: #333;
    --light-bg: #f8f9fa;
    --border-color: #eaeaea;
    --shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    --transition: all 0.3s ease;
    --accent-green: #4CAF50;
    --accent-red: #f44336;
    --accent-orange: #FF9800;
}

body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background-color: #f0f0f0;
	display: flex;
	height: 100vh;
	margin: 0;
}

.sidebar {
	background-color: #fff;
	width: 350px;
	padding: 20px;
	box-shadow: var(--shadow);
	display: flex;
	flex-direction: column;
	align-items: center;
	border-right: 2px solid var(--border-color);
}

.content {
	flex-grow: 1;
	background-color: var(--light-bg);
	padding: 20px;
	overflow-y: auto;
}

h2 {
	margin: 10px 0;
	font-size: 24px;
}

#user_icon {
    border-radius: 50%;
	margin-left: 0;
	display: block;
	margin-left: auto;
	margin-right: auto;
}

p {
	margin: 5px 0;
}

p label {
	display: block;
}
p input{
	width: 100%;
	border: 0px;
	border-bottom: 2px solid var(--border-color);
	transition: var(--transition);
}
p input:focus{
	outline: none;
	border-bottom: 2px solid var(--main-color);
}

/* Specific width for inputs inside sidebar */
.sidebar p input {
	width: 250px;
}

.buttons {
	margin-top: 20px;
}

.button {
	cursor: pointer;
	width: 80%;
	margin-left: 10%;
	background-color: #f3f4f5;
	border: none;
	border-radius: 8px;
	font-size: 14px;
	transition: var(--transition);
}

.button:hover {
	background-color: #e5e7e9;
	transform: translateY(-2px);
}

.edit-btn {
	background-color: var(--main-color);
	color: white;
	font-weight: 500;
	box-shadow: 0 2px 5px rgba(255, 111, 97, 0.3);
}

.edit-btn:hover {
	background-color: var(--main-color-hover);
	box-shadow: 0 4px 8px rgba(255, 111, 97, 0.4);
}

.delete-btn {
	background-color: var(--accent-red);
	color: white;
	text-decoration: none;
	transition: var(--transition);
}

.delete-btn:hover {
	background-color: #e53935;
	box-shadow: 0 4px 8px rgba(244, 67, 54, 0.3);
}

.menu {
	margin-top: 30px;
	width: 100%;
}

.menu a {
	display: block;
	padding: 12px 15px;
	margin-bottom: 10px;
	text-decoration: none;
	color: var(--text-color);
	border-radius: 8px;
	transition: var(--transition);
	font-weight: 500;
}

.menu a:hover {
	background-color: rgba(0, 0, 0, 0.05);
	transform: translateX(5px);
}

.menu a.choosed {
	font-weight: 600;
	border-left: 4px solid var(--accent-green);
	padding-left: 20px;
	background-color: rgba(76, 175, 80, 0.1);
}

.menu a:nth-child(3) {
	color: var(--accent-orange);
}

.menu a:nth-child(3):hover {
	background-color: rgba(255, 152, 0, 0.1);
}

.orders {
	max-width: 90%;
	margin: auto;
	padding: 20px;
	border-radius: 12px;
	box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
	background-color: #fff;
}

.orders header {
	display: flex;
	align-items: center;
	margin-bottom: 25px;
	padding-bottom: 15px;
	border-bottom: 2px solid #f0f0f0;
	position: relative;
}

.orders header:after {
	content: '';
	position: absolute;
	bottom: -2px;
	left: 0;
	width: 80px;
	height: 2px;
	background-color: #ff6f61;
	border-radius: 2px;
}

.orders header * {
	justify-content: space-between;
}

.orders h1 {
	font-size: 28px;
	font-weight: 600;
	color: #333;
	margin: 0;
}

.orders h3 {
	font-size: 24px;
	margin-bottom: 20px;
	text-align: center;
}

.order-item {
	border: 1px solid #eaeaea;
	border-radius: 10px;
	padding: 20px;
	margin-bottom: 20px;
	background-color: #fff;
	transition: all 0.3s ease;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
	position: relative;
	overflow: hidden;
}

.order-item:hover {
	transform: translateY(-3px);
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
	border-color: #ff6f61;
}

.order-item:before {
	content: '';
	position: absolute;
	left: 0;
	top: 0;
	height: 100%;
	width: 4px;
	background-color: #ff6f61;
}

.order-item p {
	margin: 12px 20px;
	display: flex;
	align-items: center;
}

.order-item strong {
	display: inline-block;
	width: 120px;
	color: #555;
	font-weight: 600;
}

.order-item a {
	text-decoration: none;
	margin-top: 10px;
	display: inline-block;
}

.order-item .button.nav-btn {
	background-color: #ff6f61;
	color: white;
	padding: 10px 15px;
	border-radius: 6px;
	font-weight: 500;
	transition: all 0.3s ease;
	box-shadow: 0 2px 5px rgba(255, 111, 97, 0.3);
	margin-left: 20px;
	border: none;
}

.order-item .button.nav-btn:hover {
	background-color: #fb5748;
	transform: translateY(-2px);
	box-shadow: 0 4px 8px rgba(255, 111, 97, 0.4);
}

.change-password {
	max-width: 400px;
	margin: 20px auto;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 8px;
	background-color: #fff;
}

.change-password h3 {
	text-align: center;
	margin-bottom: 20px;
	border-bottom: 2px solid #f44336;
	font-weight: bold;
	font-size: 20px;
}
.change-password .form-item {
	position: relative;
	display: block;
    margin-bottom: 25px; /* Provide space for error messages */
}

.change-password .form-item i {
	position: absolute;
	right: 10px;
	top: 30%;
	cursor: pointer;
}

.change-password input {
	width: 100%;
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 5px;
}

.change-password button {
	width: 100%;
	padding: 10px;
	background-color: #4CAF50;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	font-size: 16px;
}

.modal.active {
	display: block; /* Assuming it should be visible by default */
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
}
.modal{
	display:none;
}

.modal-content {
	background-color: #fefefe;
	margin: 15% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
}

.close {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

close:hover, .close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}

input[type="email"][readonly] {
	background-color: #f5f5f5;
	cursor: not-allowed;
}

.hide {
	display: none;
}

.input-field {
	position: relative;
	margin-bottom: 15px;
}

.error-message {
	display: block; /* Always display, visibility controlled by content */
	color: #f44336;
	font-size: 12px;
	margin-top: 5px;
    visibility: visible;
    height: auto;
    opacity: 1;
}

.input-field.error input {
	border-bottom: 2px solid #f44336;
}

.input-field.error .error-message {
	display: block;
}

.error input {
	border-bottom: 2px solid #f44336 !important;
}

.form-item.error input {
	border: 1px solid #f44336;
}

.change-password .form-item .error-message {
	position: absolute;
	bottom: -20px;
	margin-bottom: 10px;
}

.message.info {
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 1000;
    max-width: 400px;
}

.alert {
    padding: 15px 20px;
    border-radius: 10px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    margin-bottom: 15px;
    animation: slide-in 0.4s ease-out;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: 500;
}

.alert-success {
    background-color: #d4edda;
    color: #155724;
    border-left: 4px solid #4CAF50;
}

.alert-danger {
    background-color: #f8d7da;
    color: #721c24;
    border-left: 4px solid #f44336;
}

[close-alert] {
    cursor: pointer;
    font-weight: bold;
    margin-left: 15px;
    font-size: 18px;
    opacity: 0.7;
    transition: var(--transition);
}

[close-alert]:hover {
    opacity: 1;
    transform: scale(1.1);
}

@keyframes slide-in {
    from {
        transform: translateX(100%);
        opacity: 0;
    }
    to {
        transform: translateX(0);
        opacity: 1;
    }
}

@keyframes slide-out {
    from {
        transform: translateX(0);
        opacity: 1;
    }
    to {
        transform: translateX(100%);
        opacity: 0;
    }
}

.alert-hidden {
    animation: slide-out 0.3s forwards;
}

/* Existing style for message success and error inside the form */
.message-success {
    color: #155724;
    background-color: #d4edda;
    border-radius: 5px;
    padding: 10px;
    margin: 10px 0;
    text-align: center;
}

.message-error {
    color: #721c24;
    background-color: #f8d7da;
    border-radius: 5px;
    padding: 10px;
    margin: 10px 0;
    text-align: center;
}</style>
</head>
<body>
    <!-- Alert Container -->
    <div id="alertContainer"></div>

    <!-- Success Alert -->
    <c:if test="${not empty successMessage}">
        <div class="message info">
            <div class="alert alert-success" data-time="3000" show-alert role="alert">
                ${successMessage} <span close-alert>X</span>
            </div>
        </div>
    </c:if>

    <!-- Error Alert -->
    <c:if test="${not empty errorMessage}">
        <div class="message info">
            <div class="alert alert-danger" data-time="3000" show-alert role="alert">
                ${errorMessage} <span close-alert>X</span>
            </div>
        </div>
    </c:if>

    <div class="sidebar">
        <form:form method="POST" action="/autopart/account/edit.htm" modelAttribute="customer" id="editForm">
            <img id="user_icon" src="https://i.pinimg.com/474x/89/90/48/899048ab0cc455154006fdb9676964b3.jpg" width="100px" height="100px" alt="">
            <p>
                <form:input type="hidden" path="cartId" required="required"/>
            </p>
            <p class="input-field">
                <label>Email</label>
                <form:input type="email" path="email" readonly="true" required="required"/>
                <span class="error-message"></span>
            </p>
            <p class="input-field">
                <label>Họ và tên</label>
                <form:input type="text" path="fullName" disabled="true" required="required"/>
                <span class="error-message"></span>
            </p>
            <p class="input-field">
                <label>Số điện thoại</label>
                <form:input type="tel" path="phone" disabled="true" required="required"/>
                <span class="error-message"></span>
            </p>
            <p class="input-field">
                <label>Địa chỉ</label>
                <form:input type="text" path="address" disabled="true" required="required"/>
                <span class="error-message"></span>
            </p>
            <p>
                <label>Trạng thái</label>
                <c:choose>
                    <c:when test="${customer.status == 'Active'}">
                        <label style="color: green;">Đang hoạt động</label>
                    </c:when>
                    <c:otherwise>
                        <label>Ngưng hoạt động</label>
                    </c:otherwise>
                </c:choose>
                <form:input path="status" type="hidden" required="required"/>
            </p>
            <div class="buttons">
                <button type="submit" class="button edit-btn" id="edit-btn">Chỉnh sửa</button>
            </div>
        </form:form>
        <div class="menu">
            <a href="#" class="order choosed"><i class="fa-solid fa-box-open"></i> Thông tin đơn hàng</a>
            <a href="#" class="setting"><i class="fa-solid fa-lock"></i> Đổi mật khẩu</a>
            <a href="/autopart"><i class="fa-solid fa-house"></i> Trang chủ</a>
            <a href="/autopart/logout.htm" class="delete-btn"><i class="fa-solid fa-right-from-bracket"></i> Đăng xuất</a>
        </div>
    </div>

    <div class="content">
        <div class="orders">
            <header class="header">
                <h1>Lịch sử đơn hàng</h1>
            </header>
            <c:choose>
                <c:when test="${empty orders or orders.size() == 0}">
                    <h2>Bạn chưa có đơn hàng nào</h2>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${orders}" var="order">
                        <div class="order-item" data-order="${order}">
                            <p><strong>Mã đơn hàng: </strong>${order.orderId}</p>
                            <p><strong>Ngày: </strong><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></p>
                            <p><strong>Tổng tiền: </strong><fmt:formatNumber value="${order.totalCost}" type="currency" currencySymbol="₫" groupingUsed="true"/></p>
                            <p><strong>Trạng thái: </strong>
                                <c:choose>
                                    <c:when test="${order.status == 'Pending'}">Chờ xác nhận</c:when>
                                    <c:when test="${order.status == 'Processing'}">Đang xử lý</c:when>
                                    <c:when test="${order.status == 'Shipping'}">Đang giao</c:when>
                                    <c:when test="${order.status == 'Completed'}">Đã giao</c:when>
                                    <c:when test="${order.status == 'Cancelled'}">Đã hủy</c:when>
                                    <c:otherwise>${order.status}</c:otherwise>
                                </c:choose>
                            </p>
                            <a class="button nav-btn" href="/autopart/order/detail.htm?orderId=${order.orderId}">Xem chi tiết đơn hàng</a>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

        <form action="/autopart/account/changepass.htm" method="post" class="change-password hide" id="changePassForm">
            <h3>Đổi mật khẩu</h3>
            <div class="form-item">
                <input type="password" name="pass" placeholder="Mật khẩu hiện tại" required autocomplete="current-password"/>
                <i class="fa-solid fa-eye"></i>
                <i class="fa-solid fa-eye-slash"></i>
                <span class="error-message"></span>
            </div>
            <div class="form-item">
                <input type="password" name="newpass" placeholder="Mật khẩu mới" required autocomplete="new-password"/>
                <i class="fa-solid fa-eye"></i>
                <i class="fa-solid fa-eye-slash"></i>
                <span class="error-message"></span>
            </div>
            <div class="form-item">
                <input type="password" name="confirmpass" placeholder="Xác nhận mật khẩu mới" required autocomplete="new-password"/>
                <i class="fa-solid fa-eye"></i>
                <i class="fa-solid fa-eye-slash"></i>
                <span class="error-message"></span>
            </div>
            <button type="submit">Cập nhật</button>
        </form>
    </div>


</body>

<script type="text/javascript"
	src="<c:url value="/resources/js/profile.js" />"></script>
</html>