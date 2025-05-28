<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="header">
    <!-- Alerts -->
    <c:if test="${not empty successMessage}">
        <div class="message info">
            <div class="alert alert-success" data-time="3000" show-alert role="alert">
                ${successMessage} <span close-alert>X</span>
            </div>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="message info">
            <div class="alert alert-danger" data-time="3000" show-alert role="alert">
                ${errorMessage} <span close-alert>X</span>
            </div>
        </div>
    </c:if>

    <div class="header_container header-inner">
        <div class="header_logo">
            <a href="/autopart">
                <img src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="home" class="header_logo-img">
            </a>
        </div>
        <div class="header_search">
            <form action="/autopart/product/search.htm" method="get">
                <input type="text" name="keyword" placeholder="Nhập từ khóa tìm kiếm..." value="${keyword != null ? keyword : ''}" autocomplete="false">
                <span>
                    <button type="submit" class="search-btn">
                        <i class="fa fa-search"></i>
                    </button>
                </span>
            </form>
        </div>
        <div class="header-right">
            <div class="header_account">
                <a class="nav-link active" href="/autopart/login.htm" aria-current="page">
                    <button type="button" class="header-right_account">
                        <div class="header-account_button">
                            <i class="fa-solid fa-circle-user logo-account"></i>
                            <span class="title-account">
                                <c:choose>
                                    <c:when test="${user == null}">Tài khoản</c:when>
                                    <c:otherwise>${userName}</c:otherwise>
                                </c:choose>
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
                        <span class="header__cart-notice">${productInCart != null ? productInCart.size() : 0}</span>
                    </div>
                    <form class="header__cart-list" action="/autopart/order.htm" method="get">
                        <h4 class="header__cart-heading">Sản phẩm đã thêm</h4>
                        <c:choose>
                            <c:when test="${user == null || productInCart == null || productInCart.size() == 0}">
                                <p class="empty-cart-message">Chưa có sản phẩm nào</p>
                                <img src="/resources/img/no_cart.png" alt="Empty Cart" class="empty-cart-img">
                            </c:when>
                            <c:otherwise>
                                <ul class="header__cart-list-item">
                                    <c:forEach items="${productInCart}" var="p">
                                        <li class="header__cart-item">
                                            <input type="checkbox" name="${p.key.productId}" value="${p.value}" id="cart-item-${p.key.productId}">
                                            <img src="${p.key.imageUrls.split(',')[0]}" alt="${p.key.productName}" class="header__cart-img">
                                            <div class="header__cart-item-info">
                                                <div class="header__cart-item-head">
                                                    <h5 class="header__cart-item-name">${p.key.productName}</h5>
                                                    <div class="header__cart-item-wrap">
                                                        <span class="header__cart-item-price">
                                                            <fmt:formatNumber value="${p.key.salePrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                                        </span>
                                                        <span class="header__cart-item-multiply">x</span>
                                                        <span class="header__cart-item-qnt">${p.value}</span>
                                                    </div>
                                                </div>
                                                <div class="header__cart-item-body">
                                                    <span class="header__cart-item-description">
                                                        <a href="/autopart/product/detailproduct.htm?productId=${p.key.productId}">Xem chi tiết</a>
                                                    </span>
                                                    <span class="header__cart-item-remove">
                                                        <a href="/autopart/product/delete.htm?productId=${p.key.productId}">
                                                            <i class="fa-solid fa-trash-can"></i> Xóa
                                                        </a>
                                                    </span>
                                                </div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <button type="submit" class="btn btn--primary header__cart-view-cart">
                                    <i class="fa-solid fa-credit-card"></i> Thanh toán
                                </button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="header_menu">
        <ul class="header_menu-list">
            <li><a class="menu-item" href="/autopart/product/search.htm">Sản phẩm</a></li>
            <li>
                <a class="menu-item" href="#categories">
                    Danh mục
                    <i class="fa fa-angle-down" aria-hidden="true"></i>
                    <div class="group-items items">
                        <c:forEach items="${groups}" var="group">
                            <h4 class="item" data-name="${group.groupName}">${group.groupName}</h4>
                        </c:forEach>
                    </div>
                </a>
            </li>
            <li><a href="/autopart/chat.htm">Liên hệ</a></li>
            <li>
                <a class="menu-item" href="#brand" id="brand">
                    Nhãn hàng
                    <i class="fa fa-angle-down" aria-hidden="true"></i>
                    <div class="brand-items items">
                        <c:forEach items="${brands}" var="brand">
                            <h4 class="item" data-name="${brand.brandName}">${brand.brandName}</h4>
                        </c:forEach>
                    </div>
                </a>
            </li>
            <li><a href="/autopart/blog.htm" target="_blank">Bài viết</a></li>
        </ul>
    </div>
