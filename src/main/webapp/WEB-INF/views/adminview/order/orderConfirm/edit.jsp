<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa Đơn Hàng</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">

    <style>
	    .product-list, .selected-products {
	        max-height: 350px;
	        overflow-y: auto;
	    }
	    
	    .product-item, .selected-item {
	        border: 1px solid #ddd;
	        padding: 10px;
	        margin-bottom: 5px;
	        border-radius: 5px;
	        cursor: pointer;
	        display: flex;
	        align-items: center;
	        justify-content: space-between;
	    }
	    
	    .product-item:hover, .selected-item:hover {
	        background-color: #f8f9fa;
	    }
	    
	    .error {
	        color: red;
	        font-size: 0.9em;
	        display: none;
	    }
	    
	    .separation {
	        margin: 20px 0;
	        border: 1px solid #aaa;
	    }
	    
	    .quantity-input {
	        width: 60px;
	        padding: 2px 5px;
	        margin-left: 10px;
	        border: 1px solid #ccc;
	        border-radius: 4px;
	        text-align: center;
	    }
	    
	    .selected-item .btn-danger {
	        margin-left: 10px;
	    }
	    
	    .selected-item span {
	        flex-grow: 1;
	    }
	    
	    .totalCost {
	    	margin-top: 32px;
	    }
	    
	    #totalCostlbl {
	    	text-transform: uppercase;
	    	font-weight: bold;
	    	color: #ff6f61;
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
                            <h6 class="m-0 font-weight-bold text-primary">Sửa Đơn Hàng</h6>
                        </div>
                        <div class="card-body">
                            <form:form action="${pageContext.request.contextPath}/admin/order/edit.htm" method="post" modelAttribute="order">
                                <div class="row">
                                    <!-- Khung chọn sản phẩm -->
                                    <div class="col-lg-12">
                                        <h5>Chọn Sản Phẩm</h5>
                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="form-group">
                                                    <label for="productSearch">Tìm sản phẩm</label>
                                                    <input type="text" class="form-control" id="productSearch" placeholder="Nhập tên sản phẩm">
                                                </div>
                                                <div class="product-list" id="productList">
												    <c:forEach var="product" items="${products}">
												        <div class="product-item" 
													        	data-product-id="${product.productId}" 
													        	data-product-name="${product.productName}" 
													        	data-product-stock="${product.stock}"
													        	data-sale-price="${product.salePrice}">
												            ${product.productName} - Giá: <fmt:formatNumber value="${product.salePrice}" type="number" maxFractionDigits="0" groupingUsed="true" />₫
												        </div>
												    </c:forEach>
												</div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="form-group">
                                                    <label>Chi tiết đơn hàng</label>
                                                    <div class="selected-products" id="selectedProducts">
                                                        <c:forEach items="${order.orderDetails}" var="detail" varStatus="loop">
								                            <div class="selected-item" data-index="${loop.index}"
								                                 data-product-id="${detail.productId}"
								                                 data-product-name="${detail.productName}"
								                                 data-unit-price="${detail.unitPrice}"
								                                 data-amount="${detail.amount}">
								                                <div>
								                                    <div>${detail.productName}</div>
								                                    <div>Giá: <fmt:formatNumber value="${detail.unitPrice}" type="number" maxFractionDigits="0" groupingUsed="true" />₫</div>
								                                </div>
								                                <div class="d-flex justify-content-end">
								                                    <input type="number" class="quantity-input" name="orderDetails[${loop.index}].amount" min="1" value="${detail.amount}" onchange="updateTotal(this, ${loop.index}, ${detail.unitPrice})">
								                                    <form:hidden path="orderDetails[${loop.index}].productId" />
								                                    <form:hidden path="orderDetails[${loop.index}].productName" />
								                                    <form:hidden path="orderDetails[${loop.index}].unitPrice" />
								                                    <button type="button" class="btn btn-danger btn-sm" onclick="removeProduct(${loop.index})">Xóa</button>
								                                </div>
								                            </div>
								                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="separation"></div>
                                <div class="row">
                                	<!-- Thông tin đơn hàng -->
                                    <div class="col-lg-12">
                                    	<h5>Điền Thông Tin Cá Nhân</h5>
                                        <div class="form-group">
                                            <label for="orderId">Mã đơn hàng <span class="required-text">*</span></label>
                                            <form:input class="form-control" path="orderId" name="orderId" data-order-id="${nextOrderId}" value="${nextOrderId}" readonly="true" />
                                        </div>
                                        <div class="form-group">
										    <label for="discountId">Mã giảm giá</label>
										    <form:select path="discountId" class="form-control mb-3" id="discountSelect">
										        <form:option value="" label="-- Chọn khuyến mãi --" disabled="true"/>
										        <c:forEach items="${discounts}" var="discount">
										            <form:option value="${discount.discountId}" 
										                         label="${discount.discountDesc} - ${discount.discountAmount}% - ${discount.minimumAmount}₫"
										                         data-discount-amount="${discount.discountAmount}"
										                         data-minimum-amount="${discount.minimumAmount}"
										                         data-status="${discount.status}"
										                         data-usage-limit="${discount.usageLimit}"
										                         data-apply-start="${discount.applyStartDate}"
										                         data-apply-end="${discount.applyEndDate}"/>
										        </c:forEach>
										    </form:select>
										</div>
										<div class="form-group">
                                            <label for="userName">Họ tên khách hàng <span class="required-text">*</span></label>
                                            <input class="form-control" value="${userName}" name="userName" required placeholder="Nhập họ và tên"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="userPhone">Số điện thoại khách hàng <span class="required-text">*</span></label>
                                            <form:input class="form-control" path="userPhone" name="userPhone" required="true" placeholder="Nhập số điện thoại"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="shipAddress">Địa chỉ giao hàng <span class="required-text">*</span></label>
                                            <form:input class="form-control" path="shipAddress" name="shipAddress" required="true" placeholder="Nhập địa chỉ giao hàng"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                	<div class="col-lg-12">
                                		<div class="form-group totalCost">
                                             <label>Tổng tiền: <span id="totalCostlbl"><fmt:formatNumber value="${order.totalCost}" type="number" maxFractionDigits="0" groupingUsed="true" />₫</span></label>
                                             <form:input class="form-control" type="hidden" id="totalCost" name="totalCost" path="totalCost"/>
                                         </div>
                                         <div id="selectedProductsInputs">
								            <!-- Các input ẩn sẽ được thêm bằng JS -->
								        </div>
                                	</div>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <button type="submit" class="btn btn-primary">Lưu Đơn Hàng</button>
                                </div>
                            </form:form>
                        </div>
                    </div>

                    <!-- Modal Logout -->
                    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabelLogout" aria-hidden="true">
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
	
	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
    <script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
	
    <script>
	    let selectedProducts = [];
	
	    // Khởi tạo selectedProducts từ các phần tử HTML hiện có trong selectedProducts
	    $(document).ready(function() {
	        $('.selected-item').each(function() {
	            var index = $(this).data('index');
	            var product = {
	                productId: $(this).data('product-id'),
	                productName: $(this).data('product-name'),
	                salePrice: $(this).data('unit-price'),
	                quantity: parseInt($(this).data('amount')) || 1
	            };
	            selectedProducts.push(product);
	        });
	
	        updateSelectedProducts();
	        calculateTotal();
	        updateDiscountOptions();
	
	        $('#productSearch').on('input', function() {
	            const searchTerm = $(this).val().toLowerCase();
	            $('.product-item').each(function() {
	                const productName = $(this).data('product-name').toLowerCase();
	                if (productName.includes(searchTerm)) {
	                    $(this).show();
	                } else {
	                    $(this).hide();
	                }
	            });
	        });
	
	        $(document).on('click', '.product-item', function() {
	            const product = {
	                productId: $(this).data('product-id'),
	                productName: $(this).data('product-name'),
	                salePrice: $(this).data('sale-price'),
	                productStock: $(this).data('product-stock')
	            };
	            addProduct(product);
	        });
	        
	        $('form').on('submit', function(e) {
	            if (selectedProducts.length === 0) {
	                e.preventDefault();
	                alert('Vui lòng chọn ít nhất một sản phẩm trước khi lưu đơn hàng!');
	                return false;
	            }
	            updateSelectedProducts(); // Đảm bảo input ẩn được cập nhật trước khi submit
	        });
	    });
	
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
	
	    // Hàm thêm sản phẩm vào danh sách đã chọn
	    function addProduct(product) {
	        if (!selectedProducts.some(p => p.productId === product.productId)) {
	            selectedProducts.push({ ...product, quantity: 1 }); // Thêm quantity mặc định là 1
	            updateSelectedProducts();
	            calculateTotal();
	            updateDiscountOptions(); // Cập nhật danh sách giảm giá khi thêm sản phẩm
	        } else {
	            alert('Sản phẩm đã được chọn!');
	        }
	    }
	
	    // Hàm xóa sản phẩm khỏi danh sách đã chọn
	    function removeProduct(productId) {
	        productId = String(productId);
	        selectedProducts = selectedProducts.filter(p => p.productId !== productId);
	        updateSelectedProducts();
	        calculateTotal();
	        updateDiscountOptions();
	    }
	
	    // Cập nhật danh sách sản phẩm đã chọn
	    function updateSelectedProducts() {
	        let html = '';
	        let inputsHtml = '';
	        selectedProducts.forEach((product, index) => {
	            html += '<div class="selected-item" data-index="' + index + '" ' +
	                    'data-product-id="' + product.productId + '" ' +
	                    'data-product-name="' + product.productName + '" ' +
	                    'data-unit-price="' + product.salePrice + '" ' +
	                    'data-amount="' + product.quantity + '">' +
	                    '<div>' + 
	                        '<div>' + product.productName + '</div>' + 
	                        '<div> Giá: ' + formatCurrency(product.salePrice) + '</div>' +
	                    '</div>' +
	                    '<div class="d-flex justify-content-end">' +
	                        '<input type="number" class="quantity-input" min="1" max="' + product.productStock + '" value="' + product.quantity + '" onchange="updateQuantity(\'' + product.productId + '\', this.value)">' +
	                        '<button type="button" class="btn btn-danger btn-sm" onclick="removeProduct(\'' + product.productId + '\')">Xóa</button>' +
	                    '</div>' +
	                    '</div>';
	
	            // Thêm input ẩn cho từng sản phẩm (ánh xạ vào orderDetails)
	            inputsHtml += '<input type="hidden" name="orderDetails[' + index + '].productId" value="' + product.productId + '" />';
	            inputsHtml += '<input type="hidden" name="orderDetails[' + index + '].productName" value="' + product.productName + '" />';
	            inputsHtml += '<input type="hidden" name="orderDetails[' + index + '].amount" value="' + product.quantity + '" />';
	            inputsHtml += '<input type="hidden" name="orderDetails[' + index + '].unitPrice" value="' + product.salePrice + '" />';
	        });
	        $('#selectedProducts').html(html);
	        $('#selectedProductsInputs').html(inputsHtml); 
	    }
	
	    // Hàm cập nhật số lượng
	    function updateQuantity(productId, newQuantity) {
	        selectedProducts = selectedProducts.map(p => {
	            if (p.productId === productId) {
	                p.quantity = parseInt(newQuantity) || 1;
	            }
	            return p;
	        });
	        updateSelectedProducts(); // Cập nhật lại input ẩn khi thay đổi số lượng
	        calculateTotal();
	        updateDiscountOptions();
	    }
	
	    // Tính tổng tiền
	    function calculateTotal(discountAmount = 0) {
	        let total = selectedProducts.reduce((sum, product) => sum + (product.salePrice * product.quantity), 0);
	        if (discountAmount != 0) {
	        	total *= (100 - discountAmount) / 100;
	        }
	        $('#totalCostlbl').text(formatCurrency(total));
	        $('#totalCost').val(total); // Lưu giá trị tổng vào hidden input
	    }
	
	    // Hàm cập nhật danh sách mã giảm giá dựa trên tổng tiền
	    function updateDiscountOptions() {
	        const totalCost = Number($('#totalCost').val());
	        const currentDate = new Date();
	
	        $('#discountSelect option').each(function() {
	            const minimumAmount = Number($(this).data('minimum-amount'));
	            const status = $(this).data('status');
	            const usageLimit = Number($(this).data('usage-limit'));
	            const applyStart = new Date($(this).data('apply-start'));
	            const applyEnd = new Date($(this).data('apply-end'));
	
	            const isValid = (
	                totalCost >= minimumAmount &&
	                status === 'Active' && 
	                currentDate >= applyStart && 
	                currentDate <= applyEnd && 
	                usageLimit > 0 
	            );
	
	            $(this).prop('disabled', !isValid); 
	            $(this).toggle(isValid); 
	        });
	
	        const selectedDiscount = $('#discountSelect').val();
	        if (!selectedDiscount || $('#discountSelect option[value="' + selectedDiscount + '"]').is(':hidden')) {
	            $('#discountSelect').val(''); 
	        }
	        
	        // Chọn ra mã khuyến mãi giảm nhiều nhất
	        let maxAmount = -Infinity; // Giá trị nhỏ nhất để so sánh
	        let $selectedOption = null;

	        $('#discountSelect option:enabled').each(function() {
	            let currentAmount = parseFloat($(this).attr("data-discount-amount")) || 0; 
	            if (currentAmount > maxAmount) {
	                maxAmount = currentAmount;
	                $selectedOption = $(this); 
	            }
	        });

	        if ($selectedOption) {
	            $selectedOption.prop("selected", true);
	            
	            // Cập nhật giá tiền
	            let discountAmount = parseFloat($selectedOption.attr("data-discount-amount")) || 0;
	            calculateTotal(discountAmount);
	        }
	    }
	</script>
</body>
</html>