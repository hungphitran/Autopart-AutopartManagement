<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Automobile Product Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="<c:url value='/resources/css/base.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/css/detailProduct.css' />" rel="stylesheet">
    <style type="text/css">:root {
    --main-color: #ff6f61;
    --main-color-hover: #fb5748;
    --text-color: #333;
    --light-bg: #f8f9fa;
    --border-color: #eaeaea;
    --shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    --transition: all 0.3s ease;
}

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: var(--light-bg);
    color: var(--text-color);
    line-height: 1.5;
    min-height: 100vh;
}

.container {
    display: flex;
    flex-wrap: wrap;
    gap: 1.5rem;
    margin: 12% auto 4%;
    max-width: 1000px;
    padding: 0 15px;
    position: relative;
}

/* Product Image Styles */
.col-md-6:first-child {
    flex: 1;
    min-width: 280px;
    position: relative;
}

#main-image {
    width: 100%;
    height: auto;
    border-radius: 6px;
    box-shadow: var(--shadow);
    margin-bottom: 0.8rem;
    transition: var(--transition);
    background: white;
    padding: 4px;
}

#main-image:hover {
    transform: scale(1.01);
    box-shadow: 0 4px 15px rgba(0,0,0,0.12);
}

.list-image {
    display: flex;
    gap: 6px;
    flex-wrap: wrap;
    margin-top: 0.8rem;
    padding: 6px;
    background: rgba(255, 255, 255, 0.8);
    border-radius: 6px;
}

.image-item {
    width: 60px;
    height: 60px;
    object-fit: cover;
    border-radius: 4px;
    cursor: pointer;
    transition: var(--transition);
    border: 2px solid transparent;
    background: white;
    padding: 2px;
}

.image-item:hover {
    transform: translateY(-2px);
    box-shadow: var(--shadow);
}

.image-item.active {
    border-color: var(--main-color);
    box-shadow: 0 2px 8px rgba(255, 111, 97, 0.2);
}

/* Product Details Styles */
.product-details {
    flex: 1;
    min-width: 280px;
    background-color: #ffffff;
    padding: 1.2rem;
    border-radius: 6px;
    box-shadow: var(--shadow);
    position: relative;
    overflow: hidden;
}

.product-details::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 3px;
    background: var(--main-color);
}

.product-details h2 {
    color: var(--main-color);
    font-size: 1.4rem;
    margin-bottom: 0.8rem;
    font-weight: 600;
    position: relative;
    padding-bottom: 0.4rem;
}

.product-details h2::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 40px;
    height: 2px;
    background: var(--main-color);
    border-radius: 1px;
}

.product-details p {
    margin-bottom: 0.6rem;
    font-size: 0.9rem;
    color: var(--text-color);
}

.product-details strong {
    color: var(--text-color);
    font-weight: 600;
    display: inline-block;
    margin-right: 0.3rem;
}

.product-details ul {
    list-style-type: none;
    padding: 0;
    margin: 1rem 0;
}

.product-details ul li {
    background: var(--light-bg);
    margin: 5px 0;
    padding: 8px 12px;
    border-radius: 4px;
    border-left: 3px solid var(--main-color);
    transition: var(--transition);
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 0.9rem;
}

.product-details ul li::before {
    content: '•';
    color: var(--main-color);
    font-size: 1.1rem;
    font-weight: bold;
}

.product-details ul li:hover {
    transform: translateX(3px);
    background: #fff;
    box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}

/* Form Styles */
form .input-group {
    max-width: 160px;
    margin: 1rem 0;
    background: var(--light-bg);
    padding: 4px;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.06);
    display: flex;
    align-items: center;
    transition: var(--transition);
}

form .input-group:focus-within {
    box-shadow: 0 2px 12px rgba(255, 111, 97, 0.15);
    transform: translateY(-1px);
}

.input-group .btn {
    width: 36px;
    height: 36px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 1.1rem;
    font-weight: bold;
    border-radius: 6px;
    transition: var(--transition);
    background: white;
    color: var(--main-color);
    border: 1px solid var(--border-color);
    cursor: pointer;
}

.input-group .btn:hover {
    background: var(--main-color);
    color: white;
    transform: scale(1.05);
}

.input-group .btn:active {
    transform: scale(0.95);
}

.input-group input {
    text-align: center;
    font-size: 1rem;
    height: 36px;
    border: 1px solid var(--border-color);
    background: white;
    border-radius: 6px;
    box-shadow: inset 0 1px 3px rgba(0,0,0,0.04);
    margin: 0 4px;
    width: 60px;
    transition: var(--transition);
}

.input-group input:focus {
    outline: none;
    border-color: var(--main-color);
    box-shadow: 0 0 0 2px rgba(255, 111, 97, 0.1);
}

.input-group input::-webkit-inner-spin-button,
.input-group input::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
}

.input-group input[type=number] {
    -moz-appearance: textfield;
}

.btn-danger {
    padding: 0.5rem 1.2rem;
    font-size: 0.9rem;
    font-weight: 500;
    text-transform: uppercase;
    letter-spacing: 0.3px;
    transition: var(--transition);
    border-radius: 4px;
    background: var(--main-color);
    border: none;
}

.btn-danger:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(255, 111, 97, 0.2);
    background: var(--main-color-hover);
}

