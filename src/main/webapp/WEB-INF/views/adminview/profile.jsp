<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Information</title>
	<link href="<c:url value="/resources/css/admincss/profile.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/mixins/adminnav.jsp" />

	<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />
    <div class="wrapper">
    	<div class="container">
        <div class="info">
            <label for="fullName">Full Name:</label>
            <span id="fullName">John Doe</span>
        </div>
        <div class="info">
            <label for="phone">Phone:</label>
            <span id="phone">123-456-7890</span>
        </div>
        <div class="info">
            <label for="address">Address:</label>
            <span id="address">123 Main St, Anytown, USA</span>
        </div>
        <div class="info">
            <label for="avatar">Avatar:</label>
            <span id="avatar"><img alt="Avatar" width="50" height="50"></span>
        </div>
        <div class="info">
            <label for="cartId">Cart ID:</label>
            <span id="cartId">CART12345</span>
        </div>
        <div class="info">
            <label for="status">Status:</label>
            <span id="status">Active</span>
        </div>
        <div class="info">
            <label for="deletedAt">Deleted At:</label>
            <span id="deletedAt">N/A</span>
        </div>
        <div class="actions">
            <button onclick="editAccount()">Edit</button>
            <button onclick="logout()">Logout</button>
        </div>
    </div>
    </div>
</body>
</html>

<script>
    function editAccount() {
    // Implement edit account functionality
    alert('chưa làm');
    }

    function logout() {
    // Implement logout functionality
    alert('chưa làm');
    }
</script>