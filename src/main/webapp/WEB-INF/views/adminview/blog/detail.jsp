<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết bài viết</title>

   	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">

    <style>
        body {
            background-color: #f8f9fc;
            font-family: 'Arial', sans-serif;
        }

        .article-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .article-title {
            font-size: 2.5rem;
            font-weight: 700;
            color: #2c3e50;
            margin-bottom: 20px;
            text-align: center;
        }

        .article-category {
            font-size: 1.1rem;
            color: #6c757d;
            margin-bottom: 15px;
            text-align: center;
        }

        .article-content {
            font-size: 1rem;
            line-height: 1.8;
            color: #444;
            white-space: pre-wrap; /* Giữ nguyên định dạng nội dung từ textarea */
        }

        .back-button {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #ff6f61;
            color: white;
            border-radius: 5px;
            text-decoration: none;
        }

        .back-button:hover {
            background-color: #fb5748;
            color: white;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div id="wrapper">
        <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />

        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <jsp:include page="/WEB-INF/mixins/adminheader.jsp" />
				
				<div class="container-fluid" id="container-wrapper">
	                <div class="article-container">
	                    <h1 class="article-title">${blog.title}</h1>
	                    <p class="article-category">Thể loại: ${blog.blogGroup.groupName}</p>
	                    <div class="article-content">${blog.description}</div>
	                    <a href="${pageContext.request.contextPath}/admin/blog.htm" class="back-button">Quay lại danh sách</a>
					</div>
                </div>
            </div>
        </div>
    </div>

    <!-- Scroll to top -->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
    <script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
    <script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
</body>
</html>