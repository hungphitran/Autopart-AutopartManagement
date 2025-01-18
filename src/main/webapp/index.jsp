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
	<link href="<c:url value='/resources/css/dashboard.css'/>" rel="stylesheet">
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<div class="header_container header-inner">
				<div class="header_logo">
					<img
						src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp"
						alt="home"
						class="header_logo-img"
					>
				</div>

				<div class="header_search">
				   <form action="/autopart/search.htm" method ="get">
				   		<input 
						type="text" 
						name="keyword"
						placeholder="Tên sản phẩm..."
						autocomplete="false"
						/>
						<span>
							<button type="submit" class="search-btn">
								<i class="fa fa-search"></i>
							</button>
						</span>	
				   </form>

				</div>

				<div class="header-right">
					<div class="header_account">
						<a class="nav-link active" aria-current="page" href="/autopart/login.htm">
							<button type="button" class="header-right_account btn btn-warning">
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
						</a>
					</div>

					<div class="header_cart">
						<a href="/autopart/cart.htm" class="nav-link active" aria-current="page">
							<button type="button" class="header-right_cart btn btn-warning">
								<i class="fa fa-shopping-cart"></i> Cart
							</button>
						</a>
					</div>
				</div>
			</div>

			<div class="header_menu">
				<ul class="header_menu-list">
					<li><a href="#">Trang chủ</a></li>
					<li>
						<a href="#">
							Danh mục
							<i class="fa fa-angle-down" aria-hidden="true"></i>
						</a>
					</li>
					<li>
						<a href="#">
							Sản phẩm
							<i class="fa fa-angle-down" aria-hidden="true"></i>
						</a>
					</li>
					<li>
						<a href="#">
							Liên hệ
							<i class="fa fa-angle-down" aria-hidden="true"></i>
						</a>
					</li>
					<li>
						<a href="#">
							Nhãn hàng
							<i class="fa fa-angle-down" aria-hidden="true"></i>
						</a>
					</li>
					<li><a href="#">Bài viết</a></li>
				</ul>
			</div>
		</div>

		<div class="banner">
			<img
				src="https://html.themability.com/autoelite/assets/images/banners/mainbanner1.png"
				alt="main-img">
		</div>

		<div class="cat-container">
			<!-- show all categories -->
			<div class="categories">
				<div class="categories_title">
					<h1 class="part-name">Danh mục</h1>
				</div>
				<div class="categories_list">
					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/1.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/2.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/3.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/4.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/5.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>

					<div class="categories_list-item">
						<a href="#" class="item-img">
							<img src="https://html.themability.com/autoelite/assets/images/categories/6.png" alt="">
						</a>
						<span class="item-title">Động cơ</span>
					</div>
				</div>
			</div>
			<!-- show products -->
			<div class="list-card">
				<div class="products_title">
					<h1 class="part-name">Sản phẩm nổi bật</h1>
				</div>
				
				<div class="products_list">
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

		<footer class="footer">
			<div class="grid">
			  <div class="grid__row">
				<div class="grid__column-2-4">
				  <h3 class="footer__heading">Chăm sóc khách hàng</h3>
				  <ul class="footer-list">
					<li class="footer-item">
					  <a href="" class="footer-item__link">Trung tâm trợ giúp</a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">Shop</a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">Hướng dẫn mua hàng</a>
					</li>
				  </ul>
				</div>
				<div class="grid__column-2-4">
				  <h3 class="footer__heading">Giới thiệu</h3>
				  <ul class="footer-list">
					<li class="footer-item">
					  <a href="" class="footer-item__link">Giới thiệu</a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">Tuyển dụng</a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">Điều khoản</a>
					</li>
				  </ul>
				</div>
				<div class="grid__column-2-4">
				  <h3 class="footer__heading">Danh mục</h3>
				  <ul class="footer-list">
					<li class="footer-item">
					  <a href="" class="footer-item__link">Động cơ</a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">Động cơ</a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">Động cơ</a>
					</li>
				  </ul>
				</div>
				<div class="grid__column-2-4">
				  <h3 class="footer__heading">Theo dõi</h3>
				  <ul class="footer-list">
					<li class="footer-item">
					  <a href="" class="footer-item__link">
						<i class="footer-item__icon fa-brands fa-facebook"></i>
						Facebook
					  </a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">
						<i class="footer-item__icon fa-brands fa-instagram"></i>
						Instagram
					  </a>
					</li>
					<li class="footer-item">
					  <a href="" class="footer-item__link">
						<i class="footer-item__icon fa-brands fa-linkedin"></i>
						Linkedin
					  </a>
					</li>
				  </ul>
				</div>
				<div class="grid__column-2-4">
				  <h3 class="footer__heading">Vào cửa hàng trên ứng dụng</h3>
				  <div class="footer__download">
					<img src="./resources/img/QR_code.png" alt="Download QR" class="footer__download-qr">
					<div class="footer__download-app">
					  <a class="footer__download-app-link">
						<img src="./resources/img/gg_play.png" alt="GG play" class="footer__download-app-img">
					  </a>
					  <a class="footer__download-app-link">
						<img src="./resources/img/app store.png" alt="App store" class="footer__download-app-img">
					  </a>
					</div>
				  </div>
				</div>
			  </div>
			  
			</div>
			<div class="footer__bottom">
			  <div class="footer__text">
				<p class="footer__text">@ 2024 - No copyright</p>
			  </div>
			  <div class="footer__language-area">
				<ul class="footer__language-area">
				  <li class="footer__language-area-item">Quốc gia & Khu vực: </li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Singapore</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Thái Lan</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Malaysia</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Việt Nam</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Philippines</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Brazil</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Mexico</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Colombia</li>
				  <li class="footer__language-area-item footer__language-area-item--separate">Chile</li>
				  <li class="footer__language-area-item">Đài Loan</li>
				</ul>
			  </div>
			</div>
		</footer>
	</div>




</body>

</html>