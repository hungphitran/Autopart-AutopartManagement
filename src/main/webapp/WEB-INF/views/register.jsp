<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
    <style>
        #content{
            padding :20px;
            font-weight: bold;
        }
    </style>
</head>
<body>




	<div class="header">
        <a href="/autopart">
            <img src="https://down-bs-vn.img.susercontent.com/vn-11134216-7ras8-m2ko7nkbfksm02_tn.webp" alt="Home" woidth="100" height="100">
        </a>
    </div>

	<div id="container">
		<div class="row">
            
            <div id="content" class="col">
                <p>If you already have an account with us, please login at the <a href="/autopart/login.htm">login page</a>.</p>
                <form id="form-register" action="autopart/register.htm" method="post" data-oc-toggle="ajax"></form>
                    <fieldset id="account">
                        <legend>Your Personal Details</legend>
                        <div class="row mb-3 required">
                            <label for="input-firstname" class="col-sm-2 col-form-label">First Name</label>
                            <div class="col-sm-10">
                                <input type="text" name="firstname" value="" placeholder="First Name" id="input-firstname" class="form-control">
                                <div id="error-firstname" class="invalid-feedback"></div>
                            </div>
                        </div>
                        <div class="row mb-3 required">
                            <label for="input-lastname" class="col-sm-2 col-form-label">Last Name</label>
                            <div class="col-sm-10">
                                <input type="text" name="lastname" value="" placeholder="Last Name" id="input-lastname" class="form-control">
                                <div id="error-lastname" class="invalid-feedback"></div>
                            </div>
                        </div>
                        <div class="row mb-3 required">
                            <label for="input-email" class="col-sm-2 col-form-label">E-Mail</label>
                            <div class="col-sm-10">
                                <input type="email" name="email" value="" placeholder="E-Mail" id="input-email" class="form-control">
                                <div id="error-email" class="invalid-feedback"></div>
                            </div>
                        </div>
                    </fieldset>
                    <fieldset>
                        <legend>Your Password</legend>
                        <div class="row mb-3 required">
                            <label for="input-password" class="col-sm-2 col-form-label">Password</label>
                            <div class="col-sm-10">
                                <input type="password" name="password" value="" placeholder="Password" id="input-password" class="form-control">
                                <div id="error-password" class="invalid-feedback"></div>
                            </div>
                        </div>
						<div class="row mb-3 required">
                            <label for="input-password" class="col-sm-2 col-form-label">Confirm Password</label>
                            <div class="col-sm-10">
                                <input type="password" name="password" value="" placeholder="Password" id="input-password" class="form-control">
                                <div id="error-password" class="invalid-feedback"></div>
                            </div>
                        </div>
                    </fieldset>
                    
                    <div class="d-inline-block pt-2 pd-2 w-100">
                        <div class="float-end text-right">
                            <button type="submit" class="btn btn-primary">Continue</button>
                        </div>
                    </div>
                  </div><aside id="column-left" class="col-3">
            </aside>
          </div>
	</div>

</body>
</html>