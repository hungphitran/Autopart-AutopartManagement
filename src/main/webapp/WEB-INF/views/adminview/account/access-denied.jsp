<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Truy cập bị từ chối</title>
    <link rel="stylesheet" href="/resources/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="/resources/js/script.js" defer></script>
    
    <style>
		.access-denied-container {
		    display: flex;
		    justify-content: center;
		    align-items: center;
		    min-height: 100vh;
		    background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
		    margin: 0;
		    padding: 20px;
		}
		
		.access-denied-content {
		    text-align: center;
		    background: white;
		    padding: 40px;
		    border-radius: 15px;
		    box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
		    max-width: 500px;
		    width: 100%;
		    opacity: 0;
		    transform: translateY(20px);
		    animation: fadeInUp 0.8s ease forwards;
		}
		
		.warning-icon {
		    font-size: 60px;
		    color: #e74c3c;
		    margin-bottom: 20px;
		}
		
		.access-denied-content h1 {
		    font-size: 32px;
		    color: #333;
		    margin-bottom: 10px;
		}
		
		.access-denied-content p {
		    font-size: 16px;
		    color: #666;
		    margin-bottom: 20px;
		}
		
		.btn-back {
		    display: inline-block;
		    padding: 12px 30px;
		    background-color: #007bff;
		    color: white;
		    text-decoration: none;
		    border-radius: 25px;
		    font-size: 16px;
		    transition: background-color 0.3s ease, transform 0.2s ease;
		}
		
		.btn-back:hover {
		    background-color: #0056b3;
		    transform: translateY(-2px);
		}
		
		/* Animation */
		@keyframes fadeInUp {
		    0% {
		        opacity: 0;
		        transform: translateY(20px);
		    }
		    100% {
		        opacity: 1;
		        transform: translateY(0);
		    }
		}
    </style>
</head>
<body>
    <div class="access-denied-container">
        <div class="access-denied-content">
            <i class="fas fa-exclamation-triangle warning-icon"></i>
            <h1>Truy cập bị từ chối</h1>
            <p>Bạn không có quyền truy cập vào trang này.</p>
            <p>Vui lòng kiểm tra lại quyền của bạn hoặc liên hệ với quản trị viên.</p>
            <a href="${preUrl}" class="btn-back">Quay lại</a>
        </div>
    </div>
</body>
</html>