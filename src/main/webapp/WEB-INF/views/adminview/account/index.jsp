<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Danh sách tài khoản</title>

	<link href="<c:url value="/resources/img/logo.webp" />" rel="icon">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
	<link href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />" rel="stylesheet" type="text/css">
	<link href="<c:url value="/resources/css/admincss/base.css" />" rel="stylesheet">
	<link href="<c:url value="/resources/vendor/datatables/dataTables.bootstrap4.css" />" rel="stylesheet">
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
								<h6 class="m-0 font-weight-bold text-primary">Danh Sách Tài Khoản</h6>
							</div>
						  <div class="table-responsive p-3">
							<table class="table align-items-center table-flush" id="dataTable">
								<thead class="thead-light">
									<tr>
										<th>Email</th>
										<th>Phân Quyền</th>
										<th>Trạng Thái</th>
										<th>Hoạt Động</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${accounts}" var="acc">
										<tr class="brand-item">
											<td class="align-middle">${acc.email}</td>
											<td class="align-middle">${acc.roleName}</td>
											<td class="align-middle">
												<c:choose>
												    <c:when test="${acc.status == 'Active'}">
												    	<a href="javascript:void(0);" data-acc-phone="${acc.email}" data-acc-status="${acc.status}" class="change-status-link">
													        <span class="badge badge-success">Hoạt động</span>											    	
												    	</a>
												    </c:when>
												    <c:otherwise>
												        <a href="javascript:void(0);" data-acc-phone="${acc.email}" data-acc-status="${acc.status}" class="change-status-link">
													        <span class="badge badge-danger">Ngừng hoạt động</span>											    	
												    	</a>
												    </c:otherwise>
												</c:choose>
											</td>
											<td class="align-middle">
												<a href="javascript:void(0);" data-acc-phone="${acc.email}" data-toggle="modal" data-target="#EditModal" class="btn btn-sm btn-danger edit-btn">Sửa</a>
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
					
					<!-- Modal Edit Item -->
					<div class="modal fade" id="EditModal" tabindex="-1" role="dialog" aria-labelledby="EditModal" aria-hidden="true">
			            <div class="modal-dialog modal-dialog-centered" role="document">
			              <div class="modal-content">
			               	
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

			$('#dataTable').on('click', '.change-status-link',function(event) {
			      event.preventDefault(); 

			      var accPhone = $(this).data('acc-phone');
			      var accStatus = $(this).data('acc-status');

			      $.ajax({
			        url: '${pageContext.request.contextPath}/admin/account/changeStatus.htm',
			        type: 'POST',
			        data: { accPhone: accPhone },
			        success: function(response) {
			          var badge = $(event.target).closest('.change-status-link').find('.badge');
			          var link = $(event.target).closest('.change-status-link');
			          
			          if (accStatus === "Inactive") { 
			              badge.removeClass('badge-danger').addClass('badge-success').text('Hoạt động');
			              link.data('acc-status', 'Active');
			          } else {
			              badge.removeClass('badge-success').addClass('badge-danger').text('Ngừng hoạt động');
			              link.data('acc-status', 'Inactive');
			          }

			        },
			        error: function(error) {
			          console.error("Error changing status:", error);
			          alert("Lỗi khi thay đổi trạng thái.");
			        }
				});
		    });
			
		    $('#dataTable').on('click', '.edit-btn', function() {
		    	var accPhone = $(this).data('acc-phone');

		        // Load the edit modal content
		        $('#EditModal .modal-content').load('${pageContext.request.contextPath}/admin/account/edit.htm?accPhone=' + accPhone, function() {
		            $('#EditModal').modal('show');
		        });
		    });
		});
  	</script>
</body>
