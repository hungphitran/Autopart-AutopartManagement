<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
    integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
    crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
    crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: whitesmoke;
        }
        nav {
            background-color: #ff6f61;
            color: white;
            width: 200px;
            height: 100vh;
            padding-top: 20px;
        }
        nav ul {
            list-style: none;
            padding: 0;
        }
        nav ul li {
            margin: 10px 0;
        }

        nav ul li a {
            color: white;
            text-decoration: none;
            font-weight: bold;
            display:flex;
            padding: 10px;
            justify-content: space-between;
        }
        nav ul li a p {
            display: inline;
            margin: 0;
        }
        nav ul li a p *{
            margin-right: 10px;
        }

        nav ul li a i.fa-chevron-left {
            font-size: 20px;
            align-items: center;
            display: flex;
        }
        nav ul li a:hover, nav ul li a.active {
            background-color: #ff8a75;
            border-radius: 4px;
        }
        nav ul li a.active i.fa-chevron-left {
            transform: rotate(180deg);
        }
        
        header {
            background-color: #ff6f61;
            color: white;
            text-align: center;
            width: 100%;
            display: block;
            position: relative;
        }
        header a {
            color: white;
            text-decoration: none;
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            right: 5%;
        }
        header a *{
            margin-right: 10px;
        }
    </style>
</head>
<body>
    <nav>
        <header>
            <img src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="Home" width="100" height="100" style="border-radius: 50%;">
        </header>
        <ul>
            <li><a href="#overview"><p><i class="fa-solid fa-chart-simple"></i>Thống kê</p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#inventory"><p><i class="fa-solid fa-box"></i>Sản phẩm </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#brands"> <p><i class="fa-solid fa-copyright"></i>Thương hiệu</p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#categories"><p><i class="fa-solid fa-list"></i>Nhóm hàng </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#reports"><p><i class="fa-solid fa-user-group"></i>Nhân sự </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#orders"><p><i class="fa-solid fa-file-invoice"></i>Đơn hàng </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#orders"><p><i class="fa-solid fa-briefcase"></i>Phân quyền </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#suppliers"><p><i class="fa-solid fa-blog"></i>Bài viết </p><i class="fa-solid fa-chevron-left"></i></a></li>
            <li><a href="#about"> <p><i class="fa-solid fa-circle-info"></i>Thông tin</p><i class="fa-solid fa-chevron-left"></i></a></li>
        </ul>
    </nav>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const links = document.querySelectorAll('nav ul li a');
            const activeLink = localStorage.getItem('activeLink');
            console.log(activeLink);

            if (activeLink) {
                document.querySelector(`nav ul li a[href="${activeLink}"]`).classList.add('active');
            }

            links.forEach(link => {
                link.addEventListener('click', function() {
                    links.forEach(el => {
                        el.classList.remove('active');
                    });
                    this.classList.add('active');
                    localStorage.setItem('activeLink', this.getAttribute('href'));
                });
            });
        });
    </script>
</body>
</html>