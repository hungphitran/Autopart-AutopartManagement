<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Autopart</title>
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
		integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
		crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">

	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/filterproduct.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/mixins/header.jsp" />

	<div class="container_filter">
		<div class="sidebar">

			<div>
				<h2>Bộ lọc</h2>
				<p>
					<strong>Từ khóa: ${keyword}</strong>
				</p>
			</div>
	
			<div class="filter-form">
				<form action="/autopart/product/search.htm" method="get">
					<div class="search-form">
						<input type="text" name="keyword" placeholder="Search...">
					</div>
	
					<div class="form-group">
						<label for="brand">Hãng</label> <select id="brand" name="brand"
							class="form-control">
							<option value="All">Tất cả</option>
							<c:forEach var="brand" items="${brands}">
								<c:choose>
									<c:when test="${brand.brandName.equals(brand)}">
										<option value="${brand.brandName}" selected>${brand.brandName}</option>
									</c:when>
									<c:otherwise>
										<option value="${brand.brandName}">${brand.brandName}</option>									
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="category">Loại hàng</label> <select id="category"
							name="category" class="form-control">
							<option value="All">Tất cả</option>
							<c:forEach var="group" items="${categories}">
								<c:choose>
									<c:when test="${group.groupName.equals(category)}">
										<option value="${group.groupName}" selected>${group.groupName}</option>
									</c:when>
									<c:otherwise>
										<option value="${group.groupName}">${group.groupName}</option>									
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<button type="submit" class="btn btn-primary">Tìm kiếm</button>
				</form>
			</div>
		</div>
	
		<div class="content">
			<!-- show products -->
	
			<div class="list-card">
					<c:forEach items="${products}" var="product">
						<div class="card" style="width: 18rem;">
							<a href="/autopart/product/detailproduct.htm?productId=${product.productId}"><img
							src="${product.imageUrls}"
							class="card-img-top" ></a>
							<div class="card-body">
								<h5 class="card-title">${product.productName}</h5>
								<p class="card-text">${product.description}</p>
								<p>Giá bán: ${product.salePrice }đ</p>
							</div>
						</div>
					</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
