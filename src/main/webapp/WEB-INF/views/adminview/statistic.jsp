<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">	
	<title>Thống kê</title>
	
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
					<div class="d-sm-flex align-items-center justify-content-between mb-4">
					  <h1 class="h3 mb-0 text-gray-800">Thống kê</h1>
					</div>
					
					<div class="row mb-3">
					  <!-- Earnings (Monthly) Card Example -->
					  <div class="col-xl-3 col-md-6 mb-4">
					    <div class="card h-100">
					      <div class="card-body">
					        <div class="row align-items-center">
					          <div class="col mr-2">
					            <div class="text-xs font-weight-bold text-uppercase mb-1">Doanh thu (Theo tháng)</div>
					            <div class="h5 mb-0 font-weight-bold text-gray-800">20.000.000 &#8363;</div>
					            <div class="mt-2 mb-0 text-muted text-xs">
					              <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
					              <span>So với tháng trước</span>
					            </div>
					          </div>
					          <div class="col-auto">
					            <i class="fas fa-calendar fa-2x text-primary"></i>
					          </div>
					        </div>
					      </div>
					    </div>
					  </div>
					  <!-- Earnings (Annual) Card Example -->
					  <div class="col-xl-3 col-md-6 mb-4">
					    <div class="card h-100">
					      <div class="card-body">
					        <div class="row no-gutters align-items-center">
					          <div class="col mr-2">
					            <div class="text-xs font-weight-bold text-uppercase mb-1">Sản phẩm đã bán</div>
					            <div class="h5 mb-0 font-weight-bold text-gray-800">650</div>
					            <div class="mt-2 mb-0 text-muted text-xs">
					              <span class="text-success mr-2"><i class="fas fa-arrow-up"></i> 12%</span>
					              <span>So với năm trước</span>
					            </div>
					          </div>
					          <div class="col-auto">
					            <i class="fas fa-shopping-cart fa-2x text-success"></i>
					          </div>
					        </div>
					      </div>
					    </div>
					  </div>
					  <!-- New User Card Example -->
					  <div class="col-xl-3 col-md-6 mb-4">
					    <div class="card h-100">
					      <div class="card-body">
					        <div class="row no-gutters align-items-center">
					          <div class="col mr-2">
					            <div class="text-xs font-weight-bold text-uppercase mb-1">Người dùng mới</div>
					            <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">366</div>
					            <div class="mt-2 mb-0 text-muted text-xs">
					              <span class="text-success mr-2"><i class="fas fa-arrow-up"></i> 20.4%</span>
					              <span>So với tháng trước</span>
					            </div>
					          </div>
					          <div class="col-auto">
					            <i class="fas fa-users fa-2x text-info"></i>
					          </div>
					        </div>
					      </div>
					    </div>
					  </div>
					  <!-- Pending Requests Card Example -->
					  <div class="col-xl-3 col-md-6 mb-4">
					    <div class="card h-100">
					      <div class="card-body">
					        <div class="row no-gutters align-items-center">
					          <div class="col mr-2">
					            <div class="text-xs font-weight-bold text-uppercase mb-1">Yêu cầu đang chờ</div>
					            <div class="h5 mb-0 font-weight-bold text-gray-800">18</div>
					            <div class="mt-2 mb-0 text-muted text-xs">
					              <span class="text-danger mr-2"><i class="fas fa-arrow-down"></i> 1.10%</span>
					              <span>So với hôm qua</span>
					            </div>
					          </div>
					          <div class="col-auto">
					            <i class="fas fa-comments fa-2x text-warning"></i>
					          </div>
					        </div>
					      </div>
					    </div>
					  </div>
					
					  <!-- Area Chart -->
					  <div class="col-xl-8 col-lg-7">
					    <div class="card mb-4">
					      <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					        <h6 class="m-0 font-weight-bold">Thống kê doanh thu trong năm</h6>
					        <div class="dropdown no-arrow">
					          <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown"
					            aria-haspopup="true" aria-expanded="false">
					          </a>
					        </div>
					      </div>
					      <div class="card-body">
					        <div class="chart-area">
					          <canvas id="myAreaChart"></canvas>
					        </div>
					      </div>
					    </div>
					  </div>
					  <!-- Pie Chart -->
					  <div class="col-xl-4 col-lg-5">
					    <div class="card mb-4">
					      <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					        <h6 class="m-0 font-weight-bold">Sản phẩm đã bán</h6>
					        <div class="dropdown no-arrow">
					          <a class="dropdown-toggle btn btn-primary btn-sm" href="#" role="button" id="dropdownMenuLink"
					            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					            Tháng <i class="fas fa-chevron-down"></i>
					          </a>
					          <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
					            aria-labelledby="dropdownMenuLink">
					            <div class="dropdown-header">Chọn thời gian</div>
					            <a class="dropdown-item" href="#">Hôm nay</a>
					            <a class="dropdown-item" href="#">Tuần</a>
					            <a class="dropdown-item active" href="#">Tháng</a>
					            <a class="dropdown-item" href="#">Năm</a>
					          </div>
					        </div>
					      </div>
					      <div class="card-body">
					        <div class="mb-3">
					          <div class="small text-gray-500">Oblong T-Shirt
					            <div class="small float-right"><b>600 trên 800 sản phẩm</b></div>
					          </div>
					          <div class="progress" style="height: 12px;">
					            <div class="progress-bar bg-warning" role="progressbar" style="width: 80%" aria-valuenow="80"
					              aria-valuemin="0" aria-valuemax="100"></div>
					          </div>
					        </div>
					        <div class="mb-3">
					          <div class="small text-gray-500">Gundam 90'Editions
					            <div class="small float-right"><b>500 trên 800 sản phẩm</b></div>
					          </div>
					          <div class="progress" style="height: 12px;">
					            <div class="progress-bar bg-success" role="progressbar" style="width: 70%" aria-valuenow="70"
					              aria-valuemin="0" aria-valuemax="100"></div>
					          </div>
					        </div>
					        <div class="mb-3">
					          <div class="small text-gray-500">Rounded Hat
					            <div class="small float-right"><b>455 trên 800 sản phẩm</b></div>
					          </div>
					          <div class="progress" style="height: 12px;">
					            <div class="progress-bar bg-danger" role="progressbar" style="width: 55%" aria-valuenow="55"
					              aria-valuemin="0" aria-valuemax="100"></div>
					          </div>
					        </div>
					        <div class="mb-3">
					          <div class="small text-gray-500">Indomie Goreng
					            <div class="small float-right"><b>400 trên 800 sản phẩm</b></div>
					          </div>
					          <div class="progress" style="height: 12px;">
					            <div class="progress-bar bg-info" role="progressbar" style="width: 50%" aria-valuenow="50"
					              aria-valuemin="0" aria-valuemax="100"></div>
					          </div>
					        </div>
					        <div class="mb-3">
					          <div class="small text-gray-500">Remote Control Car Racing
					            <div class="small float-right"><b>200 trên 800 sản phẩm</b></div>
					          </div>
					          <div class="progress" style="height: 12px;">
					            <div class="progress-bar bg-success" role="progressbar" style="width: 30%" aria-valuenow="30"
					              aria-valuemin="0" aria-valuemax="100"></div>
					          </div>
					        </div>
					      </div>
					      <div class="card-footer text-center">
					        <a class="m-0 small text-primary card-link" href="#">Xem thêm <i
					            class="fas fa-chevron-right"></i></a>
					      </div>
					    </div>
					  </div>
					  <!-- Invoice Example -->
					  <div class="col-xl-8 col-lg-7 mb-4">
					    <div class="card">
					      <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					        <h6 class="m-0 font-weight-bold">Hóa đơn</h6>
					        <a class="m-0 float-right btn btn-danger btn-sm" href="#">Xem thêm <i
					            class="fas fa-chevron-right"></i></a>
					      </div>
					      <div class="table-responsive">
					        <table class="table align-items-center table-flush">
					          <thead class="thead-light">
					            <tr>
					              <th>Mã đơn hàng</th>
					              <th>Khách hàng</th>
					              <th>Số điện thoại</th>
					              <th>Trạng thái</th>
					              <th>Hoạt động</th>
					            </tr>
					          </thead>
					          <tbody>
					            <tr>
					              <td><a href="#">RA0449</a></td>
					              <td>Udin Wayang</td>
					              <td>Nasi Padang</td>
					              <td><span class="badge badge-success">Delivered</span></td>
					              <td><a href="#" class="btn btn-sm btn-primary">Chi tiết</a></td>
					            </tr>
					            <tr>
					              <td><a href="#">RA5324</a></td>
					              <td>Jaenab Bajigur</td>
					              <td>Gundam 90' Edition</td>
					              <td><span class="badge badge-warning">Shipping</span></td>
					              <td><a href="#" class="btn btn-sm btn-primary">Chi tiết</a></td>
					            </tr>
					            <tr>
					              <td><a href="#">RA8568</a></td>
					              <td>Rivat Mahesa</td>
					              <td>Oblong T-Shirt</td>
					              <td><span class="badge badge-danger">Pending</span></td>
					              <td><a href="#" class="btn btn-sm btn-primary">Chi tiết</a></td>
					            </tr>
					            <tr>
					              <td><a href="#">RA1453</a></td>
					              <td>Indri Junanda</td>
					              <td>Hat Rounded</td>
					              <td><span class="badge badge-info">Processing</span></td>
					              <td><a href="#" class="btn btn-sm btn-primary">Chi tiết</a></td>
					            </tr>
					            <tr>
					              <td><a href="#">RA1998</a></td>
					              <td>Udin Cilok</td>
					              <td>Baby Powder</td>
					              <td><span class="badge badge-success">Delivered</span></td>
					              <td><a href="#" class="btn btn-sm btn-primary">Chi tiết</a></td>
					            </tr>
					          </tbody>
					        </table>
					      </div>
					      <div class="card-footer"></div>
					    </div>
					  </div>
					  <!-- Message From Customer-->
					  <div class="col-xl-4 col-lg-5 ">
					    <div class="card">
					      <div class="card-header py-4 bg-primary d-flex flex-row align-items-center justify-content-between">
					        <h6 class="m-0 font-weight-bold text-light">Tin nhắn từ khách hàng</h6>
					      </div>
					      <div>
					        <div class="customer-message align-items-center">
					          <a class="font-weight-bold" href="#">
					            <div class="text-truncate message-title">Hi there! I am wondering if you can help me with a
					              problem I've been having.</div>
					            <div class="small text-gray-500 message-time font-weight-bold">Udin Cilok · 58m</div>
					          </a>
					        </div>
					        <div class="customer-message align-items-center">
					          <a href="#">
					            <div class="text-truncate message-title">But I must explain to you how all this mistaken idea
					            </div>
					            <div class="small text-gray-500 message-time">Nana Haminah · 58m</div>
					          </a>
					        </div>
					        <div class="customer-message align-items-center">
					          <a class="font-weight-bold" href="#">
					            <div class="text-truncate message-title">Lorem ipsum dolor sit amet, consectetur adipiscing elit
					            </div>
					            <div class="small text-gray-500 message-time font-weight-bold">Jajang Cincau · 25m</div>
					          </a>
					        </div>
					        <div class="customer-message align-items-center">
					          <a class="font-weight-bold" href="#">
					            <div class="text-truncate message-title">At vero eos et accusamus et iusto odio dignissimos
					              ducimus qui blanditiis
					            </div>
					            <div class="small text-gray-500 message-time font-weight-bold">Udin Wayang · 54m</div>
					          </a>
					        </div>
					        <div class="card-footer text-center">
					          <a class="m-0 small text-primary card-link" href="#">Xem thêm <i
					              class="fas fa-chevron-right"></i></a>
					        </div>
					      </div>
					    </div>
					  </div>
					</div>
					<!--Row-->
					
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
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var ctx = document.getElementById('statistics').getContext('2d');
        var chart = new Chart(ctx, {
            type: 'bar', // or 'line', 'pie', etc.
            data: {
                labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
                datasets: [{
                    label: 'Sales',
                    data: [10, 20, 30, 40, 50, 60, 70],
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });
</script>

<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
<script src="<c:url value="/resources/vendor/chart.js/Chart.min.js" />"></script>
<script src="<c:url value="/resources/js/demo/chart-area-demo.js" />"></script>

</body>
</html>