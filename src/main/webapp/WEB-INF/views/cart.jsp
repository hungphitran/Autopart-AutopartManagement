<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    
    	 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<link href="<c:url value="/resources/css/cart.css" />" rel="stylesheet">
    <script>
        function updateQuantity(input, delta) {
            let value = parseInt(input.value) + delta;
            if (value < 1) {
                showDialog(input);
            } else {
                input.value = value;
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
</head>
<body>
    <div class="cart-container">
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
        <!-- Add more cart items as needed -->
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
    </div>
</body>
</html>