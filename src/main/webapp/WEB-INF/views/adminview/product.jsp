<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Product List</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<link href="<c:url value="/resources/css/admincss/product.css" />"
	rel="stylesheet">
</head>
<body>
	<jsp:include page="/WEB-INF/mixins/adminnav.jsp" />

	<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />
	<div class="wrapper">
		<div class="container">
			<div id="product-list" class="product-list">
				<table>
					<thead>
						<tr>
							<th>Mã sản phẩm</th>
							<th>Tên sản phẩm</th>
							<th>Số lượng</th>
							<th>Trạng thái</th>
						</tr>
					</thead>
					<tbody id="table-body">
						<c:forEach items="${products}" var="product">
							<tr class="product-item" data-product-id="${product.productId}"
								data-product-name="${product.productName}"
								data-group-name="${product.groupName}"
								data-brand-name="${product.brandName}"
								data-sale-price="${product.salePrice}"
								data-cost-price="${product.costPrice}"
								data-stock="${product.stock}" data-unit="${product.unit}"
								data-image-urls="${product.imageUrls}"
								data-weight="${product.weight}" data-status="${product.status}"
								data-deleted-at="${product.deletedAt}"
								data-description="${product.description}">
								<td>${product.productId}</td>
								<td>${product.productName}</td>
								<td>${product.stock}</td>
								<td>${product.status}</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>

			<div id="pagination" class="pagination">
				<button id="prevPage" onclick="prevPage()">Trước</button>
				<span id="pageInfo"></span>
				<button id="nextPage" onclick="nextPage()">Sau</button>
			</div>
		</div>

	</div>
	<!-- The Modal -->
	<div id="productModal" class="modal">
		<div class="modal-content">
			<span class="close">&times;</span>
			<div id="product-detail-content">
				<p>
					<strong>Mã sản phẩm:</strong> <span id="modal-product-id"></span>
				</p>
				<p>
					<strong>Tên sản phẩm:</strong> <span id="modal-product-name"></span>
				</p>
				<p>
					<strong>Nhóm:</strong> <span id="modal-group-name"></span>
				</p>
				<p>
					<strong>Thương hiệu:</strong> <span id="modal-brand-name"></span>
				</p>
				<p>
					<strong>Giá bán:</strong> <span id="modal-sale-price"></span>
				</p>
				<p>
					<strong>Giá gốc:</strong> <span id="modal-cost-price"></span>
				</p>
				<p>
					<strong>Số lượng:</strong> <span id="modal-stock"></span>
				</p>
				<p>
					<strong>Đơn vị:</strong> <span id="modal-unit"></span>
				</p>
				<p>
					<strong>Hình ảnh:</strong> <span id="modal-image-urls"></span>
				</p>
				<p>
					<strong>Trọng lượng:</strong> <span id="modal-weight"></span>
				</p>
				<p>
					<strong>Trạng thái:</strong> <span id="modal-status"></span>
				</p>
				<p>
					<strong>Ngày xóa:</strong> <span id="modal-deleted-at"></span>
				</p>
				<p>
					<strong>Mô tả:</strong> <span id="modal-description"></span>
				</p>
			</div>
			<button class="edit-btn">Edit</button>
			<button class="delete-btn">Delete</button>
		</div>
	</div>

	<script>
		document
				.addEventListener(
						'DOMContentLoaded',
						function() {
							const productModal = document
									.getElementById('productModal');
							const modalClose = document.querySelector('.close');
							const modalProductId = document
									.getElementById('modal-product-id');
							const modalProductName = document
									.getElementById('modal-product-name');
							const modalGroupName = document
									.getElementById('modal-group-name');
							const modalBrandName = document
									.getElementById('modal-brand-name');
							const modalSalePrice = document
									.getElementById('modal-sale-price');
							const modalCostPrice = document
									.getElementById('modal-cost-price');
							const modalStock = document
									.getElementById('modal-stock');
							const modalUnit = document
									.getElementById('modal-unit');
							const modalImageUrls = document
									.getElementById('modal-image-urls');
							const modalWeight = document
									.getElementById('modal-weight');
							const modalStatus = document
									.getElementById('modal-status');
							const modalDeletedAt = document
									.getElementById('modal-deleted-at');
							const modalDescription = document
									.getElementById('modal-description');
							const productsPerPage = 12;
							const products = document
									.querySelectorAll('.product-item');
							const tableBody = document
									.getElementById('table-body');
							const pageInfo = document
									.getElementById('pageInfo');
							var currentPage = 1;

							function renderProducts() {
								const start = (currentPage - 1)
										* productsPerPage;
								const end = start + productsPerPage;
								tableBody.innerHTML = "";
								for (let i = start; i < end; i++) {
									if (products.item(i)) {
										const row = document
												.createElement('tr');
										row.classList.add('product-item');
										row.innerHTML = products.item(i).innerHTML;
										row.dataset.productId = products
												.item(i).dataset.productId;
										row.dataset.productName = products
												.item(i).dataset.productName;
										row.dataset.groupName = products
												.item(i).dataset.groupName;
										row.dataset.brandName = products
												.item(i).dataset.brandName;
										row.dataset.salePrice = products
												.item(i).dataset.salePrice;
										row.dataset.costPrice = products
												.item(i).dataset.costPrice;
										row.dataset.stock = products.item(i).dataset.stock;
										row.dataset.unit = products.item(i).dataset.unit;
										row.dataset.imageUrls = products
												.item(i).dataset.imageUrls;
										row.dataset.weight = products.item(i).dataset.weight;
										row.dataset.status = products.item(i).dataset.status;
										row.dataset.deletedAt = products
												.item(i).dataset.deletedAt;
										row.dataset.description = products
												.item(i).dataset.description;
										row.addEventListener('click',
												function() {
													showModal(this);
												});
										tableBody.appendChild(row);
									}
								}
								pageInfo.innerHTML = "Trang " + currentPage;
							}

							function nextPage() {
								if ((currentPage * productsPerPage) < products.length) {
									currentPage++;
									renderProducts();
								}
							}

							function prevPage() {
								if (currentPage > 1) {
									currentPage--;
									renderProducts();
								}
							}

							function showModal(product) {
								modalProductId.textContent = product.dataset.productId;
								modalProductName.textContent = product.dataset.productName;
								modalGroupName.textContent = product.dataset.groupName;
								modalBrandName.textContent = product.dataset.brandName;
								modalSalePrice.textContent = product.dataset.salePrice;
								modalCostPrice.textContent = product.dataset.costPrice;
								modalStock.textContent = product.dataset.stock;
								modalUnit.textContent = product.dataset.unit;
								modalImageUrls.textContent = product.dataset.imageUrls;
								modalWeight.textContent = product.dataset.weight;
								modalStatus.textContent = product.dataset.status;
								modalDeletedAt.textContent = product.dataset.deletedAt;
								modalDescription.textContent = product.dataset.description;
								productModal.style.display = "block";
							}

							modalClose.addEventListener('click', function() {
								productModal.style.display = "none";
							});

							window.addEventListener('click', function(event) {
								if (event.target == productModal) {
									productModal.style.display = "none";
								}
							});

							document.getElementById('prevPage')
									.addEventListener('click', prevPage);
							document.getElementById('nextPage')
									.addEventListener('click', nextPage);
							renderProducts();
						});
	</script>
</body>
</html>
