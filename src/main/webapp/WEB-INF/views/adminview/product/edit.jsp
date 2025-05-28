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
        
        #previewContainer img {
            max-width: 100px;
            max-height: 100px;
            margin-right: 10px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body id="page-top">
<!-- Success message -->
	<c:if test="${not empty successMessage}">
		<div class="alert alert-success mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${successMessage}</div>
	</c:if>

	<!-- Error message -->
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${errorMessage}</div>
	</c:if>

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
                            <form:form action="${pageContext.request.contextPath}/admin/product/edit.htm" 
                                       method="post" 
                                       modelAttribute="product" 
                                       enctype="multipart/form-data">
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
                                                <form:input required="true" type="number" class="form-control" id="costPrice" path="costPrice" name="costPrice" readonly="true" />
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
                                            <form:input required="true" type="text" class="form-control" id="stock" path="stock" name="stock" readonly="true" />
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
                                            <!-- Trường ẩn để lưu danh sách ảnh cũ -->
                                            <c:if test="${not empty product.imageUrls}">
											    <form:input type="hidden" path="imageUrls" value="${product.imageUrls}" />
											</c:if>
                                            <div class="custom-file">
                                                <input type="file" class="custom-file-input" name="imageFiles" id="customFile" accept="image/*" multiple onchange="previewImages(this)" />
                                                <label class="custom-file-label" for="customFile">Chọn ảnh</label>
                                            </div>
                                            <div class="mt-2" id="fileNameList" style="font-size: 0.9em; color: #666;"></div>
                                            <div class="mt-3" id="previewContainer">
                                                <!-- Preview ảnh sẽ được thêm vào đây -->
                                                <c:if test="${not empty product.imageUrls}">
                                                    <c:forEach items="${product.imageUrls.split(',')}" var="existingUrl">
                                                        <c:choose>
                                                            <c:when test="${existingUrl.startsWith('https')}">
                                                                <img src="${existingUrl}" alt="Current Image (External)" class="img-fluid" style="max-width: 100px; max-height: 100px; margin-right: 10px;">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="${pageContext.request.contextPath}/resources/img/${existingUrl}" alt="Current Image (Local)" class="img-fluid" style="max-width: 100px; max-height: 100px; margin-right: 10px;">
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </c:if>
                                            </div>
                                            <input type="checkbox" name="confirmDeleteImg" id="confirmDeleteImg" value="confirm" /> Xóa ảnh cũ
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
        function previewImages(input) {
            const previewContainer = document.getElementById('previewContainer');
            const fileLabel = input.nextElementSibling;
            const fileNameList = document.getElementById('fileNameList');
            previewContainer.innerHTML = ''; // Xóa preview cũ
            fileNameList.innerHTML = '';

            if (input.files && input.files.length > 0) {
                fileLabel.textContent = 'Đã chọn ' + input.files.length + ' file';
                const fileNames = Array.from(input.files)
                    .map(file => file.name.length > 20 ? file.name.substring(0, 17) + '...' : file.name)
                    .join(', ');
                fileNameList.textContent = fileNames.length > 50 ? fileNames.substring(0, 47) + '...' : fileNames;

                Array.from(input.files).forEach(file => {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const img = document.createElement('img');
                        img.src = e.target.result;
                        img.alt = 'Preview';
                        img.style.maxWidth = '100px';
                        img.style.maxHeight = '100px';
                        img.style.marginRight = '10px';
                        img.style.marginBottom = '10px';
                        previewContainer.appendChild(img);
                    };
                    reader.readAsDataURL(file);
                });
            } else {
                fileLabel.textContent = 'Chọn ảnh';
                fileNameList.innerHTML = '';
            }
        }
    </script>

    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
    <script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
</body>
<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    // Get success and error message elements
    const successMessage = document.querySelector('.alert-success');
    const errorMessage = document.querySelector('.alert-danger');
    
    // If success message exists, hide it after 3 seconds
    if (successMessage) {
        setTimeout(function() {
            successMessage.style.transition = 'opacity 0.5s';
            successMessage.style.opacity = '0';
            setTimeout(function() {
                successMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
    
    // If error message exists, hide it after 3 seconds
    if (errorMessage) {
        setTimeout(function() {
            errorMessage.style.transition = 'opacity 0.5s';
            errorMessage.style.opacity = '0';
            setTimeout(function() {
                errorMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
});
</script>
</html>