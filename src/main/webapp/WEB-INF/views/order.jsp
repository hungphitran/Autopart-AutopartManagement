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
	<!-- <div class="cart-container">
        <div class="cart-header">
            <h1>Giỏ hàng</h1>
        </div>
        <div class="cart-item">
            <img src="product1.jpg" alt="Product 1">
            <div class="cart-item-details">
                <h2>Sản phẩm 1</h2>
                <p>Product description goes here.</p>
                <div class="cart-item-quantity">
                    Quantity: 
                    <div class="quantity-controls">
                        <button onclick="updateQuantity(this.nextElementSibling, -1)">-</button>
                        <input type="number" value="1" min="1" readonly>
                        <button onclick="updateQuantity(this.previousElementSibling, 1)">+</button>
                    </div>
                </div>
                <div class="cart-item-size">Size: M</div>
                <div class="cart-item-color">Color: Red</div>
            </div>
            <div class="cart-item-price">$10.00</div>
        </div>
        <div class="cart-item">
            <img src="product2.jpg" alt="Product 2">
            <div class="cart-item-details">
                <h2>Sản phẩm 2</h2>
                <p>Product description goes here.</p>
                <div class="cart-item-quantity">
                    Quantity: 
                    <div class="quantity-controls">
                        <button onclick="updateQuantity(this.nextElementSibling, -1)">-</button>
                        <input type="number" value="2" min="1" readonly>
                        <button onclick="updateQuantity(this.previousElementSibling, 1)">+</button>
                    </div>
                </div>
                <div class="cart-item-size">Size: L</div>
                <div class="cart-item-color">Color: Blue</div>
            </div>
            <div class="cart-item-price">$20.00</div>
        </div>
       
    </div>
    <div class="sidebar">
        <div class="total-cost">Total: $50.00</div>
        <button>Proceed to Checkout</button>
    </div>
    <div id="dialog-overlay" class="dialog-overlay">
        <div class="dialog">
            <p>Are you sure you want to remove this item from the cart?</p>
            <button id="confirm-button">Yes</button>
            <button id="cancel-button">No</button>
        </div>
    </div> -->

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
						<input type="hidden" id="${product.key.productId}" name="${product.key.productId}" value ="${product.value}">
					</c:forEach>
				</div>
				<div class="row">
					<input type="text" name="shipAddress" placeholder="Địa chỉ" required="required">
				</div>
				<div>
					<p>Loại vận chuyển</p>
					<select>
						<option class="text-muted">Vận chuyển thường- 20.000
							&#8363;</option>
						<option class="text-muted">Vận chuyển nhanh- 50.000
							&#8363;</option>
						<option class="text-muted">Vận chuyển tiết kiệm- 15.000
							&#8363;</option>
					</select>
					<p>Mã khuyến mãi</p>
					<input id="code" placeholder="Nhập mã khuyến mãi">
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
<script>
	
let totalInput= document.getElementById("total");
let priceInputs = document.querySelectorAll(".price");
let quantityInputs = document.querySelectorAll(".quantity");

console.log(totalInput, priceInputs , quantityInputs)


function updateCost(){
	let total = 0;
	for(let i = 0; i < priceInputs.length; i++) {
	    let price = parseFloat(priceInputs[i].value);
	    let quantity = parseInt(quantityInputs[i].value);
	    console.log(price, quantity)
	    total += price * quantity;
	}
	console.log("update cost", total)

	totalInput.value = total
}

updateCost();
	function updateQuantity(input, delta) {
		let value = parseInt(input.value) + delta;
		if (value < 1) {
			showDialog(input);
		}
		else if(value>input.max){
			return;
		}
		else {
			input.value = value;
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