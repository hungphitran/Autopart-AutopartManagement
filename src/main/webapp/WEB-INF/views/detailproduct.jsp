<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Automobile Product Detail</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/resources/css/detailProduct.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/mixins/header.jsp" />

	<div class="container">
		<div class="col-md-6">
			<c:forEach items="${imgUrls}" begin="0" end="0" var="url">
				<img id="main-image" src="${url}" width="500" height="500"
					alt="Car Image" class="img-fluid">
			</c:forEach>
			<div class="list-image">
				<c:forEach items="${imgUrls}" begin="0" end="0" var="url">
					<img src="${url}" class="image-item active"
						onclick="updateMainImg(this)" width="50" height="50"
						alt="Car Image" class="img-fluid">
				</c:forEach>
				<c:forEach items="${imgUrls}" begin="1" var="url">
					<img src="${url}" class="image-item" onclick="updateMainImg(this)"
						width="50" height="50" alt="Car Image" class="img-fluid">
				</c:forEach>
			</div>

		</div>
		<div class="col-md-6 product-details">
			<h2>${product.productName}</h2>
			<p>
				<strong>Mã sản phẩm: ${product.productId}</strong>
			</p>
			<p>
				<strong>Giá bán:</strong> ${product.salePrice}
			</p>
			<p>
				<strong>Mô tả:</strong> ${product.description}.
			</p>
			<ul>
				<li>Hãng: ${brand.brandName}</li>
				<li>Loại hàng: ${group.groupName}</li>
				<li>Trọng lượng: ${product.weight}</li>
				<li>Số lượng: ${product.stock} ${product.unit}</li>
			</ul>

			<c:if test="${product.stock>0 }">
				<form action="/autopart/product/addproduct.htm" method="post">
					<div class="input-group mb-3">
						<button class="btn btn-danger" type="button" id="button-minus">-</button>
						<input type="number" class="form-control" name="quantity"
							id="quantity" value="1" min="1" max="${product.stock}">
						<button class="btn btn-danger" type="button" id="button-plus">+</button>

					</div>
					<button class="btn btn-danger" type="submit">Thêm vào giỏ
						hàng</button>
					<input type="hidden" name="productId" value="${product.productId}">

				</form>
			</c:if>

			<c:if test="${product.stock<=0}">
				<ul>
					<li><p>Hết hàng</p></li>
				</ul>

			</c:if>


		</div>
	</div>

	<jsp:include page="/WEB-INF/mixins/footer.jsp" />

	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	<script src="<c:url value="/resources/js/detailProduct.js" />"></script>

</body>
</html>