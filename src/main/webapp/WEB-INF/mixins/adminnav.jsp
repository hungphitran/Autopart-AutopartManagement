<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
      <link href="<c:url value="/resources/css/admincss/nav.css" />" rel="stylesheet">
    <link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
    integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
    crossorigin="anonymous">

</head>
<body>
    <nav>
        <ul>
            <li><a href="/autopart/admin/statistic.htm"><p><i class="fa-solid fa-chart-simple"></i>Thống kê</p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="/autopart/admin/product.htm"><p><i class="fa-solid fa-box"></i>Sản phẩm </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#brands"> <p><i class="fa-solid fa-copyright"></i>Thương hiệu</p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#categories"><p><i class="fa-solid fa-list"></i>Nhóm hàng </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#reports"><p><i class="fa-solid fa-user-group"></i>Nhân sự </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#orders"><p><i class="fa-solid fa-file-invoice"></i>Đơn hàng </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#orders"><p><i class="fa-solid fa-briefcase"></i>Phân quyền </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="/autopart/admin/blog.htm"><p><i class="fa-solid fa-blog"></i>Bài viết </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#about"> <p><i class="fa-solid fa-circle-info"></i>Thông tin</p><i class="fa-solid fa-chevron-left"></i></a></li>
        </ul>
    </nav>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const links = document.querySelectorAll('nav ul li a');
            
            const activeLink = window.location.pathname;
            
            links.forEach(link => {
                link.addEventListener('click', function() {
                    links.forEach(el => {
                        el.classList.remove('active');
                    });
                    this.classList.add('active');
                });
            });
            
            links.forEach(link => {
                if(link.getAttribute('href') === activeLink){
                    link.classList.add('active');
                }
            });
        });
    </script>
</body>
</html>
