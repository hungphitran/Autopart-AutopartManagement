<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Danh sách nhãn hàng</title>

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
								<h6 class="m-0 font-weight-bold text-primary">Danh Sách Nhãn Hàng</h6>
								<a href="${pageContext.request.contextPath}/admin/brand/add.htm" class="btn btn-primary">+ Thêm nhãn hàng</a>
							</div>
						  <div class="table-responsive p-3">
							<table class="table align-items-center table-flush" id="dataTable">
								<thead class="thead-light">
									<tr>
										<th>Tên Nhãn Hàng</th>
										<th>Trạng thái</th>
										<th>Hoạt Động</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${brands}" var="brand">
										<tr class="brand-item">
											<td class="align-middle">${brand.brandName}</td>
											<td class="align-middle">
												<c:choose>
												    <c:when test="${brand.status == 'Active'}">
												    	<a href="javascript:void(0);" data-brand-name="${brand.brandName}" data-brand-status="${brand.status}" class="change-status-link">
													        <span class="badge badge-success">Hoạt động</span>											    	
												    	</a>
												    </c:when>
												    <c:otherwise>
												        <a href="javascript:void(0);" data-brand-name="${brand.brandName}" data-brand-status="${brand.status}" class="change-status-link">
													        <span class="badge badge-danger">Ngừng hoạt động</span>											    	
												    	</a>
												    </c:otherwise>
												</c:choose>
											</td>
											<td class="align-middle">
												<a href="javascript:void(0);" data-brand-name="${brand.brandName}" data-toggle="modal" data-target="#DeleteModal" class="btn btn-sm btn-danger delete-btn">Xóa</a>
												<a href="javascript:void(0);" data-brand-name="${brand.brandName}" data-toggle="modal" data-target="#DetailModal" class="btn btn-sm btn-dark detail-btn">Chi tiết</a>
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
							<h5 class="modal-title" id="exampleModalLabelLogout">Xóa nhãn hàng</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							</div>
							<div class="modal-body">
							<p>Bạn chắc chắn muốn xóa nhãn hàng này không?</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-outline-primary" data-dismiss="modal">Không</button>
								<a href="#" id="delete-link" class="btn btn-primary">Xóa</a>
							</div>
						</div>
						</div>
					</div>
	
					<!-- Modal Detail Item -->
					<div class="modal fade" id="DetailModal" tabindex="-1" role="dialog" aria-labelledby="DetailModal" aria-hidden="true">
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
			
			$('.change-status-link').click(function(event) {
			      event.preventDefault(); 

			      var brandName = $(this).data('brand-name');
			      var brandStatus = $(this).data('brand-status');

			      $.ajax({
			        url: '${pageContext.request.contextPath}/admin/brand/changeStatus.htm?brandName=' + brandName,
			        type: 'POST',
			        success: function(response) {
			          var badge = $(event.target).closest('.change-status-link').find('.badge');
			          var link = $(event.target).closest('.change-status-link');
			          
			          if (brandStatus === "Inactive") { 
			              badge.removeClass('badge-danger').addClass('badge-success').text('Hoạt động');
			              link.data('brand-status', 'Active');
			          } else {
			              badge.removeClass('badge-success').addClass('badge-danger').text('Ngừng hoạt động');
			              link.data('brand-status', 'Inactive');
			          }

			        },
			        error: function(error) {
			          console.error("Error changing status:", error);
			          alert("Lỗi khi thay đổi trạng thái.");
			        }
				});
		    });
			
			$('.detail-btn').on('click', function() {
		        var brandName = $(this).data('brand-name');
		        $('#DetailModal .modal-content').load('${pageContext.request.contextPath}/admin/brand/detail.htm?brandName=' + brandName, function() {
		            $('#DetailModal').modal('show');
		        });
		    });
			
			$('.delete-btn').on('click', function() {
			    var brandName = $(this).data('brand-name');
			    $('#delete-link').attr('href', '${pageContext.request.contextPath}/admin/brand/delete.htm?brandName=' + brandName);
		  	});
		});
  	</script>
</body>
</html>
