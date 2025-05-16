<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Giỏ hàng</title>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link href="<c:url value="/resources/css/cart.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">

</head>

<body>
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
	<div class="card">
		<div class="row">
			<div class="col-md-8 cart">
				<div class="title">
					<div class="row">
						<img
							src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp"
							alt="home" class="cart_logo-img">
						<div class="col cart_title">
							<h4>
								<b>Giỏ hàng</b>
							</h4>
						</div>
						<div>${cart}</div>
						<div class="col align-self-center text-right text-muted">${products.size()} món</div>
					</div>
				</div>
				<div class="cart-list">
					<c:forEach items="${products}" var="product">
						<div class="row border-bottom">
							<div class="row main align-items-center">
								<div class="col-2">
									<img class="img-fluid"
										src="https://html.themability.com/autoelite/assets/images/categories/1.png">
								</div>
								<div class="col">
									<div class="row text-muted">${product.key.productId }</div>
									<div class="row">${product.key.productName}</div>
																		<input type="hidden" class="price" value="${product.key.salePrice}">
									${product.key.salePrice } &#8363;
								</div>
								<div class="col updateQTY">
									<button class="btn-updateQTY"
										onclick="updateQuantity(this.nextElementSibling, -1)">-</button>
									<input class="input-updateQTY quantity" type="number" data-product-id="${product.key.productId}" value="${product.value}" min="1" max="${product.key.stock}">
									<button class="btn-updateQTY"
										onclick="updateQuantity(this.previousElementSibling, 1)">+</button>
								</div>
								<div class="col" >
									<a href="/autopart/product/detailproduct.htm?productId=${product.key.productId }">Xem chi tiết -></a>
								</div>
							</div>
						</div>
					</c:forEach>

				</div>
				<div class="back-to-shop">
					<a href="/autopart"> <span>&leftarrow;</span> <span
						class="text-muted">Tiếp tục mua sắm</span>
					</a>
				</div>
			</div>
			<form action="/autopart/order/create.htm" method="post" class="col-md-4 summary">
				<div>
					<h5>
						<b>HÓA ĐƠN</b>
					</h5>
				</div>
				<hr>
				<div class="row">
					<div class="col" style="padding-left: 0;">Số lượng: ${products.size()}</div>
					<c:forEach items="${products}" var="product">
						<input type="hidden" class="form-quantity" data-product-id="${product.key.productId}" id="${product.key.productId}" name="${product.key.productId}" value ="${product.value} " >
					</c:forEach>
						
					
				</div>
				<div class="row">
					<input type="text" name="shipAddress" placeholder="Địa chỉ" required="required">
				</div>
				<div>
					<p>Loại vận chuyển</p>
					<select name="shippingType" id="shippingType">
						<option value="" readonly>-- Chọn loại vận chuyển -- </option>
                        <option value="20000">Vận chuyển thường - 20.000 ₫</option>
                        <option value="50000">Vận chuyển nhanh - 50.000 ₫</option>
                        <option value="15000">Vận chuyển tiết kiệm - 15.000 ₫</option>
                    </select>
					<p>Mã khuyến mãi</p>
					<select name="discountId" id="discountId"></select>
				</div>
				<div class="row"
					style="border-top: 1px solid rgba(0, 0, 0, .1); padding: 2vh 0;">
					<div class="col">TỔNG TIỀN</div>
					<input class="col text-right" id="total" name="totalCost" value="" readonly> &#8363;
				</div>
				<button  class="btn">ĐẶT HÀNG</button>
			</form>
		</div>

	</div>
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
<script>
        let totalInput = document.getElementById("total");
        let priceInputs = document.querySelectorAll(".price");
        let quantityInputs = document.querySelectorAll(".quantity");
        let discountSelect = document.getElementById("discountId");
        let shippingSelect = document.getElementById("shippingType");

        // Danh sách mã khuyến mãi từ server
        const discounts = [
            <c:forEach var="discount" items="${discounts}" varStatus="loop">
                { id: "${discount.discountId}", code: "${discount.discountDesc.replace('\"', '\\\"')}", amount: ${discount.discountAmount}, minOrderValue: ${discount.minimumAmount} }<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
        ];

        console.log("Discounts: ", discounts);

        function updateDiscountOptions(totalCost) {
            const select = document.getElementById('discountId');
            select.innerHTML = '<option value="">-- Chọn mã khuyến mãi --</option>';
            discounts.forEach(discount => {
                const option = document.createElement('option');
                option.value = discount.id;
                option.text = discount.code + " - Giảm " + discount.amount + "% (Tối thiểu: " + discount.minOrderValue.toLocaleString('vi-VN') + " VNĐ)";
                if (discount.minOrderValue > totalCost) {
                    option.disabled = true;
                }
                select.appendChild(option);
            });
        }

        function calculateTotalWithDiscount(baseTotal, discountId, shippingCost) {
            let total = baseTotal;
            if (discountId) {
                const selectedDiscount = discounts.find(discount => discount.id === discountId);
                if (selectedDiscount && selectedDiscount.minOrderValue <= baseTotal) {
                    total = total * (1 - selectedDiscount.amount / 100);
                }
            }
            total += parseFloat(shippingCost || 0);
            return total;
        }

        function updateCost() {
            let baseTotal = 0;
            for (let i = 0; i < priceInputs.length; i++) {
                let price = parseFloat(priceInputs[i].value);
                let quantity = parseInt(quantityInputs[i].value);
                baseTotal += price * quantity;
            }
            const selectedDiscountId = discountSelect.value;
            const selectedShippingCost = shippingSelect.value;
            const finalTotal = calculateTotalWithDiscount(baseTotal, selectedDiscountId, selectedShippingCost);
            totalInput.value = finalTotal.toLocaleString('vi-VN');
            updateDiscountOptions(baseTotal);
        }

        // Lắng nghe sự kiện thay đổi mã giảm giá
        discountSelect.addEventListener('change', function() {
            let baseTotal = 0;
            for (let i = 0; i < priceInputs.length; i++) {
                let price = parseFloat(priceInputs[i].value);
                let quantity = parseInt(quantityInputs[i].value);
                baseTotal += price * quantity;
            }
            const selectedDiscountId = this.value;
            const selectedShippingCost = shippingSelect.value;
            const finalTotal = calculateTotalWithDiscount(baseTotal, selectedDiscountId, selectedShippingCost);
            totalInput.value = finalTotal.toLocaleString('vi-VN');
        });

        // Lắng nghe sự kiện thay đổi loại vận chuyển
        shippingSelect.addEventListener('change', function() {
            let baseTotal = 0;
            for (let i = 0; i < priceInputs.length; i++) {
                let price = parseFloat(priceInputs[i].value);
                let quantity = parseInt(quantityInputs[i].value);
                baseTotal += price * quantity;
            }
            const selectedDiscountId = discountSelect.value;
            const selectedShippingCost = this.value;
            const finalTotal = calculateTotalWithDiscount(baseTotal, selectedDiscountId, selectedShippingCost);
            totalInput.value = finalTotal.toLocaleString('vi-VN');
        });

        // Gọi hàm lần đầu khi trang tải
        updateCost();

        function updateQuantity(input, delta) {
            let value = parseInt(input.value) + delta;
            if (value < 1) {
              showDialog(input);
            } else if (value > parseInt(input.max)) {
              return;
            } else {
              input.value = value;
              // Update the corresponding hidden input
              const productId = input.dataset.productId;
              console.log(productId)
              const hiddenInput = document.querySelector(`.form-quantity[data-product-id="${productId}"]`);
              console.log(hiddenInput)
              if (hiddenInput) {
                hiddenInput.value = value;
              }
              updateCost();
            }
          }

        function showDialog(input) {
            const dialogOverlay = document.getElementById('dialog-overlay');
            dialogOverlay.style.visibility = 'visible';
            const confirmButton = document.getElementById('confirm-button');
            confirmButton.onclick = function() {
                input.closest('.cart-item').remove();
                dialogOverlay.style.visibility = 'hidden';
            };
            const cancelButton = document.getElementById('cancel-button');
            cancelButton.onclick = function() {
                dialogOverlay.style.visibility = 'hidden';
            };
        }
    </script>
</html>