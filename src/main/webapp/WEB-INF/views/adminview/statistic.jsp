<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        max-height: 400px;
        overflow-y: auto;
    }
    
    .product-item {
        margin-bottom: 1rem;
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
                    <div id="alertContainer"></div>
					<div class="d-sm-flex align-items-center justify-content-between mb-4">
					  <h1 class="h3 mb-0 text-gray-800">Thống kê</h1>
					  <div class="btn-group">
                        <a class="btn btn-primary mr-2" href="/autopart/admin/product-report.htm?fromDate=${fromDate}&toDate=${toDate}">
                            <i class="fas fa-box mr-1"></i>
                            Báo cáo sản phẩm
                        </a>
                        <a class="btn btn-success" href="/autopart/admin/financial-report.htm?fromDate=${fromDate}&toDate=${toDate}">
                            <i class="fas fa-chart-line mr-1"></i>
                            Báo cáo tài chính
                        </a>
                      </div>
					</div>
					
                    <div class="card mb-4">
                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                            <h6 class="m-0 font-weight-bold">Chọn khoảng thời gian</h6>
                        </div>
                        <div class="card-body">
                        	<form class="form-inline" action="/autopart/admin/statistic.htm" method="get">
                                <div class="form-group mx-sm-3 mb-2">
                                    <label class="mr-2" for="fromDate">Từ ngày:</label>
                                    <input class="form-control" type="date" id="fromDate" name="fromDate" value="${fromDate}">
                                </div>
                                <div class="form-group mx-sm-3 mb-2">
                                    <label class="mr-2" for="toDate">Đến ngày:</label>
                                    <input class="form-control" type="date" id="toDate" name="toDate" value="${toDate}">
                                </div>
                                <button class="btn btn-primary mb-2" type="submit">Lọc dữ liệu</button>
                            </form>
                        </div>
                    </div>
                    
					<div class="row mb-3">
					  <!-- Earnings (Monthly) Card Example -->
					  <div class="col-xl-3 col-md-6 mb-4">
					    <div class="card h-100">
					      <div class="card-body">
					        <div class="row align-items-center">
					          <div class="col mr-2">
					            <div class="text-xs font-weight-bold text-uppercase mb-1">Tổng doanh thu</div>
					            <div class="h5 mb-0 font-weight-bold text-gray-800"><fmt:formatNumber value="${totalIncome}" pattern="#,##0"/> &#8363;</div>
					            <div class="text-xs text-muted mt-2">
					            </div>
					          </div>
					          <div class="col-auto">
					            <i class="fas fa-dollar-sign fa-2x text-primary"></i>
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
					            <div class="h5 mb-0 font-weight-bold text-gray-800"><fmt:formatNumber value="${totalProductThisYear}" pattern="#,##0"/></div>
					            <div class="text-xs text-muted mt-2">
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
					            <div class="text-xs font-weight-bold text-uppercase mb-1">Tài khoản mới</div>
					            <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800"><c:out value="${newAccs.size()}"/></div>
					            <div class="text-xs text-muted mt-2">
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
					            <div class="text-xs font-weight-bold text-uppercase mb-1">Đơn hàng chờ xác nhận</div>
					            <div class="h5 mb-0 font-weight-bold text-gray-800"><c:out value="${newOrders.size()}"/></div>
					            <div class="text-xs text-muted mt-2">
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
					        <h6 class="m-0 font-weight-bold">Doanh thu theo ngày</h6>
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
					        <span class="small text-gray-600"><c:out value="${products.size()}"/> Sản phẩm</span>
					      </div>
					      <c:if test="${products.size() !=0}">
					      <div class="card-body" id="products">
                            <div class="products-container">
                                <c:forEach items="${products}" var="p">
                                    <div class="product-item mb-4">
                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                            <h6 class="mb-0 font-weight-bold"><c:out value="${p.key.productName}"/></h6>
                                            <span class="badge badge-primary px-2"><c:out value="${p.key.productId}"/></span>
                                        </div>
                                        <div class="small text-gray-600 mb-2">
                                            <div class="row">
                                                <div class="col-6">
                                                    <i class="fas fa-tag mr-1"></i>
                                                    Giá: <fmt:formatNumber value="${p.key.salePrice}" pattern="#,##0"/> &#8363;
                                                </div>
                                                <div class="col-6 text-right">
                                                    <i class="fas fa-boxes mr-1"></i>
                                                    Tồn kho: <c:out value="${p.key.stock}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="small text-gray-600 mb-2">
                                            <div class="row">
                                                <div class="col-6">
                                                    <i class="fas fa-shopping-cart mr-1"></i>
                                                    Đã bán: <c:out value="${p.value}"/>
                                                </div>
                                                <div class="col-6 text-right">
                                                    <i class="fas fa-chart-pie mr-1"></i>
                                                    Tổng: <c:out value="${p.key.stock + p.value}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="progress" style="height: 12px;">
                                            <c:set var="percentage" value="${p.value / (p.key.stock + p.value) * 100}" />
                                            <c:choose>
                                                <c:when test="${percentage >= 80}">
                                                    <div class="progress-bar bg-danger" role="progressbar" style="width: ${percentage}%" 
                                                        aria-valuenow="${p.value}" aria-valuemin="0" aria-valuemax="100"></div>
                                                </c:when>
                                                <c:when test="${percentage >= 50}">
                                                    <div class="progress-bar bg-warning" role="progressbar" style="width: ${percentage}%" 
                                                        aria-valuenow="${p.value}" aria-valuemin="0" aria-valuemax="100"></div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="progress-bar bg-success" role="progressbar" style="width: ${percentage}%" 
                                                        aria-valuenow="${p.value}" aria-valuemin="0" aria-valuemax="100"></div>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="small text-muted text-center mt-1">
                                            Đã bán <fmt:formatNumber value="${percentage}" pattern="#,##0.0"/>% trong tổng số sản phẩm
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                          </div>
                          </c:if>
                          <c:if test="${products.size() == 0}">
                            <div class="card-body text-center">
                                <p class="text-muted">Không có sản phẩm nào được bán trong khoảng thời gian này.</p>
                                </div>
                           </c:if>
                          <div class="card-footer py-2 bg-light">
                            <button id="showMoreProducts" class="btn btn-link text-primary w-100" type="button">
                                <span class="text-primary">Xem thêm</span>
                                <i class="fas fa-chevron-down ml-1"></i>
                            </button>
                          </div>
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
	<script type="text/javascript">
		// Hàm number_format
		function number_format(number, decimals = 0, dec_point = ',', thousands_sep = '.') {
		    number = (number + '').replace(/[^0-9+\-Ee.]/g, '');
		    var n = !isFinite(+number) ? 0 : +number,
		        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
		        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
		        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
		        s = '',
		        toFixedFix = function(n, prec) {
		            var k = Math.pow(10, prec);
		            return '' + Math.round(n * k) / k;
		        };
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
	
		document.addEventListener('DOMContentLoaded', function() {
		    // Kiểm tra và định dạng đầu vào ngày
		    const fromDateInput = document.getElementById('fromDate');
		    const toDateInput = document.getElementById('toDate');
		    
		    // Nếu đầu vào ngày không có giá trị, đặt giá trị mặc định
		    if (!fromDateInput.value) {
		        const firstDayOfYear = new Date(new Date().getFullYear(), 0, 1);
		        fromDateInput.value = formatDate(firstDayOfYear);
		    }
		    
		    if (!toDateInput.value) {
		        const today = new Date();
		        toDateInput.value = formatDate(today);
		    }
		    
		    // Định dạng ngày cho các trường đầu vào
		    function formatDate(date) {
		        const year = date.getFullYear();
		        const month = String(date.getMonth() + 1).padStart(2, '0');
		        const day = String(date.getDate()).padStart(2, '0');
		        return `${year}-${month}-${day}`;
		    }
		    
		    // Kiểm tra và tạo biểu đồ
		    function initializeChart() {
		        const ctx = document.getElementById("myAreaChart");
		        if (!ctx) {
		            console.error("Canvas element không tìm thấy");
		            return;
		        }
		        
		        // Lấy dữ liệu từ biến chứa trong window object
		        const dailyIncomeData = window.dailyIncomeData || [];
		        const labelsData = window.labelsData || [];
		        
		        // Kiểm tra dữ liệu hợp lệ
		        if (Array.isArray(dailyIncomeData) && Array.isArray(labelsData) && dailyIncomeData.length > 0 && labelsData.length > 0) {
		            new Chart(ctx, {
		                type: 'line',
		                data: {
		                    labels: ${labels},
		                    datasets: [{
		                        label: "Doanh thu",
		                        lineTension: 0.3,
		                        backgroundColor: "rgba(78, 115, 223, 0.05)",
		                        borderColor: "rgba(78, 115, 223, 1)",
		                        pointRadius: 3,
		                        pointBackgroundColor: "rgba(78, 115, 223, 1)",
		                        pointBorderColor: "rgba(78, 115, 223, 1)",
		                        pointHoverRadius: 3,
		                        pointHoverBackgroundColor: "rgba(78, 115, 223, 1)",
		                        pointHoverBorderColor: "rgba(78, 115, 223, 1)",
		                        pointHitRadius: 10,
		                        pointBorderWidth: 2,
		                        data: ${dailyIncome},
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
		                        x: {
		                            grid: {
		                                display: false,
		                                drawBorder: false
		                            },
		                            ticks: {
		                                maxTicksLimit: 7,
		                                callback: function(value, index, values) {
		                                    if (typeof labelsData[index] === 'string') {
		                                        const parts = labelsData[index].split('-');
		                                        if (parts.length === 3) {
		                                            return parts[2] + '/' + parts[1];
		                                        }
		                                    }
		                                    return value;
		                                }
		                            }
		                        },
		                        y: {
		                            ticks: {
		                                maxTicksLimit: 5,
		                                padding: 10,
		                                callback: function(value, index, values) {
		                                    return number_format(value) + " đ";
		                                }
		                            },
		                            grid: {
		                                color: "rgb(234, 236, 244)",
		                                zeroLineColor: "rgb(234, 236, 244)",
		                                drawBorder: false,
		                                borderDash: [2],
		                                zeroLineBorderDash: [2]
		                            }
		                        },
		                    },
		                    plugins: {
		                        legend: {
		                            display: false
		                        },
		                        tooltip: {
		                            backgroundColor: "rgb(255,255,255)",
		                            bodyColor: "#858796",
		                            titleMarginBottom: 10,
		                            titleColor: '#6e707e',
		                            titleFont: {
		                                size: 14
		                            },
		                            borderColor: '#dddfeb',
		                            borderWidth: 1,
		                            padding: 15,
		                            displayColors: false,
		                            intersect: false,
		                            mode: 'index',
		                            caretPadding: 10,
		                            callbacks: {
		                                title: function(tooltipItems) {
		                                    if (tooltipItems.length > 0) {
		                                        const dataIndex = tooltipItems[0].dataIndex;
		                                        const dateStr = labelsData[dataIndex];
		                                        if (typeof dateStr === 'string') {
		                                            const parts = dateStr.split('-');
		                                            if (parts.length === 3) {
		                                                return parts[2] + '/' + parts[1] + '/' + parts[0];
		                                            }
		                                        }
		                                    }
		                                    return '';
		                                },
		                                label: function(tooltipItem) {
		                                    return "Doanh thu: " + number_format(tooltipItem.raw) + " đ";
		                                }
		                            }
		                        }
		                    }
		                }
		            });
		        } else {
		            // Hiển thị thông báo khi không có dữ liệu
		            const container = ctx.parentElement;
		            const noDataMsg = document.createElement('div');
		            noDataMsg.className = 'text-center py-5 text-muted';
		            noDataMsg.innerHTML = '<i class="fas fa-chart-line fa-3x mb-3"></i><br>Không có dữ liệu trong khoảng thời gian đã chọn';
		            container.appendChild(noDataMsg);
		        }
		    }
		    
		    // Xử lý hiển thị thêm sản phẩm
		    function initializeProducts() {
		        const productsContainer = document.getElementById('products');
		        const showMoreBtn = document.getElementById('showMoreProducts');
		        
		        if (productsContainer && showMoreBtn) {
		            let expanded = false;
		            
		            showMoreBtn.addEventListener('click', function() {
		                if (expanded) {
		                    productsContainer.style.maxHeight = '400px';
		                    productsContainer.style.overflowY = 'auto';
		                    showMoreBtn.innerHTML = '<span class="text-primary">Xem thêm</span> <i class="fas fa-chevron-down ml-1"></i>';
		                } else {
		                    productsContainer.style.maxHeight = 'none';
		                    productsContainer.style.overflowY = 'auto';
		                    showMoreBtn.innerHTML = '<span class="text-primary">Thu gọn</span> <i class="fas fa-chevron-up ml-1"></i>';
		                }
		                expanded = !expanded;
		            });
		        }
		    }
		    
		    // Hiển thị thông báo
		    function initializeAlerts() {
		        const urlParams = new URLSearchParams(window.location.search);
		        const status = urlParams.get('status');
		        const alertContainer = document.getElementById('alertContainer');
		
		        if (status === 'success') {
		            alertContainer.innerHTML = `
		                <div class="alert alert-success alert-dismissible fade show" role="alert">
		                    <strong>Thành công!</strong> Dữ liệu đã được cập nhật.
		                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                    </button>
		                </div>`;
		        } else if (status === 'error') {
		            alertContainer.innerHTML = `
		                <div class="alert alert-danger alert-dismissible fade show" role="alert">
		                    <strong>Lỗi!</strong> Đã xảy ra lỗi khi cập nhật dữ liệu.
		                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                    </button>
		                </div>`;
		        }
		
		        // Tự động ẩn thông báo sau 5 giây
		        setTimeout(function() {
		            const alerts = document.querySelectorAll('.alert');
		            alerts.forEach(function(alert) {
		                if (typeof bootstrap !== 'undefined' && bootstrap.Alert) {
		                    const bsAlert = new bootstrap.Alert(alert);
		                    bsAlert.close();
		                }
		            });
		        }, 5000);
		    }
		    
		    // Kiểm tra ngày hợp lệ
		    function initializeDateValidation() {
		        const fromDateInput = document.getElementById('fromDate');
		        const toDateInput = document.getElementById('toDate');
		
		        fromDateInput.addEventListener('change', validateDates);
		        toDateInput.addEventListener('change', validateDates);
		
		        function validateDates() {
		            const fromDate = new Date(fromDateInput.value);
		            const toDate = new Date(toDateInput.value);
		            
		            if (fromDate > toDate) {
		                alert('Ngày bắt đầu không thể sau ngày kết thúc');
		                fromDateInput.value = toDateInput.value;
		            }
		        }
		    }
		    
		    // Khởi tạo dữ liệu từ server
		    try {
	    		window.dailyIncomeData = ${empty dailyIncome ? '[]' : dailyIncome};
	    		window.labelsData = ${empty labels ? '[]' : labels};
			} catch (e) {
	    		window.dailyIncomeData = [];
	    		window.labelsData = [];
			}
		    
		    // Gọi các hàm khởi tạo
		    initializeChart();
		    initializeProducts();
		    initializeAlerts();
		    initializeDateValidation();
		});
	</script>
</body>
</html>