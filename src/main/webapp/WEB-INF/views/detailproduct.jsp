<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Automobile Product Detail</title>
    <link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        header img {
            border-radius: 50%;
        }
        .container {
            display: flex;
            margin-top: 20px;
        }
        .product-details {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .product-details h2 {
            color: #dc3545;
        }
        .product-details ul {
            list-style-type: none;
            padding: 0;
        }
        .product-details ul li {
            background: #e9ecef;
            margin: 5px 0;
            padding: 10px;
            border-radius: 5px;
        }
        footer {
            background: #343a40;
        }
    </style>
</head>
<body>
    <header class="bg-danger text-white py-3">
        <div class="container">
            <a href="/autopart">
                <img src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="Home" width="150" height="150">
            </a>
        </div>
    </header>
    <div class="container my-4">
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


    <footer class="bg-dark text-white py-3">
        <div class="container text-center">
            <p>&copy; 2023 Automobile Company. All rights reserved.</p>
        </div>
    </footer>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
    <script>
        document.getElementById('button-minus').addEventListener('click', function () {
            var quantity = document.getElementById('quantity');
            if (quantity.value > 1) {
                quantity.value = parseInt(quantity.value) - 1;
            }
        });

        document.getElementById('button-plus').addEventListener('click', function () {
            var quantity = document.getElementById('quantity');
            quantity.value = parseInt(quantity.value) + 1;
        });
    </script>
</body>
</html>