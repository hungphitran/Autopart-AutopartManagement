<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Danh sách bài viết</title>

	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
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
					<div class="col-lg-12">
						<div class="card mb-4">
							<div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
								<h6 class="m-0 font-weight-bold text-primary">Danh Sách Bài Viết</h6>
								<a href="${pageContext.request.contextPath}/admin/blog/add.htm" class="btn btn-primary">+ Thêm bài viết</a>
							</div>
						  <div class="table-responsive p-3">
							<table class="table align-items-center table-flush" id="dataTable">
								<thead class="thead-light">
									<tr>
										<th>Mã Bài Viết</th>
										<th>Loại Bài Viết</th>
										<th>Tiêu Đề</th>
										<th>Trạng thái</th>
										<th>Hoạt Động</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${blogs}" var="blog">
										<tr class="product-item">
											<td class="align-middle">${blog.blogId}</td>
											<td class="align-middle">${blog.blogGroupName}</td>
											<td class="align-middle">${blog.title}</td>
											<td class="align-middle">
												<c:choose>
												    <c:when test="${blog.status == 'Active'}">
												    	<a href="javascript:void(0);" data-blog-id="${blog.blogId}" data-blog-status="${blog.status}" class="change-status-link">
													        <span class="badge badge-success">Hoạt động</span>											    	
												    	</a>
												    </c:when>
												    <c:otherwise>
												        <a href="javascript:void(0);" data-blog-id="${blog.blogId}" data-blog-status="${blog.status}" class="change-status-link">
													        <span class="badge badge-danger">Ngừng hoạt động</span>											    	
												    	</a>
												    </c:otherwise>
												</c:choose>
											</td>
											<td class="align-middle">
												<a href="javascript:void(0);" data-blog-id="${blog.blogId}" data-toggle="modal" data-target="#DeleteModal" class="btn btn-sm btn-danger delete-btn">Xóa</a>
												<a href="${pageContext.request.contextPath}/admin/blog/edit.htm?blogId=${blog.blogId}" class="btn btn-sm btn-dark">Sửa</a>
												<a href="${pageContext.request.contextPath}/admin/blog/detail.htm?blogId=${blog.blogId}" class="btn btn-sm btn-dark">Xem</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
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
					
					<!-- Modal Delete Item -->
					<div class="modal fade" id="DeleteModal" tabindex="-1" role="dialog" aria-labelledby="DeleteModal" aria-hidden="true">
						<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabelLogout">Xóa bài viết</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							</div>
							<div class="modal-body">
							<p>Bạn chắc chắn muốn xóa bài viết này không?</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
								<a href="#" id="delete-link" class="btn btn-primary">Xóa</a>
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
	
	<script src="<c:url value="/resources/vendor/datatables/jquery.dataTables.min.js" />"></script>
	<script src="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.min.js" />"></script>

	<script>
		$(document).ready(function () {
			$('#dataTable').DataTable({
				"language": {
					"search": "Tìm kiếm",
					"lengthMenu": "Hiển thị _MENU_ dòng",
					"info": "Hiển thị từ _START_ đến _END_ trên tổng số _TOTAL_ dòng",
					"paginate": {
						"previous": "Trước", 
						"next": "Tiếp" 
					}
				}
			}); 
			
			$('#dataTable').on('click', '.change-status-link', function(event) {
			      event.preventDefault(); 

			      var blogId = $(this).data('blog-id');
			      var blogStatus = $(this).data('blog-status');

			      $.ajax({
			        url: '${pageContext.request.contextPath}/admin/blog/changeStatus.htm?blogId=' + blogId,
			        type: 'POST',
			        success: function(response) {
			          var badge = $(event.target).closest('.change-status-link').find('.badge');
			          var link = $(event.target).closest('.change-status-link');
			          
			          if (blogStatus === "Inactive") { 
			              badge.removeClass('badge-danger').addClass('badge-success').text('Hoạt động');
			              link.data('blog-status', 'Active');
			          } else {
			              badge.removeClass('badge-success').addClass('badge-danger').text('Ngừng hoạt động');
			              link.data('blog-status', 'Inactive');
			          }

			        },
			        error: function(error) {
			          console.error("Error changing status:", error);
			          alert("Lỗi khi thay đổi trạng thái.");
			        }
				});
		    });
			
			$('#dataTable').on('click', '.delete-btn', function() {
			    var blogId = $(this).data('blog-id');
			    $('#delete-link').attr('href', '${pageContext.request.contextPath}/admin/blog/delete.htm?blogId=' + blogId);
		  	});
		});
  	</script>
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
