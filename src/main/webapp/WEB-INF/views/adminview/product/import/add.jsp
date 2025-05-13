<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nhập Hàng</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">

    <style>
        .selected-products {
            max-height: 350px;
            overflow-y: auto;
        }
        
        .selected-item {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 5px;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .selected-item span {
            flex-grow: 1;
        }
        
        .order-info-column {
            padding: 15px;
        }
        
        .order-info-column label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .form-control {
            border-radius: 4px;
        }
        
        .form-group {
            margin-bottom: 15px;
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
                            <h6 class="m-0 font-weight-bold text-primary">Nhập Hàng</h6>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty message}">
                                <div class="alert alert-success">${message}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${error}</div>
                            </c:if>
                            <form:form action="${pageContext.request.contextPath}/admin/product/import/add.htm" method="post" modelAttribute="importForm">
                                <div class="row">
                                    <!-- Cột 1: Thông tin phiếu nhập -->
                                    <div class="col-lg-6 order-info-column">
                                        <div class="form-group">
                                            <label for="importId">Mã Phiếu Nhập <span class="text-danger">*</span></label>
                                            <form:input path="importId" type="text" class="form-control" id="importId" readonly="true" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="employeePhone">Nhân Viên Nhập <span class="text-danger">*</span></label>
                                            <form:select path="employeeEmail" name="employeeEmail" class="form-control" required="true">
                                                <form:option value="" label="-- Chọn nhân viên --" disabled="true"/>
                                                <form:options items="${employeeList}" itemValue="email" itemLabel="fullName"/>
                                            </form:select>
                                        </div>
                                        <div class="form-group">
                                            <label for="importDate">Ngày Nhập <span class="text-danger">*</span></label>
                                            <form:input path="importDate" name="importDate" type="date" class="form-control" id="importDate" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label>Tổng Chi Phí: <span id="totalCostlbl">0₫</span></label>
                                            <input class="form-control" type="hidden" id="importCost" name="importCost" value="0"/>
                                        </div>
                                    </div>
                                    <!-- Cột 2: Chọn sản phẩm -->
                                    <div class="col-lg-6 order-info-column">
                                        <div class="form-group">
                                            <label for="productId">Sản Phẩm <span class="text-danger">*</span></label>
                                            <select class="form-control" id="productId" onchange="addProduct(this)">
                                                <option value="" disabled selected>-- Chọn sản phẩm --</option>
                                                <c:forEach items="${productList}" var="product">
                                                    <option value="${product.productId}" data-name="${product.productName}" data-price="${product.costPrice}">${product.productName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label for="amount">Số Lượng <span class="text-danger">*</span></label>
                                            <input type="number" class="form-control" id="amount" min="1" value="1"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="price">Giá Nhập <span class="text-danger">*</span></label>
                                            <input type="number" class="form-control" id="price" min="0"/>
                                        </div>
                                        <button type="button" class="btn btn-primary mb-3" onclick="addProductToList()">Thêm Sản Phẩm</button>
                                        <div class="selected-products">
                                            <h6>Danh Sách Sản Phẩm Đã Chọn</h6>
                                            <div id="selectedProductsList"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex justify-content-end mt-3">
                                    <button type="submit" class="btn btn-primary">Lưu Phiếu Nhập</button>
                                    <a href="${pageContext.request.contextPath}/admin/product.htm" class="btn btn-secondary ml-2">Quay Lại</a>
                                </div>
                            </form:form>
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

    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
    <script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>

    <script>
        let selectedProducts = [];

        
        function formatCurrency(amount) {
	        const number = Number(amount);
	        
	        if (isNaN(number)) {
	            return "0₫";
	        }
	
	        let formattedNumber = number.toLocaleString('vi-VN', {
	            minimumFractionDigits: 0, 
	            maximumFractionDigits: 0 
	        });
	        
	        formattedNumber = formattedNumber.replace(/\./g, ',');
	
	        return formattedNumber + '₫';
	    }
        
        function addProduct(select) {
            const selectedOption = select.options[select.selectedIndex];
            const priceInput = document.getElementById("price");
            priceInput.value = selectedOption.getAttribute("data-price");
        }

        function addProductToList() {
            const productId = document.getElementById("productId").value;
            const productName = document.getElementById("productId").options[document.getElementById("productId").selectedIndex].text;
            const amount = parseInt(document.getElementById("amount").value);
            const price = parseFloat(document.getElementById("price").value);

            if (!productId || amount <= 0 || price < 0) {
                alert("Vui lòng nhập đầy đủ thông tin sản phẩm!");
                return;
            }

            const existingProduct = selectedProducts.find(p => p.productId === productId);
            if (existingProduct) {
                existingProduct.amount += amount;
                existingProduct.price = price; 
            } else {
                selectedProducts.push({ productId, productName, amount, price });
            }

            updateSelectedProductsList();
            updateTotalCost();
        }

        function updateSelectedProductsList() {
            const list = document.getElementById("selectedProductsList");
            list.innerHTML = "";
            const importId = document.getElementById("importId").value; // Lấy importId từ form

            selectedProducts.forEach((product, index) => {
                const item = document.createElement("div");
                item.className = "selected-item";
                item.innerHTML = '<div>' + 
                        '<div>' + product.productName + '</div>' + 
                        '<div>Số lượng: ' + product.amount + '</div>' + 
                        '<div> Giá: ' + formatCurrency(product.price) + '</div>' + 
                    '</div>' +
                    '<input type="hidden" name="importDetails[' + index + '].id.importId" value="' + importId + '">' + 
                    '<input type="hidden" name="importDetails[' + index + '].id.productId" value="' + product.productId + '">' +
                    '<input type="hidden" name="importDetails[' + index + '].amount" value="' + product.amount + '">' + 
                    '<input type="hidden" name="importDetails[' + index + '].price" value="' + product.price + '">' +
                    '<button type="button" class="btn btn-danger btn-sm" onclick="removeProduct(\'' + product.productId + '\')">Xóa</button>';
                list.appendChild(item);
            });
        }
        
        function removeProduct(index) {
            selectedProducts.splice(index, 1);
            updateSelectedProductsList();
            updateTotalCost();
        }

        function updateTotalCost() {
            const totalCost = selectedProducts.reduce((sum, product) => sum + (product.amount * product.price), 0);
            $('#totalCostlbl').text(formatCurrency(totalCost));
	        $('#importCost').val(totalCost);
        }
    </script>
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