#message {
    margin-top: 0.8rem;
    padding: 0.6rem 1rem;
    border-radius: 4px;
    background-color: #e3f2fd;
    color: #1976d2;
    font-weight: 500;
    font-size: 0.85rem;
    box-shadow: 0 1px 4px rgba(25, 118, 210, 0.1);
    animation: fadeIn 0.2s ease;
}

@keyframes fadeIn {
    from { opacity: 0; transform: translateY(-5px); }
    to { opacity: 1; transform: translateY(0); }
}

/* Responsive Design */
@media (max-width: 768px) {
    .container {
        flex-direction: column;
        margin-top: 10%;
        gap: 1.2rem;
    }
    
    .product-details {
        margin-top: 1.2rem;
        padding: 1rem;
    }
    
    .image-item {
        width: 50px;
        height: 50px;
    }

    .product-details h2 {
        font-size: 1.2rem;
    }

    .btn-danger {
        width: 100%;
        margin-top: 0.8rem;
    }
}

/* Stock Status Styles */
.stock-status {
    display: inline-block;
    padding: 0.3rem 0.6rem;
    border-radius: 12px;
    font-weight: 500;
    font-size: 0.85rem;
    margin: 0.6rem 0;
}

.stock-status.in-stock {
    background-color: #d4edda;
    color: #155724;
}

.stock-status.out-of-stock {
    background-color: #f8d7da;
    color: #721c24;
}

footer {
    background: #343a40;
    color: white;
    padding: 1.2rem 0;
    margin-top: 2.5rem;
}</style>
    <style>
        .back-btn {
            display: inline-flex;
            align-items: center;
            gap: 0.75rem;
            padding: 0.75rem 1.25rem;
            background: #fff;
            border: 2px solid #FF6F61;
            border-radius: 8px;
            color: #FF6F61;
            font-weight: 600;
            font-size: 0.95rem;
            transition: all 0.3s ease;
            text-decoration: none;
            position: fixed;
            top: 150px;
            left: 30px;
            z-index: 1000;
        }
        .back-btn:hover {
            background: #FF6F61;
            color: #fff;
            transform: translateX(-5px);
            box-shadow: 0 4px 12px rgba(255, 111, 97, 0.2);
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

    <header>
        <jsp:include page="/WEB-INF/mixins/header.jsp" />
    </header>


    <div class="container">
        <c:choose>
            <c:when test="${not empty imgUrls}">
                <div class="col-md-6">
                    <c:forEach items="${imgUrls}" begin="0" end="0" var="url">
                        <img id="main-image" src="${url}" style="width: 85%; height: 75%;" alt="Car Image" class="img-fluid">
                    </c:forEach>
                    <div class="list-image">
                        <c:forEach items="${imgUrls}" var="url" varStatus="status">
                            <img src="${url}" class="${status.index == 0 ? 'image-item active' : 'image-item'}" onclick="updateMainImg(this)" width="50" height="50" alt="Car Image" class="img-fluid">
                        </c:forEach>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-md-6">
                    <p>Không có hình ảnh cho sản phẩm này</p>
                </div>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${not empty product}">
                <div class="col-md-6 product-details">
                    <h2>${product.productName}</h2>
                    <p><strong>Mã sản phẩm: </strong>${product.productId}</p>
                    <p><strong>Giá bán: </strong><fmt:formatNumber value="${product.salePrice}" type="currency" currencySymbol="₫" groupingUsed="true"/></p>
                    <p><strong>Mô tả: </strong>${product.description}.</p>
                    <ul>
                        <c:if test="${not empty brand}">
                            <li>Hãng: ${brand.brandName}</li>
                        </c:if>
                        <c:if test="${not empty group}">
                            <li>Loại hàng: ${group.groupName}</li>
                        </c:if>
                        <li>Trọng lượng: ${product.weight} kg</li>
                        <li>Số lượng: ${product.stock} ${product.unit}</li>
                    </ul>

                    <c:choose>
                        <c:when test="${product.stock > 0}">
                            <form action="/autopart/product/addproduct.htm" method="post">
                                <div class="input-group mb-3">
                                    <button class="btn btn-danger" type="button" id="button-minus">-</button>
                                    <input type="number" class="form-control" name="quantity" id="quantity" value="1" min="1" max="${product.stock}">
                                    <button class="btn btn-danger" type="button" id="button-plus">+</button>
                                </div>
                                <button class="btn btn-danger" type="submit">Thêm vào giỏ hàng</button>
                                <input type="hidden" name="productId" value="${product.productId}">
                                <c:if test="${not empty message}">
                                    <p id="message" style="font-size: 12px; color: blue;">${message}</p>
                                </c:if>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <ul>
                                <li><p>Hết hàng</p></li>
                            </ul>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col-md-6 product-details">
                    <p>Sản phẩm không tồn tại</p>
                </div>
            </c:otherwise>
        </c:choose>

        <a class="back-btn" href="javascript:history.back()">
            <i class="fas fa-arrow-left me-2"></i>Tiếp tục mua sắm
        </a>
    </div>

    <footer>
        <jsp:include page="/WEB-INF/mixins/footer.jsp" />
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="<c:url value='/resources/js/detailProduct.js' />"></script>
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
        });
    </script>
</body>
</html>