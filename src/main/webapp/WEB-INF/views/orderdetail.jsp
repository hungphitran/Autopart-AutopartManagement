<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="<c:url value='/resources/css/base.css' />" rel="stylesheet">
    <style>
        :root {
            --main-color: #ff6f61;
            --main-color-hover: #fb5748;
            --text-color: #333;
            --light-bg: #f5f7fa;
            --border-color: #e9e9e9;
            --shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            --transition: all 0.3s ease;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f4f4;
            color: #333;
            margin: 0;
            padding: 0;
            line-height: 1.6;
        }
        .container {
            display: flex;
            justify-content: space-between;
            margin-top: 150px;
            max-width: 1200px;
            padding: 0 20px;
        }
        .order-details {
            width: 50%;
            margin: 10px;
            padding: 30px;
            background-color: white;
            border-radius: 12px;
            box-shadow: var(--shadow);
            max-width: 70%;
            transition: var(--transition);
        }
        .order-details:hover {
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
        }
        .order-details h2 {
            margin: 0 0 30px 0;
            color: var(--text-color);
            font-weight: 600;
            text-align: center;
            position: relative;
            padding-bottom: 15px;
        }
        .order-details h2:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 80px;
            height: 3px;
            background-color: var(--main-color);
            border-radius: 2px;
        }
        .order-details strong {
            display: inline-block;
            width: 150px;
            margin-left: 0;
            color: var(--main-color);
            font-weight: 600;
        }
        .order-details p {
            margin-bottom: 15px;
            padding: 10px 15px;
            background-color: var(--light-bg);
            border-radius: 8px;
            transition: var(--transition);
            display: flex;
        }
        .order-details p:hover {
            transform: translateX(5px);
            background-color: #edf1f7;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
        }
        .product-list {
            width: 50%;
            list-style: none;
            padding: 0;
            margin-top: 10px;
            max-height: 600px;
            overflow-y: auto;
            scrollbar-width: thin;
            scrollbar-color: var(--main-color) #f1f1f1;
        }
        .product-list::-webkit-scrollbar {
            width: 6px;
        }
        .product-list::-webkit-scrollbar-track {
            background: #f1f1f1;
            border-radius: 10px;
        }
        .product-list::-webkit-scrollbar-thumb {
            background: var(--main-color);
            border-radius: 10px;
        }
        .product-item {
            background-color: white;
            border-radius: 12px;
            box-shadow: var(--shadow);
            margin-bottom: 20px;
            padding: 20px;
            transition: var(--transition);
            position: relative;
            overflow: hidden;
            border-left: 4px solid var(--main-color);
        }
        .product-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.12);
        }
        .product-item strong {
            display: inline-block;
            width: 120px;
            color: #555;
            font-weight: 600;
        }
        .product-item p {
            margin-bottom: 10px;
        }
        .btn {
            padding: 10px 15px;
            margin: 10px 0;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 15px;
            width: 100%;
            font-weight: 500;
            transition: var(--transition);
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }
        .nav-btn {
            background-color: var(--main-color);
            color: white;
            box-shadow: 0 3px 6px rgba(255, 111, 97, 0.3);
        }
        .nav-btn:hover {
            background-color: var(--main-color-hover);
            color: white;
        }
        .edit-btn {
            background-color: #007bff;
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            border-radius: 8px;
            margin-right: 15px;
            display: inline-block;
            text-align: center;
            font-weight: 500;
            box-shadow: 0 3px 6px rgba(0, 123, 255, 0.3);
            transition: var(--transition);
        }
        .edit-btn:hover {
            background-color: #0056b3;
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(0, 123, 255, 0.4);
            color: white;
        }
        .cancel-btn {
            background-color: #dc3545;
            color: white;
            padding: 12px 20px;
            text-decoration: none;
            border-radius: 8px;
            display: inline-block;
            text-align: center;
            font-weight: 500;
            box-shadow: 0 3px 6px rgba(220, 53, 69, 0.3);
            transition: var(--transition);
        }
        .cancel-btn:hover {
            background-color: #c82333;
            transform: translateY(-3px);
            box-shadow: 0 5px 10px rgba(220, 53, 69, 0.4);
            color: white;
        }
        .order-actions {
            margin-top: 30px;
            display: flex;
            justify-content: center;
            padding: 10px 20px;
            background-color: var(--light-bg);
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/mixins/header.jsp" />
    </header>

    <div class="container">
        <div class="order-details">
            <h2>Thông tin đơn hàng</h2>
            <p>
                <strong>Tên khách hàng:</strong> ${customer.fullName}
            </p>
            <p>
                <strong>Số điện thoại:</strong> ${customer.phone}
            </p>
            <p>
                <strong>Địa chỉ:</strong> ${order.shipAddress}
            </p>
            <p>
                <strong>Tổng:</strong> <fmt:formatNumber value="${order.totalCost}" type="currency" currencySymbol="₫" groupingUsed="true"/>
            </p>
            <p>
                <strong>Mã giảm giá:</strong> ${empty order.discountId ? 'Không có' : order.discountId}
            </p>
            <p>
                <strong>Ngày đặt hàng:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
            </p>
            <p>
                <strong>Loại vận chuyển:</strong>
                <c:choose>
                    <c:when test="${order.shippingType == 'Normal'}">Thường</c:when>
                    <c:when test="${order.shippingType == 'Express'}">Nhanh</c:when>
                    <c:when test="${order.shippingType == 'Economy'}">Tiết kiệm</c:when>
                    <c:otherwise>${order.shippingType}</c:otherwise>
                </c:choose>
            </p>
            <p>
                <strong>Trạng thái:</strong>
                <c:choose>
                    <c:when test="${order.status == 'Pending'}">Chờ xác nhận</c:when>
                    <c:when test="${order.status == 'Processing'}">Chờ xử lý</c:when>
                    <c:when test="${order.status == 'Shipping'}">Đang giao</c:when>
                    <c:when test="${order.status == 'Completed'}">Đã giao</c:when>
                    <c:when test="${order.status == 'Cancelled'}">Đã hủy</c:when>
                    <c:otherwise>${order.status}</c:otherwise>
                </c:choose>
            </p>
            <c:if test="${order.status == 'Pending'}">
                <div class="order-actions">
                    <a href="/autopart/order/cancel.htm?orderId=${order.orderId}" class="cancel-btn" onclick="return confirm('Bạn có chắc muốn hủy đơn hàng ${order.orderId}?')">Hủy đơn hàng</a>

                </div>
            </c:if>
        </div>

        <ul class="product-list">
            <c:forEach items="${products}" var="p">
                <li class="product-item">
                    <p>
                        <strong>Tên sản phẩm:</strong> ${p.productName}
                    </p>
                    <p>
                        <strong>Mã sản phẩm:</strong> ${p.productId}
                    </p>
                    <p>
                        <strong>Số lượng:</strong> ${p.amount}
                    </p>
                    <p>
                        <strong>Đơn giá:</strong> <fmt:formatNumber value="${p.unitPrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                    </p>
                    <p>
                        <strong>
                            <a href="/autopart/product/detailproduct.htm?productId=${p.productId}" class="btn nav-btn">Xem sản phẩm</a>
                        </strong>
                    </p>
                </li>
            </c:forEach>

        </ul>
    </div>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>