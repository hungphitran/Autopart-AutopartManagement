<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tìm kiếm sản phẩm</title>
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
		integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
		crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
	<link href="<c:url value='/resources/css/base.css' />" rel="stylesheet">
	<link href="<c:url value='/resources/css/filterproduct.css' />" rel="stylesheet">
	
	<style>
		:root {
        --main-color: #ff6f61;
        --main-color-2: #fb5748;
        --text-color: #333;
        --light-bg: #f8f9fa;
        --border-color: #e9e9e9;
      }
      .sidebar {
        background-color: white;
        padding: 20px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      }
      .sidebar h2 {
        color: var(--main-color);
        margin-bottom: 20px;
      }
      .filter-form h3 {
        color: var(--text-color);
        margin-bottom: 15px;
      }
      .filter-form h3 i {
        color: var(--main-color);
        margin-right: 8px;
      }
      .form-group label {
        color: var(--text-color);
      }
      .form-group label i {
        color: var(--main-color);
        margin-right: 8px;
      }
      .card {
        transition: transform 0.3s ease, box-shadow 0.3s ease;
        border: 1px solid var(--border-color);
      }
      .card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      }
      .card-title {
        color: var(--text-color);
      }
      .card-text {
        color: var(--text-color);
      }
      .card-body p:last-child {
        color: var(--main-color);
      }
      .empty-state {
        color: var(--text-color);
      }
      .empty-state i {
        color: var(--main-color);
      }
      .card.inactive {
		  position: relative;
		  pointer-events: none; 
		  opacity: 1;
		}
		
		.card.inactive .card-body,
		.card.inactive .card-img-wrapper {
		  opacity: 0.6; 
		}
		
		/* Tạo lớp phủ */
		.card.inactive::before {
		  content: '';
		  position: absolute;
		  top: 0; left: 0; right: 0; bottom: 0;
		  background-color: rgba(128, 128, 128, 0.3);
		  z-index: 1;
		  border-radius: 0.375rem;
		}
		
		.inactive-overlay {
		  position: absolute;
		  top: 50%;
		  left: 50%;
		  transform: translate(-50%, -50%) rotate(-45deg);
		  background-color: #dc3545;
		  color: white;
		  padding: 8px 40px;
		  font-weight: bold;
		  font-size: 14px;
		  z-index: 2;
		  white-space: nowrap;
		  border-radius: 4px;
		  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
		  pointer-events: none; 
		}
		
		.card.inactive a {
		  pointer-events: none;
		}

	</style>
</head>
<body>
	<jsp:include page="/WEB-INF/mixins/header.jsp" />

	<div class="container_filter">
		<div class="sidebar">
			<h2>
				<strong>Bộ lọc</strong>
			</h2>
			<div class="filter-form">
				<div>
					<c:choose>
						<c:when test="${keyword==null || keyword.length()==0}">
							<h3>
								<i class="fa-solid fa-magnifying-glass"></i>
								Nhập từ khóa để tìm kiếm
							</h3>
						</c:when>
						<c:otherwise>
							<h3>
								<i class="fa-solid fa-magnifying-glass"></i>
								Tìm kiếm theo từ khóa: "${keyword}"
							</h3>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="form-group">
					<label for="brand">
						<i class="fa-solid fa-tag"></i>
						Hãng
					</label>
					<select id="brand-filter" name="brand" class="form-control">
						<c:choose>
							<c:when test="${brand == null}">
								<option value="" selected>Tất cả</option>
							</c:when>
							<c:otherwise>
								<option value="">Tất cả</option>
							</c:otherwise>
						</c:choose>
						<c:forEach var="b" items="${brands}">
							<c:choose>
								<c:when test="${b.brandId == brand}">
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
					<label for="category">
						<i class="fa-solid fa-list"></i>
						Loại hàng
					</label>
					<select id="category-filter" name="category" class="form-control">
						<c:choose>
							<c:when test="${group == null}">
								<option value="" selected>Tất cả</option>
							</c:when>
							<c:otherwise>
								<option value="">Tất cả</option>
							</c:otherwise>
						</c:choose>
						<c:forEach var="gr" items="${categories}">
							<c:choose>
								<c:when test="${gr.productGroupId == group}">
									<option value="${gr.productGroupId}" selected>${gr.groupName}</option>
								</c:when>
								<c:otherwise>
									<option value="${gr.productGroupId}">${gr.groupName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="content">
			<div class="list-card ${products.size() == 0 ? 'empty' : ''}">
				<c:choose>
					<c:when test="${products.size() == 0}">
						<div class="empty-state">
							<i class="fa-solid fa-box-open"></i>
							<p>Không tìm thấy sản phẩm nào</p>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${products}" var="product">
						  <div class="card ${product.status == 'Inactive' ? 'inactive' : ''}" 
						       data-brand="${product.brandId}" 
						       data-category="${product.productGroupId}">
						    
						    <div class="card-img-wrapper">
						      <a href="/autopart/product/detailproduct.htm?productId=${product.productId}">
						        <img class="card-img-top" src="${product.imageUrls}">
						      </a>
						    </div>
						    
						    <div class="card-body">
						      <h5 class="card-title">${product.productName}</h5>
						      <p>
						        <i class="fa-solid fa-tag"></i>
						        <fmt:formatNumber value="${product.salePrice}" pattern="#,##0₫"/>
						      </p>
						    </div>
						    
						    <c:if test="${product.status == 'Inactive'}">
						      <div class="inactive-overlay">TẠM NGỪNG BÁN</div>
						    </c:if>
						  </div>
						</c:forEach>

					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>

	<script src="<c:url value="/resources/js/filterproduct.js" />"></script>
</body>
</html>