</div>

<script src="/resources/js/dropdown-navigation.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // Handle cart item clicks
    const cartItems = document.querySelectorAll('.header__cart-item');
    cartItems.forEach(item => {
        item.addEventListener('click', function(e) {
            if (e.target.closest('.header__cart-item-remove') || 
                e.target.closest('a[href]') || 
                e.target.tagName === 'INPUT') {
                return;
            }
            const checkbox = this.querySelector('input[type="checkbox"]');
            if (checkbox) {
                checkbox.checked = !checkbox.checked;
            }
        });
        const checkbox = item.querySelector('input[type="checkbox"]');
        if (checkbox) {
            checkbox.addEventListener('click', function(e) {
                e.stopPropagation();
            });
        }
    });

    // Alert handling
    const alerts = document.querySelectorAll('[show-alert]');
    alerts.forEach(alert => {
        const time = parseInt(alert.getAttribute('data-time'));
        const closeBtn = alert.querySelector('[close-alert]');
        setTimeout(() => {
            alert.classList.add('alert-hidden');
            setTimeout(() => {
                if (alert.parentNode) {
                    alert.parentNode.removeChild(alert);
                }
            }, 300);
        }, time);
        if (closeBtn) {
            closeBtn.addEventListener('click', () => {
                alert.classList.add('alert-hidden');
                setTimeout(() => {
                    if (alert.parentNode) {
                        alert.parentNode.removeChild(alert);
                    }
                }, 300);
            });
        }
    });

    // Dropdown navigation for categories
    const groupItems = document.querySelectorAll('.group-items .item');
    groupItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            const groupName = this.getAttribute('data-name');
            window.location.href = '/autopart/product/search.htm?groupName=' + encodeURIComponent(groupName);
        });
    });

    // Dropdown navigation for brands
    const brandItems = document.querySelectorAll('.brand-items .item');
    brandItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();
            const brandName = this.getAttribute('data-name');
            window.location.href = '/autopart/product/search.htm?brandName=' + encodeURIComponent(brandName);
        });
    });
});
<<<<<<< HEAD
</script>
=======

</script>

<script type="text/javascript">
//Handle clicks on cart items
const cartItems = document.querySelectorAll('.header__cart-item');

cartItems.forEach(item => {
  // Make the entire item clickable, not just the checkbox
  item.addEventListener('click', function(e) {
    // Prevent click handling when clicking on specific elements
    if (e.target.closest('.header__cart-item-remove') || 
        e.target.closest('a[href]') || 
        e.target.tagName === 'INPUT') {
      return;
    }
    
    // Toggle the checkbox when clicking on the item
    const checkbox = this.querySelector('input[type="checkbox"]');
    if (checkbox) {
      checkbox.checked = !checkbox.checked;
    }
  });
  
  // Prevent checkbox clicks from triggering the item click handler
  const checkbox = item.querySelector('input[type="checkbox"]');
  if (checkbox) {
    checkbox.addEventListener('click', function(e) {
      e.stopPropagation();
    });
  }
});
</script>

>>>>>>> b80385efbb14ad00fcefce98d334679aa8ef542c
