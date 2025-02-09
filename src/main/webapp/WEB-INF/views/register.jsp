<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    
    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous">
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link href="<c:url value="/resources/css/register.css" />" rel="stylesheet">
    <script>
    function checkPasswords() {
        var password = document.getElementsByName('password')[0].value;
        var repassword = document.getElementsByName('repassword')[0].value;
        var message = document.getElementById('password-message');
        
        if (password === '' || repassword === '' || password === repassword) {
            message.style.display = 'none';
        } else {
            message.style.display = 'block';
            message.innerText = 'Passwords do not match!';
        }
    }
</script>
</head>
<body>
    <div class="wrapper">
        <form id="register-login" action="/autopart/register.htm" method="post" data-oc-toggle="ajax">
            <h2>Đăng ký</h2>

            <div class="container_register">
                <div class="container_left">
                    <div class="input-field">
                        <input type="text" name="fullName" required>
                        <label>Nhập Họ và Tên</label>
                    </div>
        
                    <div class="input-field">
                        <input type="text" name="address" required>
                        <label>Nhập địa chỉ</label>
                    </div>
        
                    <div class="input-field">
                        <input type="text" name="phone" required>
                        <label>Nhập số điện thoại</label>
                    </div>
                </div>
    
                <div class="container_right">
                    <div class="input-field">
                        <input type="text" name="email" required>
                        <label>Nhập email</label>
                    </div>
        
                    <div class="input-field">
                        <input type="password" name="password" required oninput="checkPasswords()">
                        <label>Nhập mật khẩu</label>
                    </div>
        
                    <div class="input-field">
                        <input type="password" name="repassword" required oninput="checkPasswords()">
                        <label>Nhập lại mật khẩu</label>
                    </div>
                </div>
            </div>

            <div id="password-message" style="color: red; display: none;"></div>
            <div>${message}</div>

            <button type="submit">Đăng ký</button>

            <div class="register">
                <p>Đã có tài khoản? <a href="/autopart/login.htm">Đăng nhập</a></p>
            </div>
        </form>
    </div>

</body>
</html>
