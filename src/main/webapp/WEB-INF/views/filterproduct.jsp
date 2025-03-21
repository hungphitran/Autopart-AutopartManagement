<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tìm kiếm sản phẩm</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<link href="<c:url value='/resources/css/base.css' />" rel="stylesheet">
	<link href="<c:url value='/resources/css/filterproduct.css' />" rel="stylesheet">
	<style>
		body {
			background-color: #f8f9fa;
			font-family: 'Arial', sans-serif;
		}

		.container_filter {
			display: flex;
			justify-content: space-between;
			padding: 20px;
			margin-top: 150px;
		}

		.sidebar {
			width: 23%;
			padding: 20px;
			background-color: #ffffff;
			border-radius: 8px;
			box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
			position: fixed;
		}

		.sidebar h2 {
			margin-bottom: 20px;
			color: #007bff;
		}

		.filter-form {
			margin-top: 20px;
		}

		.form-group label {
			font-weight: bold;
			margin-bottom: 5px;
		}

		.form-control {
			border-radius: 4px;
			padding: 10px;
			border: 1px solid #ced4da;
			transition: border-color 0.3s;
		}

		.form-control:focus {
			border-color: #80bdff;
			outline: 0;
			box-shadow: 0 0 5px rgba(128, 189, 255, 0.5);
		}

		.btn-primary {
			background-color: #007bff;
			border-color: #007bff;
			transition: background-color 0.3s, border-color 0.3s;
		}

		.btn-primary:hover {
			background-color: #0056b3;
			border-color: #004085;
		}

		.form-group {
			margin-bottom: 15px;
		}

		.content {
			width: 80%;
			margin-left: 30%;
		}

		.list-card {
			display: flex;
			flex-wrap: wrap;
			gap: 20px;
		}

		.card {
			border: 1px solid #ddd;
			border-radius: 8px;
			overflow: hidden;
			transition: transform 0.2s;
			background-color: #ffffff;
		}

		.card:hover {
			transform: scale(1.05);
		}

		.card img {
			width: 100%;
			height: 200px;
			object-fit: cover;
		}

		.card-body {
			padding: 15px;
		}

		.card-title {
			font-size: 1.25rem;
			margin-bottom: 10px;
			color: #007bff;
		}

		.card-text {
			margin-bottom: 10px;
		}
	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/mixins/header.jsp" />

	<div class="container_filter">
		<div class="sidebar">
			<div>
				<h2><strong>Bộ lọc</strong></h2>
			</div>
			<div class="filter-form">
				<div class="search-form">
				<c:if test="${keyword==null || keyword.length()==0}">
					<h3>Nhập từ khóa để tìm kiếm</h3>
				</c:if>
					
				<c:if test="${keyword!=null && keyword.length()>0}">
					<h3>Tìm kiếm theo từ khóa : "${keyword}"</h3>
				</c:if>
				</div>
				<div class="form-group">
					<label for="brand">Hãng</label>
					<select id="brand-filter" name="brand" class="form-control">
						<option value="">Tất cả</option>
						<c:forEach var="b" items="${brands}">
							<c:choose>
								<c:when test="${b.brandId.equals(brand.brandId)}">
									<option value="${b.brandId}" selected>${b.brandName}</option>
								</c:when>
								<c:otherwise>
									<option value="${b.brandId}">${b.brandName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="category">Loại hàng</label>
					<select id="category-filter" name="category" class="form-control">
						<option value="">Tất cả</option>
						<c:forEach var="group" items="${categories}">
							<c:choose>
								<c:when test="${group.productGroupId.equals(category.productGroupId)}">
									<option value="${group.productGroupId}" selected>${group.groupName}</option>
								</c:when>
								<c:otherwise>
									<option value="${group.productGroupId}">${group.groupName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="list-card">
				<c:forEach items="${products}" var="product">
					<div class="card" style="width: 18rem;" data-brand="${product.brandId}" data-category="${product.productGroupId}">
						<a href="/autopart/product/detailproduct.htm?productId=${product.productId}">
							<img src="${product.imageUrls}" class="card-img-top">
						</a>
						<div class="card-body">
							<h5 class="card-title">${product.productName}</h5>
							<p class="card-text">${product.description}</p>
							<p>Giá bán: ${product.salePrice}đ</p>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<script src="<c:url value="/resources/js/filterproduct.js" />"></script>
</body>
</html>
