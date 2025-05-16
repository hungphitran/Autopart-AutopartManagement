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
	<style type="text/css">
	@charset "UTF-8";

	
	
    .wrapper{
       	display:flex;
       		margin-left: 200px;
	padding-top: 120px;
    }
   
    .container {
        height: 100vh;
        margin-left:10px;
        padding: 10px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }	
    
    .container div {
        display: block;
    }
    
    #products{
        list-style: none;
        max-height: 350px; /* Set a maximum height for the product list */
   	 	overflow-y: auto;
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
					            <div class="h5 mb-0 font-weight-bold text-gray-800">${incomeThisMonth} &#8363;</div>
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
					            <div class="h5 mb-0 font-weight-bold text-gray-800">${totalProductThisYear}</div>
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
					            <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">${accsThisMonth.size()}</div>
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
					            <div class="h5 mb-0 font-weight-bold text-gray-800">${newOrders.size()}</div>
					            <div class="mt-2 mb-0 text-muted text-xs">
					             <!--   <span class="text-danger mr-2"><i class="fas fa-arrow-down"></i> 1.10%</span>-->
					               <!-- <span>So với hôm qua</span>-->
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
					        <h6 class="m-0 font-weight-bold">Sản phẩm đã bán trong tháng</h6>
					        <div class="dropdown no-arrow">
					  <!--         <a class="dropdown-toggle btn btn-primary btn-sm" href="#" role="button" id="dropdownMenuLink"
					            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					            Tháng
					          </a> -->
					        </div>
					      </div>
					      <div class="card-body" id="products" >
					      <c:forEach items="${products}"   var ="p">
					        <div class="mb-3" >
					          <div class="small text-gray-500">${p.key.productId }
					            <div class="small float-right"><b>${p.value} trên ${(p.key.stock+p.value)} sản phẩm</b></div>
					          </div>
					          <div class="progress" style="height: 12px;">
								<c:choose>
					          		<c:when test="${p.value /(p.key.stock+p.value) *100 >=80}">
					            		<div  class="progress-bar bg-danger" role="progressbar" style="width: ${p.value}%" aria-valuenow="${p.value}"
					              		aria-valuemin="0" aria-valuemax="100">
					             		</div>
					            	</c:when>	
					          		<c:when test="${p.value /(p.key.stock+p.value) *100 >=50 && p.value /(p.key.stock+p.value) <80}">
					            		<div  class="progress-bar bg-warning" role="progressbar" style="width: ${p.value }%" aria-valuenow="${p.value}"
					              		aria-valuemin="0" aria-valuemax="100">
					             		</div>
					            	</c:when>	
					            	<c:when test="${p.value /(p.key.stock+p.value) *100 >0 && p.value /(p.key.stock+p.value) <50}">
					            		<div  class="progress-bar bg-success" role="progressbar" style="width: ${p.value}%" aria-valuenow="${p.value}"
					              		aria-valuemin="0" aria-valuemax="100">	
					             		</div>
					            	</c:when>					            
								</c:choose>
					          </div>
					        </div>
					       </c:forEach>
					      </div>
					<!--       <div class="card-footer text-center">
					        <a class="m-0 small text-primary card-link" href="#">Xem thêm <i
					            class="fas fa-chevron-right"></i></a>
					      </div>--> 
					    </div>
					  </div>
					  <!-- Invoice Example -->
					  <div class="col-xl-8 col-lg-7 mb-4">
					    <div class="card">
					      <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
					        <h6 class="m-0 font-weight-bold">Hóa đơn</h6>
					        <a class="m-0 float-right btn btn-danger btn-sm" href="/autopart/admin/order.htm?status=pending">Xem thêm <i
					            class="fas fa-chevron-right"></i></a>
					      </div>
					      <div class="table-responsive">
					        <table class="table align-items-center table-flush">
					          <thead class="thead-light">
					            <tr>
					              <th>Mã đơn hàng</th>
					              <th>Ngày đặt</th>
					              <th>Email</th>
					              <th>Trạng thái</th>
					              <th>Hoạt động</th>
					            </tr>
					          </thead>
					          <tbody>
					          <c:forEach items="${newOrders}" var="order">
					        	<tr>
					              <td><a href="#">${order.orderId }</a></td>
					              <td>${order.orderDate }</td>
					              <td>${order.userEmail }</td>
					              <c:choose>
                                  	<c:when test="${order.status == 'Pending'}">
                                  		<td><span class="badge badge-warning">Chờ xác nhận</span></td>
                                  	</c:when>
                                  	<c:when test="${order.status == 'Processing'}">
                                  		<td><span class="badge badge-info">Đang xử lý</span></td>
                                  	</c:when>
                                  	<c:when test="${order.status == 'Completed'}">
                                  		<td><span class="badge badge-success">Đã hoàn thành</span></td>
                                  	</c:when>
                                  	<c:when test="${order.status == 'Cancelled'}">
                                  		<td><span class="badge badge-danger">Đã hủy</span></td>
                                  	</c:when>
                                  	<c:when test="${order.status == 'Shipping'}">
                                  		<td><span class="badge badge-danger">Đang vận chuyển</span></td>
                                  	</c:when>
                                  	</c:choose>
					              <td><a href="/autopart/admin/order/detail.htm?orderId=${order.orderId }" class="btn btn-sm btn-primary">Chi tiết</a></td>
					            </tr>
					          </c:forEach>
					          </tbody>
					        </table>
					      </div>
					      <div class="card-footer"></div>
					    </div>
					  </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
