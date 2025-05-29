<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa đơn hàng</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

     <style type="text/css">:root {
    --main-color: #ff6f61;
    --black-color: #000;
    --white-color: #fff;
    --hover-bg: #f5f5f5;
    --shadow-color: rgba(0, 0, 0, 0.1);
}

body {
    background: linear-gradient(135deg, #f5f7fa 0%, #e4e9f2 100%);
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    font-size: 0.9rem;
    padding: 20px;
}

.card {
    margin: 2rem auto;
    max-width: 1200px;
    width: 95%;
    box-shadow: 0 10px 30px var(--shadow-color);
    border-radius: 16px;
    border: none;
    background: var(--white-color);
}

.title {
    margin-bottom: 2rem;
    padding: 1.5rem 0;
    border-bottom: 1px solid var(--hover-bg);
}

.cart_title {
    display: flex;
    align-items: center;
    gap: 1rem;
}

.cart_title h4 {
    margin: 0;
    font-weight: 700;
}

.cart_logo-img {
    width: 60px;
    height: 60px;
    object-fit: contain;
}

.cart-list {
    height: 65vh;
    overflow-y: auto;
    padding-right: 10px;
    scrollbar-width: thin;
}

.cart-list::-webkit-scrollbar {
    width: 6px;
}

.cart-list::-webkit-scrollbar-track {
    background: #f1f1f1;
    border-radius: 3px;
}

.cart-list::-webkit-scrollbar-thumb {
    background: #c1c1c1;
    border-radius: 3px;
}

.cart-list .row.border-bottom {
    margin: 0 0 1rem 0;
    padding-bottom: 1rem;
    transition: all 0.3s ease;
}

.cart-list .row.border-bottom:hover {
    /* background: #f8f9fa; */
    transform: translateX(5px);
}

.updateQTY {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 8px;
}

.btn-updateQTY {
    background: var(--hover-bg);
    border: 2px solid var(--main-color);
    color: var(--main-color);
    width: 32px;
    height: 32px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1rem;
    cursor: pointer;
    transition: all 0.2s ease;
}

.btn-updateQTY:hover {
    background: var(--main-color);
    color: var(--white-color);
}

.input-updateQTY {
    width: 60px;
    text-align: center;
    font-weight: 600;
    border: 2px solid #e9ecef;
    border-radius: 8px;
    padding: 4px 8px;
    -webkit-appearance: none;
    -moz-appearance: textfield;
}

.row.main.align-items-center {
    background: var(--white-color);
    border-radius: 12px;
    padding: 1rem;
    margin: 0.75rem 0;
    box-shadow: 0 2px 8px var(--shadow-color);
    transition: all 0.3s ease;
}

.row.main.align-items-center:hover {
    box-shadow: 0 4px 12px var(--shadow-color);
    transform: translateY(-2px);
    background: var(--hover-bg);
}

.row.main.align-items-center img {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 8px;
    border: 1px solid #eee;
}

.row.text-muted {
    font-size: 0.85rem;
    color: #6c757d !important;
    margin-bottom: 0.25rem;
}

.cart {
    background-color: var(--white-color);
    padding: 2.5rem;
    border-radius: 16px 0 0 16px;
}

.summary {
    background: rgba(248, 249, 250, 0.95);
    backdrop-filter: blur(10px);
    -webkit-backdrop-filter: blur(10px);
    border-radius: 0 16px 16px 0;
    padding: 2rem;
    color: #343a40;
}

.summary h5 {
    font-size: 1.25rem;
    font-weight: 600;
    margin-bottom: 1rem;
}

.form-group {
    margin-bottom: 1rem;
}

.form-group label {
    font-size: 0.9rem;
    color: #495057;
    margin-bottom: 0.25rem;
    display: block;
}

.summary input,
.summary select {
    background-color: var(--white-color);
    border: 2px solid #e9ecef;
    border-radius: 8px;
    padding: 10px 14px;
    font-size: 0.95rem;
    width: 100%;
    transition: all 0.3s ease;
}

.summary input:focus,
.summary select:focus {
    border-color: var(--main-color);
    box-shadow: 0 0 0 3px rgba(255, 111, 97, 0.25);
    outline: none;
}

.summary select {
    appearance: none;
    background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' fill='%23495057' viewBox='0 0 16 16'%3E%3Cpath d='M7.247 11.14L2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z'/%3E%3C/svg%3E");
    background-repeat: no-repeat;
    background-position: right 1rem center;
    padding-right: 2.5rem;
}

.total-section {
    background: #f8f9fa;
    border-radius: 10px;
    padding: 1rem;
    margin: 1.5rem 0;
}

.back-btn {
    display: inline-flex;
    align-items: center;
    gap: 0.75rem;
    padding: 0.75rem 1.25rem;
    background: var(--white-color);
    border: 2px solid var(--main-color);
    border-radius: 8px;
    color: var(--main-color);
    font-weight: 600;
    font-size: 0.95rem;
    transition: all 0.3s ease;
}

.back-btn:hover {
    background: var(--main-color);
    color: var(--white-color);
    transform: translateX(-5px);
    box-shadow: 0 4px 12px rgba(255, 111, 97, 0.2);
}

.btn.order-btn {
    background: var(--main-color);
    border: 2px solid var(--main-color);
    color: var(--white-color);
    font-size: 1rem;
    font-weight: 600;
    padding: 0.75rem;
    border-radius: 8px;
    transition: all 0.3s ease;
}

.btn.order-btn:hover {
    background: #ff5347;
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(255, 111, 97, 0.3);
}

@media (max-width: 767px) {
    .card {
        margin: 1rem auto;
    }
    .cart {
        padding: 1.5rem;
        border-radius: 16px 16px 0 0;
    }
    .summary {
        padding: 1.5rem;
        border-radius: 0 0 16px 16px;
    }
    .cart_logo-img {
        width: 48px;
        height: 48px;
    }
    .row.main.align-items-center img {
        width: 60px;
        height: 60px;
    }
    .btn-updateQTY {
        width: 28px;
        height: 28px;
    }
    .input-updateQTY {
        width: 50px;
    }
}

.cart-list .row.border-bottom {
    display: flex;
    align-items: center;
    width: 100%; /* Đảm bảo chiều rộng toàn phần */
    /* background-color: #f8f9fa; Màu nền đồng nhất, bạn có thể thay đổi */
    padding: 10px 0; /* Khoảng cách đều */
}
  
.row.main.align-items-center {
    display: flex;
    justify-content: space-between; /* Căn đều các phần tử bên trong */
    width: 100%; /* Đảm bảo chiều rộng cố định */
}
  
.row.main.align-items-center .col {
    flex: 1; /* Chia đều không gian cho các cột */
    text-align: left; /* Căn trái nội dung */
}
  
.row.main.align-items-center .col:nth-child(2) {
    flex: 2; /* Dành nhiều không gian hơn cho cột tên sản phẩm nếu cần */
}
  
.updateQTY {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 120px; /* Đặt chiều rộng cố định cho phần số lượng */
    
    
}</style>
    <style>
    
        .cart-list .row.border-bottom:hover {
            transform: translateY(-3px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .product-name {
            font-size: 1.1rem;
            font-weight: 600;
            color: #2c3e50;
            margin: 0;
            line-height: 1.4;
        }
        .btn-primary, .btn-primary:focus, .btn-primary:active {
            background-color: #FF6F61 !important;
            border-color: #FF6F61 !important;
        }
        .btn-primary:hover {
            background-color: #ff543e !important;
            border-color: #ff543e !important;
        }
        .btn-outline-primary {
            color: #FF6F61;
            border-color: #FF6F61;
        }
        .btn-outline-primary:hover {
            background-color: #FF6F61;
            border-color: #FF6F61;
            color: white;
        }
        .btn-outline-danger {
            color: #dc3545;
            border-color: #dc3545;
        }
        .btn-outline-danger:hover {
            background-color: #dc3545;
            border-color: #dc3545;
            color: white;
        }
        .input-updateQTY {
            width: 50px;
            text-align: center;
            margin: 0 5px;
        }
        .updateQTY label {
            font-weight: 500;
            color: #666;
        }
        
        
    </style>
</head>
<body>
    <!-- Success Alert -->
    <c:if test="${not empty successMessage}">
        <div class="message info">
            <div class="alert alert-success" data-time="3000" show-alert role="alert">
                ${successMessage} <span close-alert>X</span>
            </div>
        </div>
    </c:if>

    <!-- Error Alert -->
    <c:if test="${not empty errorMessage}">
        <div class="message info">
            <div class="alert alert-danger" data-time="3000" show-alert role="alert">
                ${errorMessage} <span close-alert>X</span>
            </div>
        </div>
    </c:if>

    <div class="card">
        <div class="row">
            <div class="col-md-8 cart">
                <div class="title">
                    <div class="row">
                        <img class="cart_logo-img" src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="home">
                        <div class="col cart_title">
                            <h4><b>Sửa đơn hàng #${order.orderId}</b></h4>
                        </div>
                        <div class="col align-self-center text-right text-muted">
                            <i class="fas fa-shopping-cart me-2"></i>
                            ${products.size()} món
                        </div>
                    </div>
                </div>
                <div class="cart-list">
                    <c:forEach items="${products}" var="item">
                        <div class="row border-bottom py-4 cart-item">
                            <div class="col-2">
                                <img class="img-fluid" src="${item.key.imageUrls.split(',')[0]}" alt="${item.key.productName}">
                            </div>
                            <div class="col">
                                <div class="row text-muted">${item.key.productId}</div>
                                <div class="row product-name">${item.key.productName}</div>
                                <input type="hidden" class="price" value="${item.key.salePrice}">
                                <fmt:formatNumber value="${item.key.salePrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                            </div>
                            <div class="col updateQTY">
                                <button class="btn-updateQTY" onclick="updateQuantity(this.nextElementSibling, -1)">
                                    <i class="fas fa-minus"></i>
                                </button>
                                <input class="input-updateQTY quantity" type="number" data-product-id="${item.key.productId}" value="${item.value}" min="1" max="${item.key.stock}">
                                <button class="btn-updateQTY" onclick="updateQuantity(this.previousElementSibling, 1)">
                                    <i class="fas fa-plus"></i>
                                </button>
                            </div>
                            <div class="col">
                                <div class="d-flex">
                                    <a class="btn btn-sm btn-light mr-2" href="/autopart/product/detailproduct.htm?productId=${item.key.productId}">
                                        <i class="fas fa-eye me-2"></i>Xem chi tiết
                                    </a>
                                    <form action="/autopart/order/edit/remove-product.htm" method="post" style="display: inline;">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <input type="hidden" name="productId" value="${item.key.productId}">
                                        <button type="submit" class="btn btn-danger btn-sm" onclick="return showDialog(this)">
                                            <i class="fas fa-trash"></i> Xóa
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="cart-actions d-flex justify-content-between align-items-center mt-4">
                    <a class="btn back-btn" href="/autopart/account.htm">
                        <i class="fas fa-arrow-left me-2"></i>Quay lại tài khoản
                    </a>
                </div>
            </div>
            <form class="col-md-4 summary" action="/autopart/order/edit.htm" method="post">
                <div>
                    <h5><b>CHI TIẾT ĐƠN HÀNG</b></h5>
                </div>
                <hr>
                <input type="hidden" name="orderId" value="${order.orderId}">
                <div class="row mb-3">
                    <div class="col" style="padding-left: 0;">Số lượng: ${products.size()}</div>
                    <c:forEach items="${products}" var="item">
                        <input type="hidden" class="form-quantity" id="${item.key.productId}" name="${item.key.productId}" value="${item.value}" data-product-id="${item.key.productId}">
                    </c:forEach>
                </div>
                <div class="form-group">
                    <label for="shippingType">Loại vận chuyển</label>
                    <select id="shippingType" name="shippingType">
                        <option value="" disabled>-- Chọn loại vận chuyển --</option>
                        <option value="20000" <c:if test="${order.shippingType == 'Normal'}">selected</c:if>>Vận chuyển thường - 20.000 ₫</option>
                        <option value="50000" <c:if test="${order.shippingType == 'Express'}">selected</c:if>>Vận chuyển nhanh - 50.000 ₫</option>
                        <option value="15000" <c:if test="${order.shippingType == 'Economy'}">selected</c:if>>Vận chuyển tiết kiệm - 15.000 ₫</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="discountId">Mã khuyến mãi</label>
                    <select id="discountId" name="discountId">
                        <option value="">-- Chọn mã khuyến mãi --</option>
                        <c:forEach var="discount" items="${discounts}">
                            <option value="${discount.discountId}" <c:if test="${order.discountId == discount.discountId}">selected</c:if>>
                                ${discount.discountDesc} - Giảm ${discount.discountAmount}% (Tối thiểu: ${discount.minimumAmount} ₫)
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="row total-section">
                    <div class="col">TỔNG TIỀN</div>
                    <input class="col text-right" id="total" name="totalCost" value="${order.totalCost}" readonly> ₫
                </div>
                <button class="btn btn-primary w-100">LƯU THAY ĐỔI</button>
            </form>
        </div>
    </div>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
    <script>
        // Alert handling
        document.addEventListener('DOMContentLoaded', function() {
            const alerts = document.querySelectorAll('.alert[show-alert]');
            alerts.forEach(alert => {
                const duration = parseInt(alert.getAttribute('data-time')) || 3000;
                setTimeout(() => {
                    alert.classList.add('alert-hidden');
                    setTimeout(() => alert.parentElement.remove(), 300);
                }, duration);
                alert.querySelector('[close-alert]').addEventListener('click', () => {
                    alert.classList.add('alert-hidden');
                    setTimeout(() => alert.parentElement.remove(), 300);
                });
            });

            // Disable delete button if only 1 product
            const deleteButtons = document.querySelectorAll('.btn-danger.btn-sm');
            const quantityInputs = document.querySelectorAll('.quantity');
            if (quantityInputs.length === 1) {
                deleteButtons.forEach(btn => {
                    btn.disabled = true;
                    btn.title = 'Bạn không thể xóa sản phẩm duy nhất trong đơn hàng';
                });
            }
        });

        let totalInput = document.getElementById("total");
        let priceInputs = document.querySelectorAll(".price");
        let quantityInputs = document.querySelectorAll(".quantity");
        let discountSelect = document.getElementById("discountId");
        let shippingSelect = document.getElementById("shippingType");
        window.currentDiscountId = "${order.discountId}";

        const discounts = [
            <c:forEach var="discount" items="${discounts}" varStatus="loop">
                { id: "${discount.discountId}", code: "${discount.discountDesc.replace('\"', '\\\"')}", amount: ${discount.discountAmount}, minOrderValue: ${discount.minimumAmount} }<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
        ];

        function updateDiscountOptions(totalCost) {
            const select = document.getElementById('discountId');
            const currentDiscountId = window.currentDiscountId || '';
            select.innerHTML = '<option value="">-- Chọn mã khuyến mãi --</option>';
            discounts.forEach(discount => {
                const option = document.createElement('option');
                option.value = discount.id;
                option.text = discount.code + " - Giảm " + discount.amount + "% (Tối thiểu: " + discount.minOrderValue.toLocaleString('vi-VN') + " ₫)";
                if (discount.minOrderValue > totalCost) {
                    option.disabled = true;
                }
                if (discount.id === currentDiscountId) {
                    option.selected = true;
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
                const productId = quantityInputs[i].dataset.productId;
                const hiddenInput = document.querySelector(`.form-quantity[data-product-id="${productId}"]`);
                if (hiddenInput) {
                    hiddenInput.value = quantity;
                }
            }
            const selectedDiscountId = discountSelect.value;
            const selectedShippingCost = shippingSelect.value;
            const finalTotal = calculateTotalWithDiscount(baseTotal, selectedDiscountId, selectedShippingCost);
            totalInput.value = finalTotal.toLocaleString('vi-VN');
            updateDiscountOptions(baseTotal);
        }

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

        function updateQuantity(input, delta) {
            let value = parseInt(input.value) + delta;
            if (value < 1) {
                showDialog(input);
            } else if (value > parseInt(input.max)) {
                return;
            } else {
                input.value = value;
                const productId = input.dataset.productId;
                const hiddenInput = document.querySelector(`.form-quantity[data-product-id="${productId}"]`);
                if (hiddenInput) {
                    hiddenInput.value = value;
                }
                updateCost();
            }
        }

        function showDialog(button) {
            const cartItems = document.querySelectorAll('.cart-item');
            if (cartItems.length === 1) {
                alert('Bạn không thể xóa sản phẩm này vì đây là sản phẩm duy nhất trong đơn hàng.');
                return false;
            }
            if (confirm('Bạn có muốn xóa sản phẩm này khỏi đơn hàng?')) {
                button.closest('.cart-item').querySelector('form').submit();
                return true;
            }
            return false;
        }

        updateCost();
    </script>
</body>
</html>