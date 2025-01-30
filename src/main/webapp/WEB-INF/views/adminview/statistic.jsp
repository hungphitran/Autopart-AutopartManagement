<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
    body {
        font-family: Arial, sans-serif;
    }
	
	
    .wrapper{
       	display: flex;
    }
   
    .container {
        height: 100vh;
        margin-left:10px;
        padding: 10px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }	
    
    .container div {
        display: block;
    }
</style>

</head>
<body>
    <div class="wrapper">
        <div class="nav">
            <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
        </div>
        
        <div class="container">
            <header>
            	<jsp:include page="/WEB-INF/mixins/adminheader.jsp"/>
            </header>
            <div id="statistics">
            </div>
        </div>
    </div>

</body>
</html>