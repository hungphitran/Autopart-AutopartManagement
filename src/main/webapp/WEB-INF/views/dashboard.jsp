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
	<link href="<c:url value='/resources/css/dashboard.css'/>" rel="stylesheet">
	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
		  <link rel="icon" type="image/x-icon" href="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp">
	
</head>
<body>
	<div class="wrapper">
		<jsp:include page="/WEB-INF/mixins/header.jsp" />

		<div class="banner">
			<img
				src="https://html.themability.com/autoelite/assets/images/banners/mainbanner1.png"
				alt="main-img">
		</div>

		<div class="cat-container">
			<!-- show all categories -->
			<div class="categories">
				<div class="categories_title">
					<h1 class="part-name">Danh mục</h1>
				</div>
				<div class="categories_list">
					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/1.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/2.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/3.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/4.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/5.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/6.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>
				</div>
			</div>
			<!-- show products -->
			<div class="list-card">
				<div class="products_title">
					<h1 class="part-name">Sản phẩm nổi bật</h1>
				</div>
				
				<div class="products_list">
					<c:forEach items="${products}" var="product">
						<div class="card" style="width: 18rem;">
							<a href="/autopart/product/detailproduct.htm?productId=${product.productId}"><img
							src="${product.imageUrls}"
							class="card-img-top" alt="..."></a>
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

		<jsp:include page="/WEB-INF/mixins/footer.jsp" />
	</div>
</body>
</html>