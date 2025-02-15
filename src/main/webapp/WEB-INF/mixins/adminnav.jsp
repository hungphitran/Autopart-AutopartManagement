<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="navbar-nav sidebar sidebar-light accordion" id="accordionSidebar">
  <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
    <div class="sidebar-brand-icon">
      <img src="<c:url value="/resources/img/logo.webp" />">
    </div>
    <div class="sidebar-brand-text mx-3">BaoLong Autopart</div>
  </a>
  <hr class="sidebar-divider my-0">
  <li class="nav-item active">
    <a class="nav-link" href="/autopart/admin/statistic.htm">
      <i class="fa-solid fa-chart-column"></i>
      <span>Thống kê</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/autopart/admin/product.htm">
      <i class="fas fa-fw fa-solid fa-gears"></i>
      <span>Quản lý sản phẩm</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="#">
      <i class="fa-solid fa-boxes-stacked"></i>
      <span>Quản lý danh mục</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="#">
      <i class="fa-solid fa-handshake"></i>
      <span>Quản lý nhãn hàng</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="#">
      <i class="fa-solid fa-person"></i>
      <span>Danh sách khách hàng</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="/autopart/admin/blog.htm">
      <i class="fa-solid fa-book-open-reader"></i>
      <span>Quản lý bài viết</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="#">
      <i class="fa-solid fa-ticket"></i>
      <span>Quản lý khuyến mãi</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="#">
      <i class="fa-solid fa-truck"></i>
      <span>Quản lý đơn hàng</span></a>
  </li>
  <li class="nav-item">
    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePage" aria-expanded="true"
      aria-controls="collapsePage">
      <i class="fa-solid fa-file-invoice"></i>
      <span>Cài đặt tài khoản</span>
    </a>
    <div id="collapsePage" class="collapse" aria-labelledby="headingPage" data-parent="#accordionSidebar">
      <div class="bg-white py-2 collapse-inner rounded">
        <a class="collapse-item" href="#">Nhóm quyền</a>
        <a class="collapse-item" href="#">Phân quyền</a>
      </div>
    </div>
  </li>
</ul>

<script>
  document.addEventListener('DOMContentLoaded', function() {
    // Lấy đường dẫn hiện tại
    const currentPath = window.location.pathname;
    
    // Lấy tất cả các thẻ nav-link
    const navLinks = document.querySelectorAll('.nav-item .nav-link');
    
    // Xóa class active từ tất cả các nav-item
    document.querySelectorAll('.nav-item').forEach(item => {
        item.classList.remove('active');
    });
    
    // Duyệt qua từng nav-link để tìm link phù hợp
    navLinks.forEach(link => {
        const href = link.getAttribute('href');
        // Kiểm tra nếu href không phải '#' và đường dẫn hiện tại chứa href
        if (href !== '#' && currentPath.includes(href)) {
            // Thêm class active vào thẻ li cha
            link.closest('.nav-item').classList.add('active');
        }
    });
  });
</script>