<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm bài viết</title>

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

        .scrollable-textarea {
            max-height: 500px; 
            overflow-y: auto; 
            resize: vertical; 
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
                            <h6 class="m-0 font-weight-bold text-primary">Thêm Bài Viết</h6>
                        </div>
                        <div class="card-body">
                            <form:form action="${pageContext.request.contextPath}/admin/blog/add.htm" method="post" modelAttribute="blog">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="blogId">Mã bài viết <span class="required-text">*</span></label>
                                            <input type="text" class="form-control" id="blogId" name="blogId" value="${nextBlogId}" readonly>
                                        </div>
                                        <div class="form-group">
                                            <label for="title">Tiêu đề bài viết <span class="required-text">*</span></label>
                                            <input required type="text" class="form-control" id="title" name="title" placeholder="Nhập tiêu đề blog">
                                        </div>
                                        <div class="form-group">
                                            <label for="blogGroupId">Nhóm bài viết <span class="required-text">*</span></label>
                                            <form:select required="true" path="blogGroupId" class="form-control mb-3">
                                                <form:option value="" label="-- Chọn nhóm blog --" disabled="true"/>
                                                <c:forEach items="${blogGroupList}" var="blogGroup">
                                                    <form:option value="${blogGroup.blogGroupId}" label="${blogGroup.groupName}"/>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                        <div class="form-group d-flex">
                                            <label for="status" class="mr-4">Trạng thái hoạt động <span class="required-text">*</span></label>
                                            <div class="custom-control custom-switch ml-4">
                                                <input type="checkbox" class="custom-control-input" name="status" id="status" value="Active" checked>
                                                <label class="custom-control-label" for="status">Hoạt động</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <div class="form-group">
                                            <label for="description">Nội dung bài viết <span class="required-text">*</span></label>
                                            <textarea required class="form-control scrollable-textarea" id="description" name="description" rows="20" placeholder="Nhập nội dung bài viết"></textarea>
                                        </div>
                                        <div class="d-flex justify-content-end">
                                            <button type="submit" class="btn btn-primary">Lưu bài viết</button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
        
                    <!-- Modal Logout -->
                    <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabelLogout"
                        aria-hidden="true">
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