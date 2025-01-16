<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Autopart</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">



<style>
header {
	background-color: white;
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: 1px dashed red;
	padding: 10px;
}

.logo {
	display: inline-block;
}

.search-form {
	display: flex;
	align-items: center;
	border: 1px solid #ccc; /* Đường viền xung quanh */
	overflow: hidden; /* Ẩn đi phần thừa */
	font-size: 20px;
}

.search-form input[type="text"] {
	flex: 1; /* Chiếm hết không gian còn lại */
	border: none; /* Không có đường viền riêng */
}

.search-form input[type="text"]:focus {
	outline: none;
}

.search-form button {
	background-color: red;
	color: white;
	border: none;
	cursor: pointer;
}

.btn-group {
	list-style-type: none;
}

.btn-item * {
	font-size: 20px;
}

.header-menu {
	display: flex;
	justify-content: center;
	align-items: center;
}

.header-menu * {
	background-color: white;
}

.header-menu a {
	font-weight: bold;
}

.header-menu a:hover {
	color:red;
}

h1.part-name {
	display: flex;
	border-bottom: 5px solid black;
}

.card {
	border: 0px;
	display: inline-block;
	padding: 20px;
}

.footer {
	background-color: red;
	color: white;
	padding: 10px;
}

.footer-content {
	display: inline-block;
	justify-content: space-between;
}

.social-media {
	display: flex;
	justify-content: space-between;
	font-size: 30px;
}

.social-media a {
	display: inline-block;
	color: white;
	border-radius: 50%;
}

