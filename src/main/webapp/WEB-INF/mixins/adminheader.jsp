<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand navbar-light bg-navbar topbar mb-4 static-top">
	<button id="sidebarToggleTop" class="btn btn-link rounded-circle mr-3">
		<i class="fa fa-bars"></i>
	</button>
	<div class="navbar-nav ml-auto">
	    <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown"
	    aria-haspopup="true" aria-expanded="false">
	   		 	<img class="img-profile rounded-circle" src="<c:url value="/resources/img/boy.png" />" style="max-width: 60px">
	       		<span class="ml-2 d-none d-lg-inline text-white small">${name}</span>
	    			</a>
	    <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
	         <a class="dropdown-item" href="/autopart/admin/profile.htm">
	           <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
	           Trang cá nhân
	         </a>
	         <a class="dropdown-item" href="/autopart/admin/logout.htm" >
	           <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
	           Đăng xuất
	         </a>
		</div>
	</div>
</nav>