<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Thêm sản phẩm</title>

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
                        <h6 class="m-0 font-weight-bold text-primary">Thêm Sản Phẩm</h6>
                        </div>
                        <div class="card-body">
                            <form action="/your-controller-path" method="post">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="productID">Mã sản phẩm</label>
                                            <input type="text" class="form-control" id="productID" name="productID" placeholder="Nhập mã sản phẩm">
                                        </div>
                                        <div class="form-group">
                                            <label for="productName">Tên sản phẩm</label>
                                            <input type="text" class="form-control" id="productName" name="productName" placeholder="Nhập tên sản phẩm">
                                        </div>
                                        <div class="form-group">
                                            <label for="productBrand">Nhãn hàng sản phẩm</label>
                                            <select class="form-control mb-3" id="productBrand" name="productBrand">
                                                <option disabled>-- Chọn nhãn hàng của sản phẩm --</option>
                                                <option>Toyota</option>
                                                <option>ABC</option>
                                                <option>ABC</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="productGroup">Danh mục sản phẩm</label>
                                            <select class="form-control mb-3" id="productGroup" name="productGroup">
                                                <option disabled>-- Chọn danh mục của sản phẩm --</option>
                                                <option>Ống xả</option>
                                                <option>Nhớt</option>
                                                <option>Bánh xe</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="productCostPrice">Giá gốc</label>
                                            <input type="number" class="form-control" id="productCostPrice" name="productCostPrice" placeholder="Nhập giá tiền gốc của sản phẩm">
                                        </div>
                                        <div class="form-group">
                                            <label for="productSalePrice">Giá bán</label>
                                            <input type="number" class="form-control" id="productSalePrice" name="productSalePrice" placeholder="Nhập giá tiền bán của sản phẩm">
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="productStock">Số lượng</label>
                                            <input type="text" class="form-control" id="productStock" name="productStock" placeholder="Nhập số lượng sản phẩm">
                                        </div>
                                        <div class="form-group">
                                            <label for="productUnit">Đơn vị</label>
                                            <select class="form-control mb-3" id="productUnit" name="productUnit">
                                                <option disabled>-- Chọn đơn vị của sản phẩm --</option>
                                                <option>Bình</option>
                                                <option>Cái</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="productImage">Ảnh sản phẩm</label>
                                            <div class="custom-file">
                                                <input type="file" class="custom-file-input" name="productImage" id="customFile" accept="image/*" onchange="previewImage(this)">
                                                <label class="custom-file-label" for="customFile">Chọn ảnh</label>
                                            </div>
                                            <div class="mt-3">
                                                <img id="preview" src="" alt="Preview" style="max-width: 200px; max-height: 200px; display: none;">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="productWeight">Khối lượng</label>
                                            <div class="input-group mb-3">
                                                <input type="number" class="form-control" name="productWeight" placeholder="Nhập khối lượng sản phẩm">
                                                <div class="input-group-append">
                                                  <span class="input-group-text">kg</span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="productDescription">Mô tả sản phẩm</label>
                                            <textarea class="form-control" id="productDescription" name="productDescription" rows="3"></textarea>
                                        </div>
                                        <div class="form-group d-flex">
                                            <label for="customSwitch1" class="mr-4">Trạng thái hoạt động</label>
                                            <div class="custom-control custom-switch ml-4">
                                                <input type="checkbox" class="custom-control-input" id="customSwitch1" checked>
                                                <label class="custom-control-label" for="customSwitch1">Hoạt động</label>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-primary">Lưu sản phẩm</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
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
