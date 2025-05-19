<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Nhóm Quyền</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
</head>
<body id="page-top">
    <div id="wrapper">
        <jsp:include page="/WEB-INF/mixins/adminnav.jsp" />
        
        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <jsp:include page="/WEB-INF/mixins/adminheader.jsp" />

                <div class="container-fluid" id="container-wrapper">
                    <div class="col-lg-12">
                        <div class="card mb-4">
                            <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">Thêm Nhóm Quyền</h6>
                            </div>
                            <div class="card-body">
                                <form id="addRoleGroupForm" action="${pageContext.request.contextPath}/admin/role/add.htm" method="POST">
                                    <div class="form-group">
                                        <label for="addRoleGroupId">Mã Nhóm Quyền</label>
                                        <input type="text" class="form-control" id="addRoleGroupId" name="roleGroupId" value="${nextId}" readonly required>
                                    </div>
                                    <div class="form-group">
                                        <label for="addRoleGroupName">Tên Nhóm Quyền</label>
                                        <input type="text" class="form-control" id="addRoleGroupName" name="roleGroupName" placeholder="Nhập tên nhóm quyền" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="addDescription">Mô Tả</label>
                                        <textarea class="form-control" id="addDescription" name="description" placeholder="Nhập mô tả" rows="3"></textarea>
                                    </div>
                                    <div class="form-group d-flex">
	                                    <label for="status" class="mr-4">Trạng thái hoạt động</label>
	                                    <div class="custom-control custom-switch ml-4">
	                                        <input type="checkbox" class="custom-control-input" name="status" id="status" value="Active" checked>
	                                        <label class="custom-control-label" for="status">Hoạt động</label>
	                                    </div>
                                    </div>
                                    <div class="d-flex justify-content-end">
                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                    </div>
                                </form>
                            </div>
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