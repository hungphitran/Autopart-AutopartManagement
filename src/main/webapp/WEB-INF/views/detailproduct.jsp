<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Automobile Product Detail</title>
    <link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
		integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
		crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<link href="<c:url value="/resources/css/detailProduct.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/mixins/header.jsp" />
    
    <div class="container">
            <div class="col-md-6">
                <img src="https://html.themability.com/autoelite/assets/images/products/5.jpg "  width="500" height="500" alt="Car Image" class="img-fluid">
            </div>
            <div class="col-md-6 product-details">
                <h2>2023 Sports Car</h2>
                <p><strong>Price:</strong> $50,000</p>
                <p><strong>Description:</strong> This 2023 sports car features a sleek design, powerful engine, and advanced technology. It's perfect for those who love speed and luxury.</p>
                <p><strong>Specifications:</strong></p>
                <ul>
                    <li>Engine: V8</li>
                    <li>Horsepower: 450 HP</li>
                    <li>Top Speed: 200 mph</li>
                    <li>0-60 mph: 3.5 seconds</li>
                </ul>
                <form action="/autopart/addproduct.htm" method="post">
                    <div class="input-group mb-3">
                        <button class="btn btn-danger" type="button" id="button-minus">-</button>
                        <input type="number" class="form-control" name="quantity" id="quantity" value="1" min="1" max >
                        <button class="btn btn-danger" type="button" id="button-plus">+</button>
                        <button class="btn btn-danger"  type="submit">Add to cart</button>
                    </div>
                </form>
            </div>
    </div>

    <jsp:include page="/WEB-INF/mixins/footer.jsp" />
    
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
    <script src="<c:url value="/resources/js/detailProduct.js" />"></script>
</body>
</html>