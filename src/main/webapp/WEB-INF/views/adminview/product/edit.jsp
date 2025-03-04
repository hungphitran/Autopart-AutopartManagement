<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Sửa sản phẩm</title>

	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">

    <style>
        .custom-file {
            position: relative;
            display: flex;
            align-items: center;
        }
        
        .custom-file-input {
            position: absolute;
            width: 100%;
            height: 100%;
            opacity: 0;
            cursor: pointer;
        }
        
        .custom-file-label {
            flex-grow: 1;
            padding: 0.375rem 0.75rem;
            border: 1px solid #ced4da;
            border-radius: 0.25rem;
            background-color: #fff;
        }
    </style>
</head>
<body id="page-top">
	<div id="wrapper">
	    <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
	    
	    <div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
	    		<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

				<div class="container-fluid" id="container-wrapper">
                    <div class="card mb-4">
                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                        <h6 class="m-0 font-weight-bold text-primary">Sửa Sản Phẩm</h6>
                        </div>
                        <div class="card-body">
                            <form:form action="${pageContext.request.contextPath}/admin/product/edit.htm" method="post" modelAttribute="product">
                            	<input type="hidden" name="_method" value="patch"/>
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="productId">Mã sản phẩm <span class="required-text">*</span></label>
                                            <form:input required="true" type="text" class="form-control" id="productId" path="productId" name="productId" readonly="true" />
                                        </div>
                                        <div class="form-group">
                                            <label for="productName">Tên sản phẩm <span class="required-text">*</span></label>
                                            <form:input required="true" type="text" class="form-control" id="productName" path="productName" name="productName" placeholder="Nhập tên sản phẩm" />
                                        </div>
                                        <div class="form-group">
                                            <label for="brandName">Nhãn hàng sản phẩm <span class="required-text">*</span></label>
                                            <form:select required="true" path="brandId" class="form-control mb-3">
                                                <form:option value="" label="-- Chọn nhãn hàng của sản phẩm --" disabled="true"/>
                                                <c:forEach items="${brandList}" var="brand">
                                                    <c:choose>
                                                        <c:when test="${brand.brandId eq product.brandId}">
                                                            <form:option value="${brand.brandId}" label="${brand.brandName}" selected="true"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:option value="${brand.brandId}" label="${brand.brandName}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                        <div class="form-group">
                                            <label for="groupName">Danh mục sản phẩm <span class="required-text">*</span></label>
                                            <form:select required="true" path="productGroupId" class="form-control mb-3">
                                                <form:option value="" label="-- Chọn danh mục của sản phẩm --" disabled="true"/>
                                                <c:forEach items="${productGroupList}" var="productGroup">
                                                    <c:choose>
                                                        <c:when test="${productGroup.productGroupId eq product.productGroupId}">
                                                            <form:option value="${productGroup.productGroupId}" label="${productGroup.groupName}" selected="true"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form:option value="${productGroup.productGroupId}" label="${productGroup.groupName}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                        <div class="form-group">
                                            <label for="costPrice">Giá gốc <span class="required-text">*</span></label>
                                            <div class="input-group mb-3">
                                                <form:input required="true" type="number" class="form-control" id="costPrice" path="costPrice" name="costPrice" placeholder="Nhập giá tiền gốc của sản phẩm" />
                                                <div class="input-group-append">
                                                    <span class="input-group-text">VNĐ</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="salePrice">Giá bán <span class="required-text">*</span></label>
                                            <div class="input-group mb-3">
                                                <form:input required="true" type="number" class="form-control" id="salePrice" path="salePrice" name="salePrice" placeholder="Nhập giá tiền bán của sản phẩm" />
                                                <div class="input-group-append">
                                                    <span class="input-group-text">VNĐ</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="stock">Số lượng <span class="required-text">*</span></label>
                                            <form:input required="true" type="text" class="form-control" id="stock" path="stock" name="stock" placeholder="Nhập số lượng sản phẩm" />
                                        </div>
                                        <div class="form-group">
                                            <label for="unit">Đơn vị <span class="required-text">*</span></label>
                                            <form:select required="true" path="unit" class="form-control mb-3">
											    <form:option value="" label="-- Chọn danh mục của sản phẩm --" disabled="true"/>
											    <form:option value="Cái" label="Cái" />
											    <form:option value="Bộ" label="Bộ" />
											    <form:option value="Bình" label="Bình" />
											</form:select>
                                        </div>
                                        <div class="form-group">
                                            <label for="imageUrls">Ảnh sản phẩm <span class="required-text">*</span></label>
                                            <div class="custom-file">
                                                <form:input type="file" class="custom-file-input" path="imageUrls" name="imageUrls" id="customFile" accept="image/*" onchange="previewImage(this)" />
                                                <label class="custom-file-label" for="customFile">Chọn ảnh</label>
                                            </div>
                                            <div class="mt-3">
                                                <c:choose>
                                                    <c:when test="${not empty product.imageUrls}">
                                                        <img id="preview" src="${product.imageUrls}" alt="Preview" style="max-width: 200px; max-height: 200px; display: block;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img id="preview" src="" alt="Preview" style="max-width: 200px; max-height: 200px; display: none;">
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="weight">Khối lượng <span class="required-text">*</span></label>
                                            <div class="input-group mb-3">
                                                <form:input required="true" type="number" class="form-control" path="weight" name="weight" placeholder="Nhập khối lượng sản phẩm" />
                                                <div class="input-group-append">
                                                    <span class="input-group-text">kg</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="description">Mô tả sản phẩm <span class="required-text">*</span></label>
                                            <form:textarea required="true" class="form-control" id="description" path="description" name="description" rows="3" />
                                        </div>
                                        <div class="form-group d-flex">
                                            <label for="status" class="mr-4">Trạng thái hoạt động <span class="required-text">*</span></label>
                                            <div class="custom-control custom-switch ml-4">
                                                <c:choose>
												    <c:when test="${product.status == 'Active'}">
												        <input type="checkbox" class="custom-control-input" name="status" id="status" value="Active" checked>
												    </c:when>
												    <c:otherwise>
												        <input type="checkbox" class="custom-control-input" name="status" id="status" value="Active">
												    </c:otherwise>
												</c:choose>
                                                <label class="custom-control-label" for="status">Hoạt động</label>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-primary">Xác nhận</button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>

                    <!-- Modal Logout -->
                    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabelLogout"
                    aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabelLogout">Đăng xuất</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <p>Bạn có muốn đăng xuất không?</p>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
                                <a href="login.html" class="btn btn-primary">Đăng xuất</a>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
			</div>
		</div>
	</div>

	<!-- Scroll to top -->
	<a class="scroll-to-top rounded" href="#page-top">
		<i class="fas fa-angle-up"></i>
	</a>

    <script>
        function previewImage(input) {
            const preview = document.getElementById('preview');
            const fileLabel = input.nextElementSibling;
    
            if (input.files && input.files[0]) {
                const reader = new FileReader();
    
                reader.onload = function(e) {
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                    fileLabel.textContent = input.files[0].name;
                }
    
                reader.readAsDataURL(input.files[0]);
            } else {
                preview.src = '';
                preview.style.display = 'none';
                fileLabel.textContent = 'Chọn ảnh';
            }
        }
    </script>

	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
	<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
</body>
</html>