.social-media a:hover {
	color: black;
}
</style>
</head>
<body>
	<div class="wrapper">

		<header class="header">

			<h1 class="logo">
				<img
					src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp"
					alt="home" width="100" height="100">
			</h1>

			<div class="search-form">
				<form action="/" method="get">
					<input type="text" placeholder="Search...">
					<button type="submit">
						<i class="fa fa-search"></i>
					</button>
				</form>
			</div>

			<ul class="btn-group">
				<li class="btn-item"><a class="nav-link active"
					aria-current="page" href="/autopart/login.htm">
						<button type="button" class="btn btn-warning">
							<i class="fa-solid fa-circle-user"></i>
								<%
								if (session.getAttribute("user") != null) {
								%>
									<%=session.getAttribute("user").toString()%>
								<%
								} else {
								%>
									Account
								<%
								}
								%>
						</button>
				</a></li>

				<li class="btn-item"><a href="#" class="nav-link active"
					aria-current="page" href="/autopart/login.htm">
						<button type="button" class="btn btn-warning">
							<i class="fa fa-shopping-cart"></i> Cart
						</button>
				</a> </a></li>
			</ul>
		</header>

		<div class="header-menu">
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">
					<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
						<div class="navbar-nav">
							<a class="nav-link">Home</a> <a class="nav-link">Brands<i
								class="fa-solid fa-angle-down"></i></a> <a class="nav-link">Categories<i
								class="fa-solid fa-angle-down"></i></a> <a class="nav-link">Features</a>
							<a class="nav-link">Pricing</a> <a class="nav-link">Blog</a>

						</div>
					</div>
				</div>
			</nav>
		</div>
		<br>

		<div class="banner">
			<img
				src="https://html.themability.com/autoelite/assets/images/banners/mainbanner1.png"
				alt="">
		</div>

		<div class="container">

			<!-- show all categories -->
			<div class="categories">
				<h1 class="part-name">Categories</h1>
				<div class="list-category">
					<img src="" alt="">
					<p class="category-name"></p>
				</div>
			</div>
			<!-- show products -->
			<div class="list-card">
				<h1 class="part-name">Products</h1>
				<div class="card" style="width: 18rem;">
					<div class="card" style="width: 18rem;">
						<a href="/autopart/detailproduct.htm"><img
							src="https://cf.shopee.vn/file/sg-11134201-7rfha-m4fpfp46y804d8"
							class="card-img-top" alt="..."></a>

						<div class="card-body">
							<h5 class="card-title">Card title</h5>
							<p class="card-text">Some quick example text to build on the
								card title and make up the bulk of the card's content.</p>
							<p>$$$$$$$$$$$$$$</p>
						</div>
					</div>
				</div>

				<div class="card" style="width: 18rem;">
					<div class="card" style="width: 18rem;">
						<a href="/autopart/detailproduct.htm"><img
							src="https://html.themability.com/autoelite/assets/images/products/13.jpg"
							class="card-img-top" alt="..."></a>

						<div class="card-body">
							<h5 class="card-title">Card title</h5>
							<p class="card-text">Some quick example text to build on the
								card title and make up the bulk of the card's content.</p>
							<p>$$$$$$$$$$$$$$</p>
						</div>
					</div>
				</div>

				<div class="card" style="width: 18rem;">
					<div class="card" style="width: 18rem;">
						<a href="/autopart/detailproduct.htm"><img
							src="https://html.themability.com/autoelite/assets/images/products/9.jpg"
							class="card-img-top" alt="..."></a>

						<div class="card-body">
							<h5 class="card-title">Card title</h5>
							<p class="card-text">Some quick example text to build on the
								card title and make up the bulk of the card's content.</p>
							<p>$$$$$$$$$$$$$$</p>
						</div>
					</div>
				</div>

				<div class="card" style="width: 18rem;">
					<div class="card" style="width: 18rem;">
						<a href="/autopart/detailproduct.htm"><img
							src="https://html.themability.com/autoelite/assets/images/products/4.jpg"
							class="card-img-top" alt="..."></a>

						<div class="card-body">
							<h5 class="card-title">Card title</h5>
							<p class="card-text">Some quick example text to build on the
								card title and make up the bulk of the card's content.</p>
							<p>$$$$$$$$$$$$$$</p>
						</div>
					</div>
				</div>

				<div class="card" style="width: 18rem;">
					<div class="card" style="width: 18rem;">
						<a href="/autopart/detailproduct.htm"><img
							src="https://cf.shopee.vn/file/sg-11134201-7rfha-m4fpfp46y804d8"
							class="card-img-top" alt="..."></a>

						<div class="card-body">
							<h5 class="card-title">Card title</h5>
							<p class="card-text">Some quick example text to build on the
								card title and make up the bulk of the card's content.</p>
							<p>$$$$$$$$$$$$$$</p>
						</div>
					</div>
				</div>


				<div class="card" style="width: 18rem;">
					<div class="card" style="width: 18rem;">
						<a href="/autopart/detailproduct.htm"><img
							src="https://cf.shopee.vn/file/sg-11134201-7rfha-m4fpfp46y804d8"
							class="card-img-top" alt="..."></a>

						<div class="card-body">
							<h5 class="card-title">Card title</h5>
							<p class="card-text">Some quick example text to build on the
								card title and make up the bulk of the card's content.</p>
							<p>$$$$$$$$$$$$$$</p>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="footer">
			<h1>Autopart</h1>
			<div class="footer-content">
				<div class="info">
					<h2>Information</h2>
					<ul>
						<li>Phone: ádasdasda</li>
						<li>Phone: ádasdasda</li>
						<li>Phone: ádasdasda</li>
						<li>Phone: ádasdasda</li>
						<li>Phone: ádasdasda</li>
					</ul>
					<div class="social-media">
						<a href="#"> <i class="fa-brands fa-facebook-f"> </i>
						</a> <a href="#"> <i class="fa-brands fa-instagram"></i></a> <a
							href="#"> <i class="fa-brands fa-whatsapp"></i></a> <a href="#">
							<i class="fa-brands fa-pinterest"></i>
						</a>
					</div>
				</div>

				<div class="contact">
					<form action="/" method="post" class="contact-form">
						<input type="text" name="email" value=""
							placeholder="E-Mail Address" id="input-email"
							class="form-control">
						<button type="submit" class="btn btn-error">Subscribe</button>
					</form>
				</div>
			</div>
		</div>
	</div>




</body>

</html>