<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auto Parts Blog</title>
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
    
    <style type="text/css">
    body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding-top: 130px;
    background-color: #f4f4f4;
}

nav ul {
    padding: 0;
    list-style: none;
}

nav ul li {
    display: inline;
    margin: 0 10px;
}

nav ul li a {
    color: #fff;
    text-decoration: none;
}

main {
    padding: 20px;
}

#blog-posts {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
}

.blog-post {
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.blog-post h2 {
    margin-top: 0;
}

.blog-post .date {
    font-style: italic;
    color: #777;
}

.blog-post a {
    display: inline-block;
    margin-top: 10px;
    padding: 8px 15px;
    background-color: #333;
    color: #fff;
    text-decoration: none;
    border-radius: 5px;
}

footer {
    text-align: center;
    padding: 10px 0;
    background-color: #333;
    color: #fff;
}
    </style>
</head>
<body>
    <header>
    		<jsp:include page="/WEB-INF/mixins/header.jsp" />	
    </header>

    <main>
        <section id="blog-posts">
        	<c:forEach items="${blogs}" var="blog">
        	<article class="blog-post">
                <h2>${blog.title }</h2>
                <p class="date">February 27, 2025</p>
                <p>${blog.description}</p>
                <a href="/autopart/blog/detail.htm?id=${blog.blogId }">Xem chi tiết</a>
            </article>
        	
        	</c:forEach>


        </section>
    </main>

    <footer>
        <p>&copy; 2025 Auto Parts Shop</p>
    </footer>

    <script src="blog.js"></script>
</body>
</html>
