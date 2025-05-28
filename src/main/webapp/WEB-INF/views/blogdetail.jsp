<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết bài viết</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIG Manuscripts/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="<c:url value='/resources/css/base.css' />" rel="stylesheet">
    <link href="<c:url value='/resources/css/blog.css' />" rel="stylesheet">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link rel="icon" type="image/x-icon" href="https://down-bs-vn.img.susercontent.com/vn-11134216-7 мое8-m2ko7nkbfksm02_tn.webp">
    <style>
        .blog-content {
            margin-top: 100px;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            color: #333;
            padding: 40px 20px;
            background-color: #ffffff;
            box-shadow: 0 0 20px rgba(0,0,0,0.05);
            border-radius: 15px;
        }
        .blog-header {
            text-align: center;
            margin-bottom: 50px;
            position: relative;
        }
        .blog-header::after {
            content: '';
            position: absolute;
            bottom: -15px;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 3px;
            background: linear-gradient(to right, #FF6F61, #ff8f85);
        }
        .blog-title {
            font-size: 3rem;
            color: #2c3e50;
            font-weight: 800;
            margin-bottom: 25px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
        }
        .blog-meta {
            color: #666;
            font-size: 1rem;
            margin-bottom: 30px;
            display: flex;
            justify-content: center;
            gap: 30px;
        }
        .blog-meta span {
            padding: 8px 15px;
            background: #f8f9fa;
            border-radius: 20px;
            transition: all 0.3s ease;
        }
        .blog-meta span:hover {
            background: #FF6F61;
            color: white;
            transform: translateY(-2px);
        }
        .blog-meta i {
            margin-right: 8px;
            color: #FF6F61;
        }
        .blog-image {
            width: 100%;
            max-width: 900px;
            margin: 0 auto 50px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
        }
        .blog-image:hover {
            transform: scale(1.02);
        }
        .blog-description {
            font-size: 1.3rem;
            line-height: 1.8;
            color: #34495e;
            background: linear-gradient(145deg, #f8f9fa, #ffffff);
            padding: 30px;
            border-radius: 12px;
            margin-bottom: 40px;
            box-shadow: 5px 5px 15px rgba(0,0,0,0.05);
            border-left: 5px solid #FF6F61;
        }
        .blog-body {
            font-size: 1.2rem;
            line-height: 2;
            color: #2c3e50;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
        }
        .blog-body h2 {
            color: #FF6F61;
            margin-top: 40px;
            margin-bottom: 20px;
            font-weight: 700;
        }
        .blog-body p {
            margin-bottom: 25px;
        }
        .blog-content img {
            max-width: 100%;
            height: auto;
            border-radius: 12px;
            margin: 30px 0;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
    </style>
</head>
<body>
    <header>
        <jsp:include page="/WEB-INF/mixins/header.jsp" />
    </header>

    <div class="container">
        <div class="blog-content" data-aos="fade-up">
            <div class="blog-header">
                <h1 class="blog-title">${blog.title}</h1>
                <div class="blog-meta">
                    <span data-aos="fade-right">
                        <i class="far fa-calendar"></i>
                        <fmt:formatDate value="${blog.updatedAt}" pattern="dd/MM/yyyy"/>
                    </span>
                    <span data-aos="fade-left">
                        <i class="far fa-user"></i>
                        ${author}
                    </span>
                </div>


            <div class="blog-description" data-aos="fade-up">
                ${blog.description}
            </div>

            <div class="blog-body" data-aos="fade-up">
                ${blog.content}
            </div>
        </div>
    </div>

    <footer>
        <jsp:include page="/WEB-INF/mixins/footer.jsp" />
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
    <script>
        AOS.init({
            duration: 800,
            once: true
        });
    </script>
</body>
</html>