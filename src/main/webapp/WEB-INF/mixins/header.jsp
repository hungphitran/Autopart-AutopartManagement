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
						<!-- Success message -->
					<c:if test="${not empty successMessage}">
						<div class="alert alert-success mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
							${successMessage}</div>
					</c:if>

					<!-- Error message -->
					<c:if test="${not empty errorMessage}">
						<div class="alert alert-danger mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
							${errorMessage}</div>
					</c:if>
		<div class="header_search">
			<form action="/autopart/product/search.htm" method="get">
				<c:if test="${keyword == null}">
					<input type="text" name="keyword"
						placeholder="Nhập từ khóa tìm kiếm..." />
				</c:if>
				<c:if test="${keyword!=null}">
					<input type="text" name="keyword" autocomplete="false"
						value="${keyword }" />
				</c:if>
				<span>
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
							class="header__cart-text">Giỏ hàng</span>
						<c:if test="${user==null}">
							<span class="header__cart-notice">0</span>
						</c:if>

						<c:if test="${user!=null }">
							<span class="header__cart-notice">${productInCart.size()}</span>
						</c:if>
					</div>

					<!-- No cart: header__cart-list--no-cart-->
					<form class="header__cart-list" action="/autopart/order.htm"
						method="post">
						<!-- <img src="./assets/img/no_cart.png" alt="" class="header__cart-np-cart-img"> -->


						<h4 class="header__cart-heading">Sản phẩm đã thêm</h4>
						<c:if test="${user==null}">
							<p>Đăng nhập để xem giỏ hàng</p>
						</c:if>
						<c:if test="${user!=null }">
							<c:if test="${productInCart.size()==0}">
								<p>Bạn chưa thêm sản phẩm nào</p>
							</c:if>
							<c:if test="${productInCart.size()>0}">
								<ul class="header__cart-list-item">
									<!-- Cart item -->
									<c:forEach items="${productInCart}" var="p">
										<li class="header__cart-item"><input type="checkbox"
											name="${p.key.productId}" value="${p.value}"> <img
											src="https://img.freepik.com/free-photo/fashion-portrait-young-elegant-woman_1328-2743.jpg?w=1480&t=st=1701357403~exp=1701358003~hmac=435f00eded774f42e172f48f4be677658485206407baa84eaef2058b11f1431f"
											alt="" class="header__cart-img">
											<div class="header__cart-item-info">
												<div class="header__cart-item-head">
													<h5 class="header__cart-item-name">${p.key.productName }</h5>
													<div class="header__cart-item-wrap">
														<span class="header__cart-item-price"></span> <span
															class="header__cart-item-multiply">x</span> <span
															class="header__cart-item-qnt">${p.value }</span>
													</div>
												</div>

												<div class="header__cart-item-body">
													<span class="header__cart-item-description"> <a
														href="/autopart/product/detailproduct.htm?productId=${p.key.productId}">${p.key.productId }</a>
													</span> <span class="header__cart-item-remove"><a
														href="/autopart/product/delete.htm?productId=${p.key.productId}">Xóa</a></span>
												</div>
											</div></li>
									</c:forEach>
								</ul>

								<button type="submit"
									class="btn btn--primary header__cart-view-cart">Thanh
									toán</button>
							</c:if>
						</c:if>
					</form>

				</div>
			</div>
		</div>
	</div>

	<div class="header_menu">
		<ul class="header_menu-list">
			<li><a href="/autopart/index.htm">Trang chủ</a></li>
			<li><a href="#categories" class="menu-item"> Danh mục <i
					class="fa fa-angle-down" aria-hidden="true"></i>
					<div class="group-items items">
						<c:forEach items="${groups}" var="group">
							<h4 class="item" data-name="${group.groupName}">${group.groupName}</h4>
						</c:forEach>
					</div>
			</a></li>
			<li><a href="#products"> Sản phẩm </a></li>
			<li><a href="#contact"> Liên hệ </a></li>
			<li><a href="#brand" class="menu-item"> Nhãn hàng <i class="fa fa-angle-down" aria-hidden="true"></i>
    			<div class="brand-items items" >
        			<c:forEach items="${brands}" var="brand">
            			<h4 class="item" data-name="${brand.brandName}"> ${brand.brandName} </h4>
       				</c:forEach>
    			</div>
			</a></li>


			<li><a href="/autopart/blog.htm" target="_blank">Bài viết</a></li>
		</ul>
	</div>

</div>

<script>

document.addEventListener('DOMContentLoaded', function() {
    // Get success and error message elements
    const successMessage = document.querySelector('.alert-success');
    const errorMessage = document.querySelector('.alert-danger');
    
    // If success message exists, hide it after 3 seconds
    if (successMessage) {
        setTimeout(function() {
            successMessage.style.transition = 'opacity 0.5s';
            successMessage.style.opacity = '0';
            setTimeout(function() {
                successMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
    
    // If error message exists, hide it after 3 seconds
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
</script>


	<script type="text/javascript">document.addEventListener('DOMContentLoaded', function() {
		console.log('DOM fully loaded and parsed');
    // Get all menu items
    const groupItems = document.querySelectorAll('.group-items .item');
    const brandItems = document.querySelectorAll('.brand-items .item');
    // Add click event listener to each menu item
    	groupItems.forEach(item => {
        item.addEventListener('click', function() {
            // Get the data-id attribute value
            const groupName = this.getAttribute('data-name');
    		console.log(groupName);

            // Redirect to the desired URL with the group name as a parameter
            window.location.href = '/autopart/product/search.htm?groupName=' + groupName;
        });
    });
    brandItems.forEach(item => {
        item.addEventListener('click', function() {
            // Get the data-id attribute value
            const brandName = this.getAttribute('data-name');
                    		console.log(brandName);
            // Redirect to the desired URL with the group name as a parameter
            window.location.href = '/autopart/product/search.htm?brandName=' + brandName;
        });
    });
});

</script>

