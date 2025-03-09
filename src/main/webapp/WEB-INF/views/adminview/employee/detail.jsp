<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Nhân Viên</title>

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
        
        #totalCostlbl {
            text-transform: uppercase;
            font-weight: bold;
            color: #ff6f61;
        }
        
        .order-info-column {
            padding: 15px;
        }
        
        .order-info-column label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        
        .order-info-column span {
            display: block;
            padding: 5px;
            background-color: #f8f9fa;
            border-radius: 4px;
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
                            <h6 class="m-0 font-weight-bold text-primary">Chi Tiết Nhân Viên</h6>
                        </div>
                        <div class="card-body" id="submit-form">
                            <h5>Thông Tin Nhân Viên</h5>
                            <div class="row">
                                <div class="col-lg-6 order-info-column">
                                    <div class="form-group">
                                        <label>Họ và Tên:</label>
                                        <span class="form-control-plaintext">${emp.fullName}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Giới Tính:</label>
                                        <c:choose>
                                        	<c:when test="${emp.gender == 'Male'}">
                                        		<span class="form-control-plaintext">Nam</span>
                                        	</c:when>
                                        	<c:otherwise>
                                        		<span class="form-control-plaintext">Nữ</span>
                                        	</c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="form-group">
                                        <label>Số Điện Thoại:</label>
                                        <span class="form-control-plaintext">${emp.phone}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Email:</label>
                                        <span class="form-control-plaintext">${emp.email}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Số CCCD:</label>
                                        <span class="form-control-plaintext">${emp.citizenId}</span>
                                    </div>
                                </div>
                                <div class="col-lg-6 order-info-column">
                                	<div class="form-group">
                                        <label>Ngày Sinh:</label>
                                        <span class="form-control-plaintext"><fmt:formatDate value="${emp.birthDate}" pattern="dd/MM/yyyy" /></span>
                                    </div>
                                    <div class="form-group">
                                        <label>Ngày Bắt Đầu Làm Việc:</label>
                                        <span class="form-control-plaintext"><fmt:formatDate value="${emp.startDate}" pattern="dd/MM/yyyy" /></span>
                                    </div>
                                    <div class="form-group">
                                        <label>Địa Chỉ:</label>
                                        <span class="form-control-plaintext">${emp.address}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Trình Độ Học Vấn:</label>
                                        <span class="form-control-plaintext">${emp.educationLevel}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Trạng Thái:</label>
                                        <c:choose>
                                        	<c:when test="${emp.status == 'Active'}">
                                        		<span class="form-control-plaintext">Hoạt động</span>
                                        	</c:when>
                                        	<c:otherwise>
                                        		<span class="form-control-plaintext">Ngừng hoạt động</span>
                                        	</c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-end mt-3">
							    <a href="${pageContext.request.contextPath}/admin/employee/edit.htm?empPhone=${emp.phone}" class="btn btn-sm btn-primary">Chỉnh Sửa Thông Tin</a>
							</div>
                        </div>
                    </div>
                    
                    <!-- Modal Logout -->
					<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="logoutModal" aria-hidden="true">
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