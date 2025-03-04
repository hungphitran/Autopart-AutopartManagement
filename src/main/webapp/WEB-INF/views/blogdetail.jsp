<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết bài viết</title>
        <link href="<c:url value="/resources/css/base.css" />" rel="stylesheet">
    	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
		integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
		crossorigin="anonymous" referrerpolicy="no-referrer" />
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
		crossorigin="anonymous">
    <link rel="stylesheet" href="blog.css">
    <link rel="icon" type="image/x-icon" href="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp">
    
       <style>
        .content {
            margin-top: 150px;
            font-family: Arial, sans-serif;
            color: #333;
            text-align: center;
        }
        h1 {
            color: #0056b3;
        }
        h2 {
            color: #0056b3;
            margin-top: 30px;
        }
        .image-container {
            display: inline-block; /* Important for centering */
        }
        img {
            max-width: 100%;
            height: auto;
        }
    </style>
</head>
<body>

    <header>
    		<jsp:include page="/WEB-INF/mixins/header.jsp" />	
    </header>
    <div class="content">
        <h1>${blog.title}</h1>
        <p>Date: 2024-03-01</p>
        <p>Author: John Doe</p>
        <img src="placeholder.jpg" alt="Blog Image" width="500">
        <h2>${blog.description}</h2>
        <p>This is a short description of the blog post.</p>
        <h2>Content</h2>
        <p>This is the main content of the blog post.</p>
    </div>
</body>
