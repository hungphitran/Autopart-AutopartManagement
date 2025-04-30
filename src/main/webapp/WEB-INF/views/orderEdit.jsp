<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sửa đơn hàng</title>

    <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="<c:url value="/resources/css/cart.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
</head>
<body>
    <!-- Success message -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
            ${successMessage}
        </div>
    </c:if>

    <!-- Error message -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
            ${errorMessage}
        </div>
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
                            <h4><b>Sửa đơn hàng #${order.orderId}</b></h4>
                        </div>
                        <div class="col align-self-center text-right text-muted">${products.size()} món</div>
                    </div>
                </div>
                <div class="cart-list">
                    <c:forEach items="${products}" var="product">
                        <div class="row border-bottom cart-item">
                            <div class="row main align-items-center">
                                <div class="col-2">
                                    <img class="img-fluid"
                                        src="https://html.themability.com/autoelite/assets/images/categories/1.png">
                                </div>
                                <div class="col">
                                    <div class="row text-muted">${product.key.productId}</div>
                                    <div class="row">${product.key.productName}</div>
                                    <input type="hidden" class="price" value="${product.key.salePrice}">
                                    ${product.key.salePrice} ₫
                                </div>
                                <div class="col updateQTY">
                                    <button class="btn-updateQTY"
                                        onclick="updateQuantity(this.nextElementSibling, -1)">-</button>
                                    <input class="input-updateQTY quantity" type="number"
                                        data-product-id="${product.key.productId}" value="${product.value}"
                                        min="0" max="${product.key.stock}">
                                    <button class="btn-updateQTY"
                                        onclick="updateQuantity(this.previousElementSibling, 1)">+</button>
                                </div>
                                <div class="col">
                                    <a href="/autopart/product/detailproduct.htm?productId=${product.key.productId}">Xem chi tiết -></a>
                                    <form action="/autopart/order/remove-product.htm" method="post" style="display: inline;">
                                        <input type="hidden" name="orderId" value="${order.orderId}">
                                        <input type="hidden" name="productId" value="${product.key.productId}">
                                        <button type="submit" class="btn btn-danger btn-sm"
                                            onclick="return confirm('Bạn có chắc muốn xóa sản phẩm ${product.key.productName} khỏi đơn hàng?')">
                                            <i class="fa fa-trash"></i> Xóa
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="back-to-shop">
                    <a href="/autopart/account.htm">
                        <span>←</span> <span class="text-muted">Quay lại tài khoản</span>
                    </a>
                </div>
            </div>
            <form action="/autopart/order/update.htm" method="post" class="col-md-4 summary">
                <div>
                    <h5><b>CHI TIẾT ĐƠN HÀNG</b></h5>
                </div>
                <hr>
                <input type="hidden" name="orderId" value="${order.orderId}">
                <div class="row">
                    <div class="col" style="padding-left: 0;">Số lượng: ${products.size()}</div>
                    <c:forEach items="${products}" var="product">
                        <input type="hidden" id="${product.key.productId}" name="${product.key.productId}" value="${product.value}">
                    </c:forEach>
                </div>
                <div class="row">
                    <input type="text" name="shipAddress" placeholder="Địa chỉ" value="${order.shipAddress}" required="required">
                </div>
                <div>
                    <p>Loại vận chuyển</p>
                    <select name="shippingType" id="shippingType">
                        <option value="" readonly>-- Chọn loại vận chuyển --</option>

                    </select>
                    <p>Mã khuyến mãi</p>
                    <select name="discountId" id="discountId">
                        <option value="">-- Chọn mã khuyến mãi --</option>
                        <c:forEach var="discount" items="${discounts}">
                            <option value="${discount.discountId}" <c:if test="${order.discountId == discount.discountId}">selected</c:if>>
                                ${discount.discountDesc} - Giảm ${discount.discountAmount}% (Tối thiểu: ${discount.minimumAmount} ₫)
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="row" style="border-top: 1px solid rgba(0, 0, 0, .1); padding: 2vh 0;">
                    <div class="col">TỔNG TIỀN</div>
                    <input class="col text-right" id="total" name="totalCost" value="${order.totalCost}" readonly> ₫
                </div>
                <button class="btn">LƯU THAY ĐỔI</button>
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

        let totalInput = document.getElementById("total");
        let priceInputs = document.querySelectorAll(".price");
        let quantityInputs = document.querySelectorAll(".quantity");
        let discountSelect = document.getElementById("discountId");
        let shippingSelect = document.getElementById("shippingType");

        const discounts = [
            <c:forEach var="discount" items="${discounts}" varStatus="loop">
                { id: "${discount.discountId}", code: "${discount.discountDesc.replace('\"', '\\\"')}", amount: ${discount.discountAmount}, minOrderValue: ${discount.minimumAmount} }<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
        ];

        function updateDiscountOptions(totalCost) {
            const select = document.getElementById('discountId');
            select.innerHTML = '<option value="">-- Chọn mã khuyến mãi --</option>';
            discounts.forEach(discount => {
                const option = document.createElement('option');
                option.value = discount.id;
                option.text = discount.code + " - Giảm " + discount.amount + "% (Tối thiểu: " + discount.minOrderValue.toLocaleString('vi-VN') + " ₫)";
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
                // Update hidden input for form submission
                document.getElementById(priceInputs[i].closest('.cart-item').querySelector('.quantity').dataset.productId).value = quantity;
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
            if (value < 0) {
                return; // Prevent negative quantities
            } else if (value > input.max) {
                return; // Respect stock limit
            } else {
                input.value = value;
                updateCost();
            }
        }

        updateCost();
    </script>
</body>
</html>