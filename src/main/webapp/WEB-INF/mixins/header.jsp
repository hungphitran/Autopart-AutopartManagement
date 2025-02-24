<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="header">
	<div class="header_container header-inner">
		<div class="header_logo">
			<a href="/autopart"> <img
				src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp"
				alt="home" class="header_logo-img">
			</a>
		</div>

		<div class="header_search">
			<form action="/autopart/product/search.htm" method="get">
				<input type="text" name="keyword" placeholder="Tên sản phẩm..."
					autocomplete="false" /> <input type="hidden" name="brand">
				<input type="hidden" name="category"> <span>
					<button type="submit" class="search-btn">
						<i class="fa fa-search"></i>
					</button>
				</span>
			</form>
		</div>

		<div class="header-right">
			<div class="header_account">
				<a class="nav-link active" aria-current="page"
					href="/autopart/login.htm">
					<button type="button" class="header-right_account">
						<div class="header-account_button">
							<i class="fa-solid fa-circle-user logo-account"></i> <span
								class="title-account"> <c:if test="${user==null}">Tài khoản</c:if>
								<c:if test="${user!=null }">${userName}</c:if>
							</span>
						</div>
					</button>
				</a>
			</div>

			<div class="header__cart">
				<div class="header__cart-wrap">
					<div class="header__cart-info">
						<i class="header__cart-icon fa-solid fa-cart-shopping"></i> <span
							class="header__cart-text">Giỏ hàng</span> <span
							class="header__cart-notice">${productInCart.size()}</span>
					</div>

					<!-- No cart: header__cart-list--no-cart-->
					<div class="header__cart-list">
						<!-- <img src="./assets/img/no_cart.png" alt="" class="header__cart-np-cart-img"> -->
						<span class="header__cart-list-no-cart-msg">Chưa có sản
							phẩm</span>

						<h4 class="header__cart-heading">Sản phẩm đã thêm</h4>
						<ul class="header__cart-list-item">
							<!-- Cart item -->
							<c:forEach items="${productInCart}" var="product">
								<li class="header__cart-item"><img
									src="https://img.freepik.com/free-photo/fashion-portrait-young-elegant-woman_1328-2743.jpg?w=1480&t=st=1701357403~exp=1701358003~hmac=435f00eded774f42e172f48f4be677658485206407baa84eaef2058b11f1431f"
									alt="" class="header__cart-img">
									<div class="header__cart-item-info">
										<div class="header__cart-item-head">
											<h5 class="header__cart-item-name">${product.key.productName }</h5>
											<div class="header__cart-item-wrap">
												<span class="header__cart-item-price"></span> <span
													class="header__cart-item-multiply">x</span> <span
													class="header__cart-item-qnt">${product.value }</span>
											</div>
										</div>

										<div class="header__cart-item-body">
											<span class="header__cart-item-description">${product.key.productId }</span> <span class="header__cart-item-remove">Xóa</span>
										</div>
									</div>
									</li>
							</c:forEach>
						</ul>
						<a href="/autopart/product/cart.htm"
							class="btn btn--primary header__cart-view-cart">Xem giỏ hàng</a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="header_menu">
		<ul class="header_menu-list">
			<li><a href="#">Trang chủ</a></li>
			<li><a href="#"> Danh mục <i class="fa fa-angle-down"
					aria-hidden="true"></i>
			</a></li>
			<li><a href="#"> Sản phẩm <i class="fa fa-angle-down"
					aria-hidden="true"></i>
			</a></li>
			<li><a href="#"> Liên hệ <i class="fa fa-angle-down"
					aria-hidden="true"></i>
			</a></li>
			<li><a href="#"> Nhãn hàng <i class="fa fa-angle-down"
					aria-hidden="true"></i>
			</a></li>
			<li><a href="#">Bài viết</a></li>
		</ul>
	</div>
</div>