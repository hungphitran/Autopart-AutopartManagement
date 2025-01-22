<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="header">
	<div class="header_container header-inner">
		<div class="header_logo">
			 <a href="/autopart">
				<img
					src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp"
					alt="home"
					class="header_logo-img"
				>					
			 </a>
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
					<button type="button" class="header-right_account">
						<div class="header-account_button">
							<i class="fa-solid fa-circle-user logo-account"></i>
							<span class="title-account">
								<%
								if (session.getAttribute("user") != null) {
								%>
									<%=session.getAttribute("user").toString()%>
								<%
								} else {
								%>
									Tài khoản
								<%
								}
								%>
							</span>
						</div>
					</button>
				</a>
			</div>
			
			<div class="header__cart">
				<div class="header__cart-wrap">
					<div class="header__cart-info">
						<i class="header__cart-icon fa-solid fa-cart-shopping"></i>
						<span class="header__cart-text">Giỏ hàng</span>
						<span class="header__cart-notice">3</span>
					</div>
		
					<!-- No cart: header__cart-list--no-cart-->
					<div class="header__cart-list">
						<!-- <img src="./assets/img/no_cart.png" alt="" class="header__cart-np-cart-img"> -->
						<span class="header__cart-list-no-cart-msg">Chưa có sản phẩm</span>
		
						<h4 class="header__cart-heading">Sản phẩm đã thêm</h4>
						<ul class="header__cart-list-item">
							<!-- Cart item -->
							<li class="header__cart-item">
								<img src="https://img.freepik.com/free-photo/fashion-portrait-young-elegant-woman_1328-2743.jpg?w=1480&t=st=1701357403~exp=1701358003~hmac=435f00eded774f42e172f48f4be677658485206407baa84eaef2058b11f1431f" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt Bộ kem đặc trị vùng mắt Bộ kem đặc trị vùng mắt Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">3.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">5</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/premium-photo/handsome-guy-man-light-jacket-medical-mask-light-background-unaltered_561613-9781.jpg?w=1480" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">1.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">3</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/premium-photo/portrait-happy-senior-man-wearing-blue-glasses-yellow-shirt-talking-phone-yellow-background_561613-22263.jpg" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">2.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">2</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/free-photo/fashion-portrait-young-elegant-woman_1328-2743.jpg?w=1480&t=st=1701357403~exp=1701358003~hmac=435f00eded774f42e172f48f4be677658485206407baa84eaef2058b11f1431f" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">3.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">5</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/premium-photo/handsome-guy-man-light-jacket-medical-mask-light-background-unaltered_561613-9781.jpg?w=1480" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">1.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">3</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/premium-photo/portrait-happy-senior-man-wearing-blue-glasses-yellow-shirt-talking-phone-yellow-background_561613-22263.jpg" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">2.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">2</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/free-photo/fashion-portrait-young-elegant-woman_1328-2743.jpg?w=1480&t=st=1701357403~exp=1701358003~hmac=435f00eded774f42e172f48f4be677658485206407baa84eaef2058b11f1431f" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">3.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">5</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/premium-photo/handsome-guy-man-light-jacket-medical-mask-light-background-unaltered_561613-9781.jpg?w=1480" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">1.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">3</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
			
							<li class="header__cart-item">
								<img src="https://img.freepik.com/premium-photo/portrait-happy-senior-man-wearing-blue-glasses-yellow-shirt-talking-phone-yellow-background_561613-22263.jpg" alt="" class="header__cart-img">
								<div class="header__cart-item-info">
								<div class="header__cart-item-head">
									<h5 class="header__cart-item-name">Bộ kem đặc trị vùng mắt</h5>
									<div class="header__cart-item-wrap">
									<span class="header__cart-item-price">2.500.000đ</span>
									<span class="header__cart-item-multiply">x</span>
									<span class="header__cart-item-qnt">2</span>
									</div>
								</div>
			
								<div class="header__cart-item-body">
									<span class="header__cart-item-description">Phân loại: Bạc</span>
									<span class="header__cart-item-remove">Xóa</span>
								</div>
								</div>
							</li>
						</ul>
	
						<a href="/autopart/cart.htm" class="btn btn--primary header__cart-view-cart">Xem giỏ hàng</a>
					</div>
				</div>
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