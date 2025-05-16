<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Thông Tin Nhân Viên</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">

    <style>
        .selected-products {
            max-height: 350px;
            overflow-y: auto;
        }
        
        .selected-item {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 5px;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .selected-item span {
            flex-grow: 1;
        }
        
        .order-info-column {
            padding: 15px;
        }
        
        .order-info-column label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .form-control {
            border-radius: 4px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
    </style>
</head>
<body id="page-top">
<!-- Success message -->
	<c:if test="${not empty successMessage}">
		<div class="alert alert-success mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${successMessage}</div>
	</c:if>

	<!-- Error message -->
	<c:if test="${not empty errorMessage}">
		<div class="alert alert-danger mt-3" role="alert" style="position: absolute; top: 0; left: 50%; right: 0; z-index: 9999;">
			${errorMessage}</div>
	</c:if>
    <div id="wrapper">
        <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
        
        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

                <div class="container-fluid" id="container-wrapper">
                    <div class="card mb-4">
                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                            <h6 class="m-0 font-weight-bold text-primary">Chỉnh Sửa Thông Tin Nhân Viên</h6>
                        </div>
                        <div class="card-body">
                            <form:form modelAttribute="emp" action="${pageContext.request.contextPath}/admin/employee/edit.htm" method="post">
                                <div class="row">
                                    <!-- Cột 1 -->
                                    <div class="col-lg-6 order-info-column">
                                        <div class="form-group">
                                            <label for="fullName">Họ và Tên <span class="text-danger">*</span></label>
                                            <form:input path="fullName" type="text" class="form-control" id="fullName" name="fullName" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="gender">Giới Tính <span class="text-danger">*</span></label>
                                            <form:select path="gender" class="form-control" id="gender" required="true">
                                                <form:option value="Male" label="Nam"/>
                                                <form:option value="Female" label="Nữ"/>
                                            </form:select>
                                        </div>
                                        <div class="form-group">
                                            <label for="phone">Số Điện Thoại <span class="text-danger">*</span></label>
                                            <form:input path="phone" type="text" class="form-control" id="phone" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="email">Email <span class="text-danger">*</span></label>
                                            <form:input path="email" type="email" class="form-control" id="email" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="citizenId">Số CCCD <span class="text-danger">*</span></label>
                                            <form:input path="citizenId" type="text" class="form-control" id="citizenId" required="true"/>
                                        </div>
                                    </div>
                                    <!-- Cột 2 -->
                                    <div class="col-lg-6 order-info-column">
                                        <div class="form-group">
                                            <label for="birthDate">Ngày Sinh <span class="text-danger">*</span></label>
                                            <form:input path="birthDate" type="date" class="form-control" id="birthDate" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="startDate">Ngày Bắt Đầu Làm Việc <span class="text-danger">*</span></label>
                                            <form:input path="startDate" type="date" class="form-control" id="startDate" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="address">Địa Chỉ <span class="text-danger">*</span></label>
                                            <form:input path="address" type="text" class="form-control" id="address" required="true"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="educationLevel">Trình Độ Học Vấn <span class="text-danger">*</span></label>
                                            <form:input path="educationLevel" type="text" class="form-control" id="educationLevel" required="true"/>
                                        </div>
                                        <div class="form-group d-flex">
                                            <label for="status" class="mr-4">Trạng thái hoạt động <span class="text-danger">*</span></label>
                                            <div class="custom-control custom-switch ml-4">
                                                <c:choose>
												    <c:when test="${emp.status == 'Active'}">
												        <input type="checkbox" class="custom-control-input" name="status" id="status" value="Active" checked>
												    </c:when>
												    <c:otherwise>
												        <input type="checkbox" class="custom-control-input" name="status" id="status" value="Active">
												    </c:otherwise>
												</c:choose>
                                                <label class="custom-control-label" for="status">Hoạt động</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex justify-content-end mt-3">
                                    <button type="submit" class="btn btn-primary">Lưu Chỉnh Sửa</button>
                                    <a href="${pageContext.request.contextPath}/admin/employee.htm" class="btn btn-secondary ml-2">Quay Lại</a>
                                </div>
                            </form:form>
                        </div>
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
	<script type="text/javascript">
document.addEventListener('DOMContentLoaded', function() {
    // Get success and error message elements
    const successMessage = document.querySelector('.alert-success');
    const errorMessage = document.querySelector('.alert-danger');
    
    // If success message exists, hide it after 3 seconds
    if (successMessage) {
        setTimeout(function() {
            successMessage.style.transition = 'opacity 0.5s';
            successMessage.style.opacity = '0';
            setTimeout(function() {
                successMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
    
    // If error message exists, hide it after 3 seconds
    if (errorMessage) {
        setTimeout(function() {
            errorMessage.style.transition = 'opacity 0.5s';
            errorMessage.style.opacity = '0';
            setTimeout(function() {
                errorMessage.style.display = 'none';
            }, 500);
        }, 3000);
    }
});
</script>
</html>