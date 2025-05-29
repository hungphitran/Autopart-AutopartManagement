<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<title>Báo cáo tài chính</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link
	href="<c:url value="/resources/vendor/bootstrap/css/bootstrap.css" />"
	rel="stylesheet" type="text/css">
<link href="<c:url value="/resources/css/admincss/base.css" />"
	rel="stylesheet">
</head>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/mixins/adminnav.jsp" />

		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<jsp:include page="/WEB-INF/mixins/adminheader.jsp" />
				<div class="container-fluid" id="container-wrapper">					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800">Báo cáo tài chính</h1>
						<div class="btn-group">
	                        <a class="btn btn-secondary mr-2" href="/autopart/admin/statistic.htm">
	                            <i class="fas fa-chart-bar mr-1"></i>
	                            Quay lại thống kê
	                        </a>
	                        <button class="btn btn-success" onclick="exportFinancialExcel()">
	                            <i class="fas fa-file-excel mr-1"></i>
	                            Xuất Excel
	                        </button>
	                    </div>
					</div>

					<div class="card mb-4">
						<div
							class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
							<h6 class="m-0 font-weight-bold">Chọn khoảng thời gian</h6>
						</div>
						<div class="card-body">
							<form class="form-inline"
								action="${pageContext.request.contextPath}/admin/financial-report.htm"
								method="get">
								<div class="form-group mx-sm-3 mb-2">
									<label class="mr-2" for="fromDate">Từ ngày:</label> <input
										class="form-control" type="date" id="fromDate" name="fromDate"
										value="${fromDate}">
								</div>
								<div class="form-group mx-sm-3 mb-2">
									<label class="mr-2" for="toDate">Đến ngày:</label> <input
										class="form-control" type="date" id="toDate" name="toDate"
										value="${toDate}">
								</div>
								<button class="btn btn-primary mb-2" type="submit">Lọc
									dữ liệu</button>
							</form>
						</div>
					</div>

					<div class="row mb-4">
						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card h-100">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col mr-2">
											<div class="text-xs font-weight-bold text-uppercase mb-1">Tổng
												doanh thu</div>
											<div class="h5 mb-0 font-weight-bold text-gray-800">
												<fmt:formatNumber value="${totalIncome}" type="currency"
													currencyCode="VND" pattern="###,###,###,###,###,###,###" />
											</div>
										</div>
										<div class="col-auto">
											<i class="fas fa-dollar-sign fa-2x text-success"></i>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card h-100">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col mr-2">
											<div class="text-xs font-weight-bold text-uppercase mb-1">Tổng
												chi phí</div>
											<div class="h5 mb-0 font-weight-bold text-gray-800">
												<fmt:formatNumber value="${totalCost}" type="currency"
													currencyCode="VND" pattern="###,###,###,###,###,###,###" />
											</div>
										</div>
										<div class="col-auto">
											<i class="fas fa-money-bill-wave fa-2x text-danger"></i>
										</div>
									</div>
								</div>
							</div>
						</div>

						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card h-100">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col mr-2">
											<div class="text-xs font-weight-bold text-uppercase mb-1">Lợi
												nhuận</div>
											<div class="h5 mb-0 font-weight-bold text-gray-800">
												<fmt:formatNumber value="${profit}" type="currency"
													currencyCode="VND" pattern="###,###,###,###,###,###,###" />
											</div>
										</div>
										<div class="col-auto">
											<i class="fas fa-chart-line fa-2x text-primary"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xl-3 col-md-6 mb-4">
							<div class="card h-100">
								<div class="card-body">
									<div class="row align-items-center">
										<div class="col mr-2">
											<div class="text-xs font-weight-bold text-uppercase mb-1">Tỷ
												suất lợi nhuận</div>
											<div class="h5 mb-0 font-weight-bold text-gray-800">${profitRate}%</div>
										</div>
										<div class="col-auto">
											<i class="fas fa-percentage fa-2x text-info"></i>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-xl-8 col-lg-7">
							<div class="card mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">Biểu đồ
										doanh thu và chi phí</h6>
								</div>
								<div class="card-body">
									<div class="chart-area">
										<canvas id="revenueChart"></canvas>
									</div>
								</div>
							</div>
						</div>

						<div class="col-xl-4 col-lg-5">
							<div class="card mb-4">
								<div class="card-header py-3">
									<h6 class="m-0 font-weight-bold text-primary">Chi tiết chi
										phí</h6>
								</div>
								<div class="card-body">
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead class="thead-light">
												<tr>
													<th>Loại chi phí</th>
													<th>Số tiền</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="cost" items="${costDetails}">
													<tr>
														<td>${cost.key}</td>
														<td><fmt:formatNumber value="${cost.value}"
																type="currency" currencyCode="VND"
																pattern="###,###,###,###,###,###,###" /></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.4/moment.min.js"></script>
	<script src="<c:url value="/resources/vendor/jquery/jquery.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/bootstrap/js/bootstrap.bundle.min.js" />"></script>
	<script
		src="<c:url value="/resources/vendor/jquery-easing/jquery.easing.min.js" />"></script>
	<script src="<c:url value="/resources/js/ruang-admin.min.js" />"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"></script>
	<script>
	console.log("Financial Report Page Loaded ${fromDate} to ${toDate}");
	console.log("Total Revenue: ${totalRevenue}");
	console.log("Total Cost: ${totalCost}");
	console.log("Profit: ${profit}");	
	
    // Revenue Chart
    var ctx = document.getElementById("revenueChart").getContext('2d');
    var myLineChart = new Chart(ctx, {
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
                data:${revenueData}
            }, {
                label: "Chi phí",
                lineTension: 0.3,
                backgroundColor: "rgba(231, 74, 59, 0.05)",
                borderColor: "rgba(231, 74, 59, 1)",
                pointRadius: 3,
                pointBackgroundColor: "rgba(231, 74, 59, 1)",
                pointBorderColor: "rgba(231, 74, 59, 1)",
                pointHoverRadius: 3,
                pointHoverBackgroundColor: "rgba(231, 74, 59, 1)",
                pointHoverBorderColor: "rgba(231, 74, 59, 1)",
                pointHitRadius: 10,
                pointBorderWidth: 2,
                data: ${costData}
            }]
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
                    type: 'time',
                    time: {
                        unit: 'day',
                        displayFormats: {
                            day: 'YYYY-MM-DD'
                        }
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
                        callback: function(value, index, values) {
                            return value.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
                        }
                    },
                    gridLines: {
                        color: "rgb(234, 236, 244)",
                        zeroLineColor: "rgb(234, 236, 244)",
                        drawBorder: false,
                        borderDash: [2],
                        zeroLineBorderDash: [2]
                    }
                }]
            },
            legend: {
                display: true
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
                        return datasetLabel + ': ' + tooltipItem.yLabel.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});
                    }
                }
            }        }
    });
</script>

<script>
    function exportFinancialExcel() {
        const fromDate = document.getElementById('fromDate').value;
        const toDate = document.getElementById('toDate').value;
        
        if (!fromDate || !toDate) {
            alert('Vui lòng chọn khoảng thời gian trước khi xuất báo cáo');
            return;
        }
        
        const url = `/autopart/admin/export-excel.htm?reportType=financial&fromDate=${fromDate}&toDate=${toDate}`;
        
        // Create a temporary link to trigger download
        const link = document.createElement('a');
        link.href = url;
        link.download = '';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }
</script>
</body>
</html>