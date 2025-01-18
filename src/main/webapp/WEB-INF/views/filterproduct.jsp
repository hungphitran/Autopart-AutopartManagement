<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<link href="<c:url value="/resources/css/filterproduct.css" />" rel="stylesheet">
</head>
<body>

	<header class="header">

		<a class="logo" href="/autopart">
			<img
				src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp"
				alt="home" width="100" height="100">
		</a>

	</header>

	<div class="sidebar">

		<div>
			<h2>Bộ lọc</h2>
			<p>
				<strong>Từ khóa: ${keyword}</strong>
			</p>
		</div>

		<div class="filter-form">
			<form action="/autopart/search.htm" method="get">
				<div class="search-form">
					<input type="text" name="keyword" placeholder="Search...">
				</div>

				<div class="form-group">
					<label for="brand">Hãng</label> <select id="brand" name="brand"
						class="form-control">
						<option value="">Tất cả</option>
						<c:forEach var="name" items="${brands}">
							<c:choose>
								<c:when test="${name.equals(brand)}">
									<option value="${name}" selected>${name}</option>
								</c:when>
								<c:otherwise>
									<option value="${name}">${name}</option>									
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="category">Loại hàng</label> <select id="category"
						name="category" class="form-control">
						<option value="">Tất cả</option>
						<c:forEach var="name" items="${categories}">
							<c:choose>
								<c:when test="${name.equals(category)}">
									<option value="${name}" selected>${name}</option>
								</c:when>
								<c:otherwise>
									<option value="${name}">${name}</option>									
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
			<div class="card" style="width: 18rem;">
				<a href="/autopart/detailproduct.htm"><img
					src="https://cf.shopee.vn/file/sg-11134201-7rfha-m4fpfp46y804d8"
					class="card-img-top" alt="..."></a>

				<div class="card-body">
					<h5 class="card-title">Card title</h5>
					<p class="card-text">Some quick example text to build on the
						card title and make up the bulk of the card's content.</p>
					<p>$$$$$$$$$$$$$$1</p>
				</div>
			</div>

			<div class="card" style="width: 18rem;">
				<a href="/autopart/detailproduct.htm"><img
					src="https://html.themability.com/autoelite/assets/images/products/13.jpg"
					class="card-img-top" alt="..."></a>

				<div class="card-body">
					<h5 class="card-title">Card title</h5>
					<p class="card-text">Some quick example text to build on the
						card title and make up the bulk of the card's content.</p>
					<p>$$$$$$$$$$$$$$2</p>
				</div>
			</div>

			<div class="card" style="width: 18rem;">
				<a href="/autopart/detailproduct.htm"><img
					src="https://html.themability.com/autoelite/assets/images/products/9.jpg"
					class="card-img-top" alt="..."></a>

				<div class="card-body">
					<h5 class="card-title">Card title</h5>
					<p class="card-text">Some quick example text to build on the
						card title and make up the bulk of the card's content.</p>
					<p>$$$$$$$$$$$$$$3</p>
				</div>
			</div>

			<div class="card" style="width: 18rem;">
				<a href="/autopart/detailproduct.htm"><img
					src="https://html.themability.com/autoelite/assets/images/products/4.jpg"
					class="card-img-top" alt="..."></a>

				<div class="card-body">
					<h5 class="card-title">Card title</h5>
					<p class="card-text">Some quick example text to build on the
						card title and make up the bulk of the card's content.</p>
					<p>$$$$$$$$$$$$$$4</p>
				</div>
			</div>

			<div class="card" style="width: 18rem;">
				<a href="/autopart/detailproduct.htm"><img
					src="https://cf.shopee.vn/file/sg-11134201-7rfha-m4fpfp46y804d8"
					class="card-img-top" alt="..."></a>

				<div class="card-body">
					<h5 class="card-title">Card title</h5>
					<p class="card-text">Some quick example text to build on the
						card title and make up the bulk of the card's content.</p>
					<p>$$$$$$$$$$$$$$5</p>
				</div>
			</div>

			<div class="card" style="width: 18rem;">
				<a href="/autopart/detailproduct.htm"><img
					src="https://cf.shopee.vn/file/sg-11134201-7rfha-m4fpfp46y804d8"
					class="card-img-top" alt="..."></a>

				<div class="card-body">
					<h5 class="card-title">Card title</h5>
					<p class="card-text">Some quick example text to build on the
						card title and make up the bulk of the card's content.</p>
					<p>$$$$$$$$$$$$$$6</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
