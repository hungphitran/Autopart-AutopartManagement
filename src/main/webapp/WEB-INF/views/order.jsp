<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <!-- <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script> -->
    
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
    
</head>
<body>
    <!-- Success Alert -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success message info" role="alert">
            ${successMessage}
            <span close-alert>&times;</span>
        </div>
    </c:if>

    <!-- Error Alert -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger message info" role="alert">
            ${errorMessage}
            <span close-alert>&times;</span>
        </div>
    </c:if>

    <div class="card">
        <div class="row">
            <div class="col-md-8 cart">
                <div class="title">
                    <div class="row">
                        <img class="cart_logo-img" src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="home">
                        <div class="col cart_title">
                            <h4><b>Giỏ hàng</b></h4>
                        </div>
                        <div class="col align-self-center text-right text-muted">
                            <i class="fas fa-shopping-cart me-2"></i>
                            <span>${products.size()} món</span>
                        </div>
                    </div>
                </div>
                <div class="cart-list">
                    <c:choose>
                        <c:when test="${not empty products and products.size() > 0}">
                            <c:forEach items="${products}" var="product">
                                <div class="row border-bottom">
                                    <div class="row main align-items-center">
                                        <div class="col-2">
                                            <img class="img-fluid" src="${product.key.imageUrls.split(',')[0]}">
                                        </div>
                                        <div class="col">
                                            <div class="row text-muted">${product.key.productId}</div>
                                            <div class="row">${product.key.productName}</div>
                                            <input type="hidden" class="price" value="${product.key.salePrice}">
                                            <fmt:formatNumber value="${product.key.salePrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                        </div>
                                        <div class="col updateQTY">
                                            <button class="btn-updateQTY" onclick="updateQuantity(this.nextElementSibling, -1)">
                                                <i class="fas fa-minus"></i>
                                            </button>
                                            <input class="input-updateQTY quantity" type="number" data-product-id="${product.key.productId}" value="${product.value}" min="1" max="${product.key.stock}">
                                            <button class="btn-updateQTY" onclick="updateQuantity(this.previousElementSibling, 1)">
                                                <i class="fas fa-plus"></i>
                                            </button>
                                        </div>
                                        <div class="col">
                                            <a class="btn btn-sm btn-light" href="/autopart/product/detailproduct.htm?productId=${product.key.productId}">
                                                <i class="fas fa-eye me-2"></i>Xem chi tiết
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p>Giỏ hàng trống</p>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="cart-actions d-flex justify-content-between align-items-center mt-4">
                    <a class="btn back-btn" href="/autopart">
                        <i class="fas fa-arrow-left me-2"></i>Tiếp tục mua sắm
                    </a>
                </div>
            </div>
            <form class="col-md-4 summary" action="/autopart/order/create.htm" method="post">
                <div>
                    <h5><b>HÓA ĐƠN</b></h5>
                </div>
                <hr>
                <div class="row mb-3">
                    <div class="col" style="padding-left: 0;">Số lượng: ${products.size()}</div>
                    <c:forEach items="${products}" var="product">
                        <input type="hidden" class="form-quantity" name="${product.key.productId}" value="${product.value}" data-product-id="${product.key.productId}">
                    </c:forEach>
                </div>
                <div class="form-group">
                    <label for="customerName">Tên</label>
                    <input id="customerName" type="text" name="customerName" placeholder="Tên" required value="${user != null && user.fullName != null ? user.fullName : ''}" oninvalid="this.setCustomValidity('Vui lòng nhập tên')" oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Số điện thoại</label>
                    <input id="phoneNumber" type="tel" name="phoneNumber" placeholder="Số điện thoại" required value="${user != null && user.phone != null ? user.phone : ''}" oninvalid="this.setCustomValidity('Vui lòng nhập số điện thoại')" oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" type="email" name="email" placeholder="Email" required value="${user != null && user.email != null ? user.email : ''}" oninvalid="this.setCustomValidity('Vui lòng nhập email')" oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label for="shipAddress">Địa chỉ giao hàng</label>
                    <input id="shipAddress" type="text" name="shipAddress" placeholder="Địa chỉ" required value="${user != null && user.address != null ? user.address : ''}" oninvalid="this.setCustomValidity('Vui lòng nhập địa chỉ')" oninput="this.setCustomValidity('')">
                </div>
                <div class="form-group">
                    <label for="shippingType">Loại vận chuyển</label>
                    <select id="shippingType" name="shippingType">
                        <option value="" disabled selected>-- Chọn loại vận chuyển --</option>
                        <option value="20000">Vận chuyển thường - 20.000 ₫</option>
                        <option value="50000">Vận chuyển nhanh - 50.000 ₫</option>
                        <option value="15000">Vận chuyển tiết kiệm - 15.000 ₫</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="discountId">Mã khuyến mãi</label>
                    <select id="discountId" name="discountId"></select>
                </div>
                <div class="row total-section" style="border-top: 1px solid rgba(0, 0, 0, .1); padding: 2vh 0;">
                    <div class="col">TỔNG TIỀN</div>
                    <input class="col text-right" id="total" name="totalCost" value="" readonly> ₫
                </div>
                <div class="row mt-3">
                    <div class="col">
                        <button class="btn order-btn w-100">ĐẶT HÀNG</button>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <script type="text/javascript">
        document.addEventListener('DOMContentLoaded', function() {
            const successMessage = document.querySelector('.alert-success');
            const errorMessage = document.querySelector('.alert-danger');
            if (successMessage) {
                setTimeout(function() {
                    successMessage.style.transition = 'opacity 0.5s';
                    successMessage.style.opacity = '0';
                    setTimeout(function() {
                        successMessage.style.display = 'none';
                    }, 500);
                }, 3000);
            }
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

        const discounts = [
            <c:forEach var="discount" items="${discounts}" varStatus="loop">
                { id: "${discount.discountId}", code: "${discount.discountDesc.replace('\"', '\\\"')}", amount: ${discount.discountAmount}, minOrderValue: ${discount.minimumAmount} }${loop.last ? '' : ','}
            </c:forEach>
        ];

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

        updateCost();

        function updateQuantity(input, delta) {
            let value = parseInt(input.value) + delta;
            if (value < 1) {
                if (confirm("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng?")) {
                    input.closest('.row.border-bottom').remove();
                    updateCost();
                }
            } else if (value > parseInt(input.max)) {
                alert("Số lượng không thể vượt quá số lượng trong kho.");
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
    </script>
</body>
</html>	