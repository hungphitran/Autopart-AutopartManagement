<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Số Chung</title>

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
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Thông Số Chung</h6>
                            </div>
                            <div class="card-body">
                                <form id="generalSettingsForm" action="${pageContext.request.contextPath}/admin/generalSettings/update.htm" method="POST" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <label for="websiteName">Tên Website</label>
                                        <input type="text" class="form-control" id="websiteName" name="websiteName" value="${generalSettings.websiteName}" readonly required>
                                    </div>
                                    <div class="form-group">
                                        <label for="logo">Logo (Ảnh)</label>
                                        <div class="custom-file">
                                            <input type="file" class="custom-file-input" id="logoFile" name="logoFile" accept="image/*" onchange="previewImage(this)" required disabled>
                                            <label class="custom-file-label" for="logoFile">Chọn ảnh</label>
                                        </div>
                                        <div class="mt-3" id="previewContainer">
                                            <c:if test="${not empty generalSettings.logo}">
                                                <img src="${generalSettings.logo}" alt="Logo hiện tại" style="max-width: 100px; max-height: 100px;">
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="phone">Số Điện Thoại</label>
                                        <input type="text" class="form-control" id="phone" name="phone" value="${generalSettings.phone}" readonly required>
                                    </div>
                                    <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" value="${generalSettings.email}" readonly required>
                                    </div>
                                    <div class="form-group">
                                        <label for="address">Địa Chỉ</label>
                                        <input type="text" class="form-control" id="address" name="address" value="${generalSettings.address}" readonly required>
                                    </div>
                                    <div class="form-group">
                                        <label for="copyright">Bản Quyền</label>
                                        <input type="text" class="form-control" id="copyright" name="copyright" value="${generalSettings.copyright}" readonly required>
                                    </div>
                                    <div class="form-group">
                                        <button type="button" id="toggleEditBtn" class="btn btn-primary">Chỉnh sửa</button>
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

    <script>
        $(document).ready(function() {
            var isEditing = false;

            $('#toggleEditBtn').on('click', function() {
                if (!isEditing) {
                    // Chuyển sang chế độ chỉnh sửa
                    $('input.form-control').removeAttr('readonly');
                    $('#logoFile').removeAttr('disabled');
                    $(this).text('Cập nhật');
                    $(this).removeClass('btn-primary').addClass('btn-success');
                    isEditing = true;
                } else {
                    // Gửi form để cập nhật
                    $('#generalSettingsForm').submit();
                }
            });
        });

        function previewImage(input) {
            const previewContainer = document.getElementById('previewContainer');
            const fileLabel = input.nextElementSibling;
            previewContainer.innerHTML = '';

            if (input.files && input.files[0]) {
                fileLabel.textContent = input.files[0].name;
                const reader = new FileReader();
                reader.onload = function(e) {
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.alt = 'Preview';
                    img.style.maxWidth = '100px';
                    img.style.maxHeight = '100px';
                    previewContainer.appendChild(img);
                };
                reader.readAsDataURL(input.files[0]);
            } else {
                fileLabel.textContent = 'Chọn ảnh';
            }
        }
    </script>
</body>
</html>