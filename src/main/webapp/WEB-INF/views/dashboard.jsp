<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AutoParts - Cửa hàng phụ tùng ô tô chất lượng cao</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css">
    <link href="<c:url value='/resources/css/dashboard.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/base.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/homepage.css'/>" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp">
</head>
<body>
    <div class="wrapper" id="dashboard">
        <jsp:include page="/WEB-INF/mixins/header.jsp" />

        <div class="banner">
            <img src="https://html.themability.com/autoelite/assets/images/banners/mainbanner1.png" alt="main-img">
        </div>

        <!-- Features Section -->
        <div class="features-section">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 col-6">
                        <div class="feature-box">
                            <i class="fas fa-shipping-fast"></i>
                            <h4>Giao hàng nhanh chóng</h4>
                            <p>Nhận hàng trong 24h</p>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="feature-box">
                            <i class="fas fa-shield-alt"></i>
                            <h4>Bảo hành chính hãng</h4>
                            <p>Đảm bảo chất lượng</p>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="feature-box">
                            <i class="fas fa-headset"></i>
                            <h4>Hỗ trợ 24/7</h4>
                            <p>Luôn sẵn sàng phục vụ</p>
                        </div>
                    </div>
                    <div class="col-md-3 col-6">
                        <div class="feature-box">
                            <i class="fas fa-exchange-alt"></i>
                            <h4>Đổi trả dễ dàng</h4>
                            <p>Trong vòng 7 ngày</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Categories Section -->
        <div class="categories-section" id="categories">
            <div class="container">
                <div class="section-title">
                    <h2>Danh mục sản phẩm</h2>
                    <p>Tìm kiếm phụ tùng theo nhu cầu của bạn</p>
                </div>
                <div class="categories-grid">
                    <c:forEach items="${groups}" var="group" varStatus="loop">
                        <div class="category-card">
                            <div class="category-img">
                                <img src="https://html.themability.com/autoelite/assets/images/categories/${loop.index + 1}.png" alt="${group.groupName}">
                            </div>
                            <div class="category-content">
                                <h3>${group.groupName}</h3>
                                <a class="btn-view" href="/autopart/product/search.htm?groupName=${group.groupName}">Xem thêm <i class="fas fa-arrow-right"></i></a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <!-- Products Section -->
        <div class="products-section" id="products">
            <div class="container">
                <div class="section-title">
                    <h2>Sản phẩm nổi bật</h2>
                    <p>Những sản phẩm được khách hàng tin dùng nhất</p>
                </div>
                <div class="products-grid">
                    <c:forEach items="${products}" var="product">
                        <div class="product-card">
                            <div class="product-badge">Nổi bật</div>
                            <div class="product-img">
                                <a href="/autopart/product/detailproduct.htm?productId=${product.productId}">
                                    <img src="${product.imageUrls.split(',')[0]}" alt="${product.productName}">
                                </a>
                            </div>
                            <div class="product-actions">
                                <a class="action-btn quick-view" href="/autopart/product/detailproduct.htm?productId=${product.productId}" title="Xem nhanh">
                                    <i class="fas fa-eye"></i>
                                </a>
                                <form action="/autopart/product/add.htm" method="post">
                                    <input type="hidden" name="quantity" value="1">
                                    <input type="hidden" name="productId" value="${product.productId}">
                                    <button type="submit" class="action-btn add-to-cart" title="Thêm vào giỏ" data-product-id="${product.productId}">
                                        <i class="fas fa-shopping-cart"></i>
                                    </button>
                                </form>
                            </div>
                            <div class="product-content">
                                <h3 class="product-title">
                                    <a href="/autopart/product/detailproduct.htm?productId=${product.productId}">${product.productName}</a>
                                </h3>
                                <div class="product-price">
                                    <span class="current-price">
                                        <fmt:formatNumber value="${product.salePrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                    </span>
                                    
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="products-more">
                    <a class="btn-view-all" href="/autopart/product/search.htm?keyword=">Xem tất cả sản phẩm <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>
        </div>

        <!-- Bestsellers Section -->
        <div class="bestsellers-section">
            <div class="container">
                <div class="section-title">
                    <h2>Sản phẩm bán chạy</h2>
                    <p>Những sản phẩm được khách hàng mua nhiều nhất</p>
                </div>
                <div class="swiper">
                    <div class="swiper-wrapper">
                        <c:if test="${not empty productOrderMost}">
                            <div class="swiper-slide">
                                <div class="bestseller-highlight">
                                    <div class="bestseller-img">
                                        <img src="${productOrderMost[0].imageUrls.split(',')[0]}" alt="${productOrderMost[0].productName}">
                                    </div>
                                    <div class="bestseller-content">
                                        <span class="bestseller-badge">TOP 1</span>
                                        <h3>${productOrderMost[0].productName}</h3>
                                        <p>${productOrderMost[0].description}</p>
                                        <div class="bestseller-price">
                                            <span class="price">
                                                <fmt:formatNumber value="${productOrderMost[0].salePrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                            </span>
                                        </div>
                                        <a class="btn-buy-now" href="/autopart/product/detailproduct.htm?productId=${productOrderMost[0].productId}">Mua ngay</a>
                                    </div>
                                </div>
                            </div>
                            <c:forEach items="${productOrderMost}" var="item" begin="1">
                                <div class="swiper-slide">
                                    <div class="bestseller-item">
                                        <div class="bestseller-item-img">
                                            <img src="${item.imageUrls.split(',')[0]}" alt="${item.productName}">
                                        </div>
                                        <div class="bestseller-item-info">
                                            <h4 class="product-title">
                                                <a href="/autopart/product/detailproduct.htm?productId=${item.productId}">${item.productName}</a>
                                            </h4>
                                            <div class="bestseller-item-price">
                                                <fmt:formatNumber value="${item.salePrice}" type="currency" currencySymbol="₫" groupingUsed="true"/>
                                            </div>
                                            <div class="bestseller-item-rating">
                                                <i class="fas fa-star"></i>
                                                <i class="fas fa-star"></i>
                                                <i class="fas fa-star"></i>
                                                <i class="fas fa-star"></i>
                                                <i class="fas fa-star"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                </div>
            </div>
        </div>

        <!-- Call to Action Section -->
        <div class="cta-section">
            <div class="container">
                <div class="cta-content">
                    <h2>Bạn cần tư vấn về phụ tùng?</h2>
                    <p>Đội ngũ chuyên gia của chúng tôi luôn sẵn sàng hỗ trợ bạn 24/7</p>
                    <div class="cta-buttons">
                   
                        <a class="btn-contact" href="/autopart/order/checkOrder.htm">
                            <i class="fas fa-magnifying-glass"></i> Tra cứu đơn hàng
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/mixins/footer.jsp" />
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            new Swiper('.bestsellers-section .swiper', {
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                slidesPerView: 1,
                spaceBetween: 20,
                breakpoints: {
                    768: {
                        slidesPerView: 2,
                    },
                    992: {
                        slidesPerView: 3,
                    }
                }
            });
        });
    </script>
</body>
</html>