<script src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
<script src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
<script src="<c:url value="/resources/vendor/chart.js/Chart.min.js" />"></script>
	<script type="text/javascript">
	
	var listIncome = '<c:forEach items="${income}" var="i">${i},</c:forEach>'.split(",");

	for (var i = 0; i < listIncome.length; i++) {
		  if (listIncome[i] == '') {
		    listIncome[i] = 0.00;
		  }
		  else{
			  listIncome[i]=Number.parseFloat(listIncome[i]);
		  }
	}

	Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
	Chart.defaults.global.defaultFontColor = '#858796';

	function number_format(number, decimals, dec_point, thousands_sep) {
	  // *     example: number_format(1234.56, 2, ',', ' ');
	  // *     return: '1 234,56'
	  number = (number + '').replace(',', '').replace(' ', '');
	  var n = !isFinite(+number) ? 0 : +number,
	    prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
	    sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
	    dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
	    s = '',
	    toFixedFix = function(n, prec) {
	      var k = Math.pow(10, prec);
	      return '' + Math.round(n * k) / k;
	    };
	  // Fix for IE parseFloat(0.55).toFixed(0) = 0;
	  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
	  if (s[0].length > 3) {
	    s[0] = s[0].replace(/\B(?=(?:\d{3})+(?!\d))/g, sep);
	  }
	  if ((s[1] || '').length < prec) {
	    s[1] = s[1] || '';
	    s[1] += new Array(prec - s[1].length + 1).join('0');
	  }
	  return s.join(dec);
	}

	// Area Chart Example

	var ctx = document.getElementById("myAreaChart");
	var myLineChart = new Chart(ctx, {
	  type: 'line',
	  data: {
	    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
	    datasets: [{
	      label: "Doanh thu",
	      lineTension: 0.3,
	      backgroundColor: "rgba(78, 115, 223, 0.5)",
	      borderColor: "rgba(78, 115, 223, 1)",
	      pointRadius: 3,
	      pointBackgroundColor: "rgba(78, 115, 223, 1)",
	      pointBorderColor: "rgba(78, 115, 223, 1)",
	      pointHoverRadius: 3,
	      pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
	      pointHoverBorderColor: "rgba(78, 115, 223, 1)",
	      pointHitRadius: 10,
	      pointBorderWidth: 2,
	      data: listIncome,
	    }],
	  },
	  options: {
	    maintainAspectRatio: false,
	    layout: {
	      padding: {
	        left: 10,
	        right: 25,
	        top: 25,
	        bottom: 0
	      }
	    },
	    scales: {
	      xAxes: [{
	        time: {
	          unit: 'date'
	        },
	        gridLines: {
	          display: false,
	          drawBorder: false
	        },
	        ticks: {
	          maxTicksLimit: 7
	        }
	      }],
	      yAxes: [{
	        ticks: {
	          maxTicksLimit: 5,
	          padding: 10,
	          // Include a dollar sign in the ticks
	          callback: function(value, index, values) {
	            return number_format(value)+ "đ";
	          }
	        },
	        gridLines: {
	          color: "rgb(234, 236, 244)",
	          zeroLineColor: "rgb(234, 236, 244)",
	          drawBorder: false,
	          borderDash: [2],
	          zeroLineBorderDash: [2]
	        }
	      }],
	    },
	    legend: {
	      display: false
	    },
	    tooltips: {
	      backgroundColor: "rgb(255,255,255)",
	      bodyFontColor: "#858796",
	      titleMarginBottom: 10,
	      titleFontColor: '#6e707e',
	      titleFontSize: 14,
	      borderColor: '#dddfeb',
	      borderWidth: 1,
	      xPadding: 15,
	      yPadding: 15,
	      displayColors: false,
	      intersect: false,
	      mode: 'index',
	      caretPadding: 10,
	      callbacks: {
	        label: function(tooltipItem, chart) {
	          var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
	          return datasetLabel + ': ' + number_format(tooltipItem.yLabel) + " đ";
	        }
	      }
	    }
	  }
	});

	
	</script>

<!--<script src="<c:url value="/resources/js/demo/chart-area-demo.js" />"></script>-->

</body>
</html>