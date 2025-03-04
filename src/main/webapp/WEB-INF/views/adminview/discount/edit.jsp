<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Khuyến Mãi</title>

    <link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">

    <style>
        .custom-file {
            position: relative;
            display: flex;
            align-items: center;
        }
        
        .custom-file-input {
            position: absolute;
            width: 100%;
            height: 100%;
            opacity: 0;
            cursor: pointer;
        }
        
        .custom-file-label {
            flex-grow: 1;
            padding: 0.375rem 0.75rem;
            border: 1px solid #ced4da;
            border-radius: 0.25rem;
            background-color: #fff;
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
                            <h6 class="m-0 font-weight-bold text-primary">Chỉnh Sửa Khuyến Mãi</h6>
                        </div>
                        <div class="card-body">
                            <form:form action="${pageContext.request.contextPath}/admin/discount/edit.htm" method="post" modelAttribute="discount">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="discountId">Mã khuyến mãi <span class="required-text">*</span></label>
                                            <form:input required="true" type="text" class="form-control" id="discountId" path="discountId" readonly="true" placeholder="Mã khuyến mãi"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="discountDesc">Mô tả khuyến mãi <span class="required-text">*</span></label>
                                            <form:input required="true" type="text" class="form-control" id="discountDesc" path="discountDesc" placeholder="Nhập mô tả khuyến mãi"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="discountAmount">Số tiền giảm giá <span class="required-text">*</span></label>
                                            <form:input required="true" type="number" class="form-control" id="discountAmount" path="discountAmount" placeholder="Nhập số tiền giảm (VND)"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="minimumAmount">Số tiền tối thiểu <span class="required-text">*</span></label>
                                            <form:input required="true" type="number" step="0.01" class="form-control" id="minimumAmount" path="minimumAmount" placeholder="Nhập số tiền tối thiểu (VND)"/>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="usageLimit">Giới hạn sử dụng <span class="required-text">*</span></label>
                                            <form:input required="true" type="number" class="form-control" id="usageLimit" path="usageLimit" placeholder="Nhập số lần sử dụng tối đa"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="applyStartDate">Ngày bắt đầu <span class="required-text">*</span></label>
                                            <form:input required="true" type="date" class="form-control" id="applyStartDate" path="applyStartDate"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="applyEndDate">Ngày kết thúc <span class="required-text">*</span></label>
                                            <form:input required="true" type="date" class="form-control" id="applyEndDate" path="applyEndDate"/>
                                        </div>
                                        <div class="form-group d-flex">
                                            <label for="status" class="mr-4">Trạng thái hoạt động <span class="required-text">*</span></label>
                                            <div class="custom-control custom-switch ml-4">
                                                <form:checkbox path="status" class="custom-control-input" id="status" value="Active"/>
                                                <label class="custom-control-label" for="status">Hoạt động</label>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-primary">Cập nhật khuyến mãi</button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
        
                    <!-- Modal Logout -->
                    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabelLogout" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabelLogout">Đăng xuất</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <p>Bạn có muốn đăng xuất không?</p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
                                    <a href="login.html" class="btn btn-primary">Đăng xuất</a>
                                </div>
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