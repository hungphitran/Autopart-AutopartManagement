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