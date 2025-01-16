 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
	integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
	
	
	<style>

	</style>
</head>
<body>
    <header class="bg-danger text-white py-3">
        <div class="container">
            <a href="/autopart">
                <img src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="Home" width="150" height="150">
            </a>
        </div>
    </header>
	<h1> user: ${user }</h1>

	<div class="container mt-5">
		<div class="row">
			<div class="col-md-4">
				<div class="card">
						<a href="/autopart/logout.htm" class="btn btn-danger mt-3">Logout</a>
						<a href="/change-password" class="btn btn-primary mt-3">Change Password</a> 
					</div>
				</div>
			</div>
		</div>
</body>
</html>

 