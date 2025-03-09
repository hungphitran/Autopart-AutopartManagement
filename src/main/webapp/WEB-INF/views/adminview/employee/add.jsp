<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Nhân Viên</title>

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
    <div id="wrapper">
        <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
        
        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

                <div class="container-fluid" id="container-wrapper">
                    <div class="card mb-4">
                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                            <h6 class="m-0 font-weight-bold text-primary">Thêm Nhân Viên</h6>
                        </div>
                        <div class="card-body">
                            <form:form modelAttribute="emp" action="${pageContext.request.contextPath}/admin/employee/add.htm" method="post">
                                <div class="row">
                                    <!-- Cột 1 -->
                                    <div class="col-lg-6 order-info-column">
                                        <div class="form-group">
                                            <label for="fullName">Họ và Tên <span class="text-danger">*</span></label>
                                            <form:input path="fullName" type="text" class="form-control" id="fullName" required="true"/>
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
                                        <div class="form-group">
                                            <label for="permission">Nhóm Quyền <span class="text-danger">*</span></label>
                                            <form:select path="permission" class="form-control mb-3" required="true">
                                                <form:option value="" label="-- Chọn nhóm quyền --" disabled="true"/>
                                                <form:options items="${roleGroup}" itemValue="roleGroupId" itemLabel="roleGroupName"/>
                                            </form:select>
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
                                                <form:checkbox path="status" class="custom-control-input" id="status" value="Active" checked="true"/>
                                                <label class="custom-control-label" for="status">Hoạt động</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="d-flex justify-content-end mt-3">
                                    <button type="submit" class="btn btn-primary">Thêm Nhân Viên</button>
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
</html>