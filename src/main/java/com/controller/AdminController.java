package com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Account_DAO;
import com.dao.BlogGroup_DAO;
import com.dao.Blog_DAO;
import com.dao.Brand_DAO;
import com.dao.Customer_DAO;
import com.dao.Employee_DAO;
import com.dao.GeneralSettings_DAO;
import com.dao.ImportDetail_DAO;
import com.dao.Import_DAO;
import com.dao.OrderDetail_DAO;
import com.dao.Order_DAO;
//import com.dao.Permission_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.dao.RoleGroup_DAO;
import com.dao.Discount_DAO;

import com.entity.Account;
import com.entity.Brand;
import com.entity.Product;
import com.entity.ProductGroup;
import com.utils.ValidationUtils;
import com.entity.Customer;
import com.entity.Blog;
import com.entity.BlogGroup;
import com.entity.Discount;
import com.entity.Employee;
import com.entity.Import;
import com.entity.Order;
import com.entity.OrderDetail;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	RoleGroup_DAO rgdao;
	
//	@Autowired
//	Permission_DAO pdao;
	
	@Autowired
	Account_DAO accountDao;
	
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Discount_DAO discountDao;

	@Autowired
	Blog_DAO blogDao;
	
	@Autowired
	BlogGroup_DAO blogGroupDao;

	@Autowired
	ProductGroup_DAO productGroupDao;

	@Autowired
	Brand_DAO brandDao;

	@Autowired
	Employee_DAO employeeDao;

	@Autowired
	Order_DAO orderDao;
	
	@Autowired
	OrderDetail_DAO orderDetailDao;

	@Autowired
	Product_DAO productDao;
	
	@Autowired
	GeneralSettings_DAO gsdao;
	
	@Autowired
	ImportDetail_DAO importDetailDao;
	
	@Autowired
	Import_DAO importDao;
	
	private String getMD5Hash(String input) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(input.getBytes());
	        byte[] digest = md.digest();
	        return DatatypeConverter.printHexBinary(digest).toLowerCase();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {
        return "adminview/account/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model,RedirectAttributes redirectAttributes) {
        if(!ValidationUtils.isValidEmail(email)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Email không hợp lệ!");
			return "redirect:/admin/login.htm";
        	
        }
        if(!ValidationUtils.isValidPassword(password)) {
        	
        	redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu không hợp lệ!");
			return "redirect:/admin/login.htm";
		}
		Account account = accountDao.getByEmail(email);
		if (account == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Sai email hoặc mật khẩu!");
			return "redirect:/admin/login.htm";
		}
        
        System.out.println(account.getPassword()+ getMD5Hash(password)	);
        if (account != null && account.getPassword().equals(getMD5Hash(password) ) && employeeDao.getByEmail(email)!=null) {
        	session.setAttribute("account", account);
        	session.setAttribute("permissions", rgdao.getById(account.getPermission()).getPermissions());
    		session.setAttribute("name", employeeDao.getByEmail(account.getEmail()).getFullName());
    		session.setAttribute("email", account.getEmail());
    		redirectAttributes.addFlashAttribute("successMessage", "Đăng nhập thành công!");
    		return "redirect:/admin/profile.htm";
        }
        
        redirectAttributes.addFlashAttribute("errorMessage", "Sai email hoặc mật khẩu!");
		return "redirect:/admin/login.htm";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "Đăng xuất thành công!");
        return "redirect:/admin/login.htm";
    }
    
    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied(HttpSession session,HttpServletRequest req) {
    	String referer = req.getHeader("Referer");
    	req.setAttribute("preUrl", referer);
    	System.out.println(referer);
        return "adminview/account/access-denied";
    }
	
    @RequestMapping("/statistic")
    public String showStatistic(HttpServletRequest req, @RequestParam(required = false) String fromDate, 
                                @RequestParam(required = false) String toDate) {
        HttpSession session = req.getSession();
        Account acc = (Account) session.getAttribute("account");
        
        // Setup date range
        java.util.Date today = new java.util.Date();
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(today);
        int currentYear = cal.get(java.util.Calendar.YEAR);
        int currentMonth = cal.get(java.util.Calendar.MONTH);
        
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;
        
        // Auto-filter from beginning of current year to today if not specified
        if (fromDate == null || fromDate.isEmpty()) {
            cal.set(currentYear, 0, 1, 0, 0, 0);
            cal.set(java.util.Calendar.MILLISECOND, 0);
            startDate = new java.sql.Date(cal.getTimeInMillis());
        } else {
            try {
                startDate = java.sql.Date.valueOf(fromDate);
            } catch (Exception e) {
                cal.set(currentYear, 0, 1, 0, 0, 0);
                cal.set(java.util.Calendar.MILLISECOND, 0);
                startDate = new java.sql.Date(cal.getTimeInMillis());
            }
        }
        
        if (toDate == null || toDate.isEmpty()) {
            endDate = new java.sql.Date(today.getTime());
        } else {
            try {
                endDate = java.sql.Date.valueOf(toDate);
            } catch (Exception e) {
                endDate = new java.sql.Date(today.getTime());
            }
        }
        
        // Format dates for the view
        req.setAttribute("fromDate", startDate.toString());
        req.setAttribute("toDate", endDate.toString());
        
        // Get orders within date range
        List<Order> orders = orderDao.getOrdersByDateRangeAndStatus(startDate, endDate, "Completed");
        
        // Initialize income tracking
        BigDecimal[] income = new BigDecimal[12];
        for (int i = 0; i < income.length; i++) {
            income[i] = BigDecimal.ZERO;
        }
        
        // Initialize collections for statistics
        List<Order> ordersThisYear = new ArrayList<>();
        List<Order> ordersThisMonth = new ArrayList<>();
        List<Order> ordersLastMonth = new ArrayList<>();
        List<Order> ordersLastYear = new ArrayList<>();
        
        // For tracking daily income (for chart)
        List<BigDecimal> dailyIncome = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        
        // Create date labels for each day in the range
        java.util.Calendar startCal = java.util.Calendar.getInstance();
        startCal.setTime(startDate);
        java.util.Calendar endCal = java.util.Calendar.getInstance();
        endCal.setTime(endDate);
        endCal.add(java.util.Calendar.DATE, 1); // Include end date
        
        // Initialize daily income map
        Map<String, BigDecimal> dailyIncomeMap = new HashMap<>();
        
        while (startCal.before(endCal)) {
            String dateStr = new java.sql.Date(startCal.getTimeInMillis()).toString();
            labels.add(dateStr);
            dailyIncomeMap.put(dateStr, BigDecimal.ZERO);
            startCal.add(java.util.Calendar.DATE, 1);
        }
        
        // Product sales tracking
        Map<Product, Integer> products = new TreeMap<>(new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getProductId().compareTo(p2.getProductId());
            }
        });
        
        // Process orders for statistics
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        int totalProductThisYear = 0;
        int totalProductLastYear = 0;
        
        for (Order o : orders) {
            Date orderDate = o.getOrderDate();
            java.util.Calendar orderCal = java.util.Calendar.getInstance();
            orderCal.setTime(orderDate);
            int orderYear = orderCal.get(java.util.Calendar.YEAR);
            int orderMonth = orderCal.get(java.util.Calendar.MONTH);
            
            // Add to monthly income
            income[orderMonth] = income[orderMonth].add(o.getTotalCost());
            
            // Add to total income
            totalIncome = totalIncome.add(o.getTotalCost());
            
            // Add to daily income for chart
            String orderDateStr = orderDate.toString();
            BigDecimal dayIncome = dailyIncomeMap.get(orderDateStr);
            if (dayIncome != null) {
                dailyIncomeMap.put(orderDateStr, dayIncome.add(o.getTotalCost()));
            }
            
            // Get order details to calculate costs
            List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(o.getOrderId());
            BigDecimal orderCostPrice = BigDecimal.ZERO;
            
            for (OrderDetail detail : orderDetails) {
                Product product = productDao.getById(detail.getProductId());
                if (product != null) {
                    // Calculate cost for this order detail
                    BigDecimal detailCost = new BigDecimal(product.getCostPrice())
                        .multiply(new BigDecimal(detail.getAmount()));
                    orderCostPrice = orderCostPrice.add(detailCost);
                    
                    // Track product sales
                    products.put(product, products.getOrDefault(product, 0) + detail.getAmount());
                }
            }
            
            totalCost = totalCost.add(orderCostPrice);
            
            // Categorize orders by year and month
            if (orderYear == currentYear) {
                ordersThisYear.add(o);
                
                // Count products sold this year
                totalProductThisYear += orderDetails.stream().mapToInt(OrderDetail::getAmount).sum();
                
                if (orderMonth == currentMonth) {
                    ordersThisMonth.add(o);
                } else if (orderMonth == (currentMonth > 0 ? currentMonth - 1 : 11)) {
                    ordersLastMonth.add(o);
                }
            } else if (orderYear == currentYear - 1) {
                ordersLastYear.add(o);
                
                // Count products sold last year
                totalProductLastYear += orderDetails.stream().mapToInt(OrderDetail::getAmount).sum();
            }
        }
        
        // Convert daily income map to list in the correct order
        for (String dateStr : labels) {
            dailyIncome.add(dailyIncomeMap.getOrDefault(dateStr, BigDecimal.ZERO));
        }
        
        // Calculate profit (income - cost)
        BigDecimal totalProfit = totalIncome.subtract(totalCost);
        
        // Calculate statistics for this month vs last month
        BigDecimal incomeThisMonth = income[currentMonth];
        BigDecimal incomeLastMonth = currentMonth > 0 ? income[currentMonth - 1] : income[11];
        
        // Calculate income growth percentage
        BigDecimal incomeGrowth = BigDecimal.ZERO;
        if (incomeLastMonth.compareTo(BigDecimal.ZERO) > 0) {
            incomeGrowth = incomeThisMonth.subtract(incomeLastMonth)
                         .multiply(new BigDecimal(100))
                         .divide(incomeLastMonth, 2, RoundingMode.HALF_UP);
        } else if (incomeThisMonth.compareTo(BigDecimal.ZERO) > 0) {
            incomeGrowth = new BigDecimal(100); // 100% growth if last month was 0
        }
        
        // Calculate product growth
        int productGrowth = 0;
        if (totalProductLastYear > 0) {
            productGrowth = ((totalProductThisYear - totalProductLastYear) * 100) / totalProductLastYear;
        } else if (totalProductThisYear > 0) {
            productGrowth = 100; // 100% growth if last year was 0
        }
        
        // Calculate order growth
        int orderThisMonthCount = ordersThisMonth.size();
        int orderLastMonthCount = ordersLastMonth.size();
        int orderGrowth = 0;
        if (orderLastMonthCount > 0) {
            orderGrowth = ((orderThisMonthCount - orderLastMonthCount) * 100) / orderLastMonthCount;
        } else if (orderThisMonthCount > 0) {
            orderGrowth = 100; // 100% growth if last month was 0
        }
        
        // Calculate account growth
        List<Account> accs = accountDao.getAll();
        List<Account> newAccs = new ArrayList<>();
        
        for (Account a : accs) {
            if (a.getCreatedAt() != null) {
                LocalDateTime createdAt = a.getCreatedAt().toLocalDateTime();
                LocalDateTime startDateTime = startDate.toLocalDate().atStartOfDay();
                LocalDateTime endDateTime = endDate.toLocalDate().atTime(23, 59, 59);
                
                if (createdAt.isAfter(startDateTime) && createdAt.isBefore(endDateTime)) {
                    newAccs.add(a);
                }
            }
        }
        
        // Get pending orders
        List<Order> newOrders = orderDao.getOrderByStatus("Pending");
        
        // Format labels for JavaScript
        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i);
            labels.set(i, "'" + label + "'");
        }
        
        // Format daily income values
        for (int i = 0; i < dailyIncome.size(); i++) {
            BigDecimal incomeValue = dailyIncome.get(i);
            dailyIncome.set(i, incomeValue.setScale(2, RoundingMode.HALF_UP));
        }
        
        // Set request attributes for view
        req.setAttribute("income", income);
        req.setAttribute("dailyIncome", dailyIncome);
        req.setAttribute("totalProductThisYear", totalProductThisYear);
        req.setAttribute("totalProductLastYear", totalProductLastYear);
        req.setAttribute("productGrowth", productGrowth);
        req.setAttribute("ordersThisYear", ordersThisYear);
        req.setAttribute("ordersLastYear", ordersLastYear);
        req.setAttribute("ordersLastMonth", ordersLastMonth);
        req.setAttribute("ordersThisMonth", ordersThisMonth);
        req.setAttribute("orderGrowth", orderGrowth);
        req.setAttribute("newOrders", newOrders);
        req.setAttribute("newAccs", newAccs);
        req.setAttribute("incomeLastMonth", incomeLastMonth);
        req.setAttribute("incomeThisMonth", incomeThisMonth);
        req.setAttribute("incomeGrowth", incomeGrowth);
        req.setAttribute("products", products);
        req.setAttribute("labels", labels);
        req.setAttribute("totalIncome", totalIncome);
        req.setAttribute("totalCost", totalCost);
        req.setAttribute("totalProfit", totalProfit);
        
        return "adminview/statistic";
    }
	
    @RequestMapping("/product-report")
    public String showProductReport(HttpServletRequest req) {
    	String fromDate = req.getParameter("fromDate");
		String toDate = req.getParameter("toDate");
		
		System.out.println(fromDate+"--" + toDate);

    	// Default to last 30 days if not provided
        LocalDate startDate = fromDate != null ? LocalDate.parse(fromDate) : LocalDate.now().minusDays(30);
        LocalDate endDate = toDate != null ? LocalDate.parse(toDate) : LocalDate.now();
        
        List<Product> products = productDao.getAll();
        List<Order> orders = orderDao.getOrdersByDateRangeAndStatus(Date.valueOf(startDate), Date.valueOf(endDate), "Completed");
        
        List<OrderDetail> orderDetails = new ArrayList<>();
        
        for (Order order : orders) {
			orderDetails.addAll(orderDetailDao.getAllByOrderId(order.getOrderId()));
		}
        
        Map<Product, Integer> productSales = new HashMap<>();
        for (OrderDetail detail : orderDetails) {
			Product product = productDao.getById(detail.getProductId());
			if (product != null) {
				productSales.put(product, productSales.getOrDefault(product, 0) + detail.getAmount());
			}
		}
        
        // Sort products by sales in descending order
        List<Map.Entry<Product, Integer>> sortedProductSales = new ArrayList<>(productSales.entrySet());
        sortedProductSales.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        
        req.setAttribute("products", products);
        req.setAttribute("productSales", sortedProductSales);
        
        req.setAttribute("fromDate", startDate);
        req.setAttribute("toDate", endDate);
    	return "adminview/report/product-report";
    }
    
    @RequestMapping("/financial-report")
    public String showFinancialReport(HttpServletRequest req) {
        String fromDate = req.getParameter("fromDate");
        String toDate = req.getParameter("toDate");
        System.out.println(fromDate + "--" + toDate);

        // Default to last 30 days if not provided
        LocalDate startDate = fromDate != null && !fromDate.isEmpty() ? LocalDate.parse(fromDate) : LocalDate.now().minusDays(30);
        LocalDate endDate = toDate != null && !toDate.isEmpty() ? LocalDate.parse(toDate) : LocalDate.now();

        // Get orders from DAO
        List<Order> orders = orderDao.getOrdersByDateRangeAndStatus(Date.valueOf(startDate), Date.valueOf(endDate), "Completed");
        List<Import> imports = importDao.getAll();

        // Initialize totals
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO; // Tổng giá nhập của sản phẩm đã bán
        BigDecimal totalImportCost = BigDecimal.ZERO; // Tổng chi phí nhập hàng

        // Aggregate data by day for chart
        Map<LocalDate, BigDecimal> revenueByDate = new HashMap<>();
        Map<LocalDate, BigDecimal> costByDate = new HashMap<>();
        List<LocalDate> dateRange = startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());

        // Initialize maps with zeros for all dates in range
        for (LocalDate date : dateRange) {
            System.out.println(date);
            revenueByDate.put(date, BigDecimal.ZERO);
            costByDate.put(date, BigDecimal.ZERO);
        }

        // Aggregate revenue and product cost from orders
        for (Order order : orders) {
            List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(order.getOrderId());
            
            // Calculate product cost from order details (giá nhập của sản phẩm đã bán)
            BigDecimal orderProductCost = BigDecimal.ZERO;
            for (OrderDetail detail : orderDetails) {
                Product product = productDao.getById(detail.getProductId());
                if (product != null) {
                    BigDecimal detailCost = BigDecimal.valueOf(detail.getAmount()).multiply(BigDecimal.valueOf(product.getCostPrice()));
                    orderProductCost = orderProductCost.add(detailCost);
                }
            }
            
            totalCost = totalCost.add(orderProductCost); // Cộng giá nhập sản phẩm vào tổng chi phí
            
            LocalDate orderDate = order.getOrderDate().toLocalDate();
            if (!orderDate.isBefore(startDate) && !orderDate.isAfter(endDate)) {
                BigDecimal orderRevenue = order.getTotalCost();
                totalIncome = totalIncome.add(orderRevenue);
                
                // Add revenue to chart data
                revenueByDate.merge(orderDate, orderRevenue, BigDecimal::add);
                // Add product cost to chart data
                costByDate.merge(orderDate, orderProductCost, BigDecimal::add);
            }
        }

        // Calculate import costs (chỉ để hiển thị thông tin, không tính vào totalCost)
        for (Import imp : imports) {
            LocalDate importDate = imp.getImportDate().toLocalDate();
            if (!importDate.isBefore(startDate) && !importDate.isAfter(endDate)) {
                BigDecimal importCost = imp.getImportCost();
                totalImportCost = totalImportCost.add(importCost);
                // Import cost không được thêm vào costByDate vì nó không phải chi phí bán hàng
            }
        }

        // Prepare chart data
        List<String> labels = dateRange.stream()
                .map(LocalDate::toString)
                .collect(Collectors.toList());
        for (int i = 0; i < labels.size(); i++) {
            String label = labels.get(i);
            labels.set(i, "'" + label + "'"); // Format to YYYY-MM-DD
        }
        List<BigDecimal> revenueData = dateRange.stream()
                .map(revenueByDate::get)
                .collect(Collectors.toList());
        List<BigDecimal> costData = dateRange.stream()
                .map(costByDate::get)
                .collect(Collectors.toList());

        // Calculate profit and profit margin
        BigDecimal profit = totalIncome.subtract(totalCost); // Doanh thu - Giá nhập sản phẩm đã bán
        BigDecimal shippingCost = totalIncome.subtract(totalCost); // Phí vận chuyển = Doanh thu - Giá nhập sản phẩm

        Map<String, BigDecimal> costDetails = new HashMap<>();
        costDetails.put("Lợi nhuận", profit);
        costDetails.put("Tổng chi phí", totalCost); // Tổng giá nhập sản phẩm đã bán
        costDetails.put("Tổng doanh thu", totalIncome);
        costDetails.put("Tổng phí vận chuyển", shippingCost);
        costDetails.put("Tổng giá trị hàng bán", totalCost); // Giống với tổng chi phí
        
        if (totalIncome.compareTo(BigDecimal.ZERO) > 0) {
        	req.setAttribute("profitRate", profit.multiply(BigDecimal.valueOf(100)).divide(totalIncome, 2, RoundingMode.HALF_UP));
        } else {
            req.setAttribute("profitRate", BigDecimal.ZERO);
        }

        // Set attributes for view
        System.out.println("Total Income: " + totalIncome);
        System.out.println("Total Cost (Product Cost): " + totalCost);
        System.out.println("Total Import Cost: " + totalImportCost);
        System.out.println("Profit: " + profit);
        
        req.setAttribute("labels", labels);
        req.setAttribute("revenueData", revenueData);
        req.setAttribute("costData", costData);
        req.setAttribute("totalIncome", totalIncome);
        req.setAttribute("totalCost", totalCost);
        req.setAttribute("profit", profit);
        req.setAttribute("shippingCost", shippingCost);
        req.setAttribute("costDetails", costDetails);
        req.setAttribute("fromDate", startDate.toString());
        req.setAttribute("toDate", endDate.toString());
        
        return "adminview/report/financial-report";
    }
    
    @RequestMapping("/export-excel")
    public void exportReportsToExcel(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String fromDate = req.getParameter("fromDate");
            String toDate = req.getParameter("toDate");
            String reportType = req.getParameter("reportType");

            // Default to last 30 days if not provided
            LocalDate startDate = fromDate != null && !fromDate.isEmpty() ? LocalDate.parse(fromDate) : LocalDate.now().minusDays(30);
            LocalDate endDate = toDate != null && !toDate.isEmpty() ? LocalDate.parse(toDate) : LocalDate.now();

            // Create workbook
            Workbook workbook = new XSSFWorkbook();

            // Generate sheets based on reportType
            switch (reportType != null ? reportType : "all") {
                
                case "product":
                    createProductReportSheet(workbook, startDate, endDate);
                    break;
                case "financial":
                    createFinancialReportSheet(workbook, startDate, endDate);
                    break;
                default:
                    break;
            }

            // Set response headers
            resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            resp.setHeader("Content-Disposition", "attachment; filename=\"Bao_cao_" +
                    startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "_den_" +
                    endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".xlsx\"");

            // Write directly to response output stream
            try (ServletOutputStream out = resp.getOutputStream()) {
                workbook.write(out);
                out.flush();
            }

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tạo file Excel");
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    
    private void createStatisticSheet(Workbook workbook, LocalDate startDate, LocalDate endDate) {
        Sheet sheet = workbook.createSheet("Thống kê tổng quan");
        
        // Create styles
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);
        
        int rowNum = 0;
        
        // Title
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO THỐNG KÊ TỔNG QUAN");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
        
        // Date range
        Row dateRow = sheet.createRow(rowNum++);
        dateRow.createCell(0).setCellValue("Từ ngày: " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                                          " đến ngày: " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 3));
        
        rowNum++; // Empty row
        
        // Get data
        List<Order> orders = orderDao.getOrdersByDateRangeAndStatus(Date.valueOf(startDate), Date.valueOf(endDate), "Completed");
        BigDecimal totalIncome = orders.stream()
                .map(Order::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        int totalOrders = orders.size();
        int totalProducts = 0;
        for (Order order : orders) {
            List<OrderDetail> details = orderDetailDao.getAllByOrderId(order.getOrderId());
            totalProducts += details.stream().mapToInt(OrderDetail::getAmount).sum();
        }
        
        // Summary statistics
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Chỉ số");
        headerRow.createCell(1).setCellValue("Giá trị");
        headerRow.getCell(0).setCellStyle(headerStyle);
        headerRow.getCell(1).setCellStyle(headerStyle);
        
        Row incomeRow = sheet.createRow(rowNum++);
        incomeRow.createCell(0).setCellValue("Tổng doanh thu");
        incomeRow.createCell(1).setCellValue(totalIncome.doubleValue());
        incomeRow.getCell(0).setCellStyle(dataStyle);
        incomeRow.getCell(1).setCellStyle(currencyStyle);
        
        Row ordersRow = sheet.createRow(rowNum++);
        ordersRow.createCell(0).setCellValue("Tổng số đơn hàng");
        ordersRow.createCell(1).setCellValue(totalOrders);
        ordersRow.getCell(0).setCellStyle(dataStyle);
        ordersRow.getCell(1).setCellStyle(dataStyle);
        
        Row productsRow = sheet.createRow(rowNum++);
        productsRow.createCell(0).setCellValue("Tổng sản phẩm đã bán");
        productsRow.createCell(1).setCellValue(totalProducts);
        productsRow.getCell(0).setCellStyle(dataStyle);
        productsRow.getCell(1).setCellStyle(dataStyle);
        
        // Auto-size columns
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    
    private void createProductReportSheet(Workbook workbook, LocalDate startDate, LocalDate endDate) {
        Sheet sheet = workbook.createSheet("Báo cáo sản phẩm");
        
        // Create styles
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);
        
        int rowNum = 0;
        
        // Title
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO SẢN PHẨM");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        
        // Date range
        Row dateRow = sheet.createRow(rowNum++);
        dateRow.createCell(0).setCellValue("Từ ngày: " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
                                          " đến ngày: " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
        
        rowNum++; // Empty row
        
        // Get product sales data
        List<Order> orders = orderDao.getOrdersByDateRangeAndStatus(Date.valueOf(startDate), Date.valueOf(endDate), "Completed");
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Order order : orders) {
            orderDetails.addAll(orderDetailDao.getAllByOrderId(order.getOrderId()));
        }
        
        Map<Product, Integer> productSales = new HashMap<>();
        Map<Product, BigDecimal> productRevenue = new HashMap<>();
        
        for (OrderDetail detail : orderDetails) {
            Product product = productDao.getById(detail.getProductId());
            if (product != null) {
                productSales.put(product, productSales.getOrDefault(product, 0) + detail.getAmount());
                BigDecimal revenue = BigDecimal.valueOf(detail.getAmount()).multiply(BigDecimal.valueOf(product.getSalePrice()));
                productRevenue.put(product, productRevenue.getOrDefault(product, BigDecimal.ZERO).add(revenue));
            }
        }
        
        // Sort products by sales
        List<Map.Entry<Product, Integer>> sortedProductSales = new ArrayList<>(productSales.entrySet());
        sortedProductSales.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        // Headers
        Row headerRow = sheet.createRow(rowNum++);
        String[] headers = {"Mã sản phẩm", "Tên sản phẩm", "Giá bán", "Số lượng bán", "Tồn kho", "Doanh thu"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
        
        // Data rows
        for (Map.Entry<Product, Integer> entry : sortedProductSales) {
            Product product = entry.getKey();
            Integer quantitySold = entry.getValue();
            BigDecimal revenue = productRevenue.get(product);
            
            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(product.getProductId());
            dataRow.createCell(1).setCellValue(product.getProductName());
            dataRow.createCell(2).setCellValue(product.getSalePrice());
            dataRow.createCell(3).setCellValue(quantitySold);
            dataRow.createCell(4).setCellValue(product.getStock());
            dataRow.createCell(5).setCellValue(revenue.doubleValue());
            
            // Apply styles
            for (int i = 0; i < 6; i++) {
                if (i == 2 || i == 5) {
                    dataRow.getCell(i).setCellStyle(currencyStyle);
                } else {
                    dataRow.getCell(i).setCellStyle(dataStyle);
                }
            }
        }
        
        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
    private void createFinancialReportSheet(Workbook workbook, LocalDate startDate, LocalDate endDate) {
        Sheet sheet = workbook.createSheet("Báo cáo tài chính");

        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);
        CellStyle currencyStyle = createCurrencyStyle(workbook);

        int rowNum = 0;

        // Tiêu đề
        Row titleRow = sheet.createRow(rowNum++);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("BÁO CÁO TÀI CHÍNH");
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

        // Khoảng thời gian
        Row dateRow = sheet.createRow(rowNum++);
        dateRow.createCell(0).setCellValue("Từ ngày: " + startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                                           " đến ngày: " + endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));

        rowNum++;

        // Dữ liệu đơn hàng
        List<Order> orders = orderDao.getOrdersByDateRangeAndStatus(Date.valueOf(startDate), Date.valueOf(endDate), "Completed");

        BigDecimal totalRevenue = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        List<Object[]> detailRows = new ArrayList<>();

        for (Order order : orders) {
            List<OrderDetail> details = orderDetailDao.getAllByOrderId(order.getOrderId());

            BigDecimal orderTotal = order.getTotalCost();
            LocalDate orderDate = order.getOrderDate().toLocalDate();
            totalRevenue = totalRevenue.add(orderTotal);

            boolean isFirst = true;

            for (OrderDetail detail : details) {
                Product product = productDao.getById(detail.getProductId());
                if (product == null) continue;

                BigDecimal costPrice = BigDecimal.valueOf(product.getCostPrice());
                BigDecimal totalItemCost = costPrice.multiply(BigDecimal.valueOf(detail.getAmount()));
                totalCost = totalCost.add(totalItemCost);

                detailRows.add(new Object[]{
                    isFirst ? order.getOrderId() : "",
                    isFirst ? orderDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "",
                    isFirst ? orderTotal.doubleValue() : "",
                    isFirst ? "Hoàn thành" : "",
                    detail.getProductId(),
                    product.getProductName(),
                    detail.getAmount(),
                    detail.getUnitPrice(),
                    product.getCostPrice(),
                    totalItemCost.doubleValue()
                });
                isFirst = false;
            }
        }

        BigDecimal profit = totalRevenue.subtract(totalCost);
        BigDecimal profitRate = totalRevenue.compareTo(BigDecimal.ZERO) > 0 ?
                profit.multiply(BigDecimal.valueOf(100)).divide(totalRevenue, 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

        // Tóm tắt tài chính
        sheet.createRow(rowNum++); // trống
        Row summaryHeader = sheet.createRow(rowNum++);
        summaryHeader.createCell(0).setCellValue("Tóm Tắt Tài Chính");

        Object[][] summary = {
            {"Tổng doanh thu", totalRevenue.doubleValue()},
            {"Tổng chi phí", totalCost.doubleValue()},
            {"Lợi nhuận", profit.doubleValue()},
            {"Tỷ suất lợi nhuận", profitRate.doubleValue()}
        };

        for (Object[] rowData : summary) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue((String) rowData[0]);
            Cell valCell = row.createCell(1);
            valCell.setCellValue((Double) rowData[1]);
            if (!"Tỷ suất lợi nhuận".equals(rowData[0])) {
                valCell.setCellStyle(currencyStyle);
            } else {
                valCell.setCellStyle(dataStyle);
            }
        }

        rowNum++;
        sheet.createRow(rowNum++).createCell(0).setCellValue("Chi Tiết Đơn Hàng");

        // Header chi tiết
        String[] headers = {
            "Mã Đơn Hàng", "Ngày Đặt Hàng", "Tổng Tiền Đơn", "Trạng Thái",
            "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Giá Vốn", "Tổng Giá Vốn SP"
        };

        Row headerRow = sheet.createRow(rowNum++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Dữ liệu chi tiết đơn hàng
        for (Object[] data : detailRows) {
            Row row = sheet.createRow(rowNum++);
            for (int i = 0; i < data.length; i++) {
                Cell cell = row.createCell(i);
                Object val = data[i];
                if (val instanceof String) {
                    cell.setCellValue((String) val);
                } else if (val instanceof Number) {
                    cell.setCellValue(((Number) val).doubleValue());
                    if (i >= 7 && i <= 9 || i == 2) {
                        cell.setCellStyle(currencyStyle);
                    } else {
                        cell.setCellStyle(dataStyle);
                    }
                }
            }
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }


    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    
    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
    
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
    
    private CellStyle createCurrencyStyle(Workbook workbook) {
        CellStyle style = createDataStyle(workbook);
        style.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));
        return style;
    }
	@RequestMapping("/profile")
	public String showProfile(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		Account acc = (Account) session.getAttribute("account");
		if(acc== null) {
			return "redirect:/admin/login.htm";
		}
		model.addAttribute(employeeDao.getByEmail(acc.getEmail()));
		return "adminview/profile";
	}
	
	@RequestMapping(value="/profile/edit", method=RequestMethod.POST)
	public String edit(HttpServletRequest req, @ModelAttribute("employee") Employee e, RedirectAttributes redirectAttributes) {
		
		// Validate input
		if(!ValidationUtils.isValidEmail(e.getEmail())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Email không hợp lệ!");
			return "redirect:/admin/profile.htm";
		}
		if(!ValidationUtils.isValidName(e.getFullName())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Tên không hợp lệ!");
			return "redirect:/admin/profile.htm";
		}
		if(!ValidationUtils.isValidAddress(e.getAddress())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Địa chỉ không hợp lệ!");
			return "redirect:/admin/profile.htm";
		}
		if(!ValidationUtils.isValidDate(e.getBirthDate())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Ngày sinh không hợp lệ!");
			return "redirect:/admin/profile.htm";
		}
		if(!ValidationUtils.isValidPhone(e.getPhone())) {
			redirectAttributes.addFlashAttribute("errorMessage", "Số điện thoại không hợp lệ!");
			return "redirect:/admin/profile.htm";
		}
		try
		{
			HttpSession session = req.getSession();
			Account acc = (Account) session.getAttribute("account");
			Employee emp = employeeDao.getByEmail(acc.getEmail());
			emp.setAddress(e.getAddress());
			emp.setBirthDate(e.getBirthDate());
			emp.setFullName(e.getFullName());
			emp.setEmail(e.getEmail());
			emp.setEducationLevel(e.getEducationLevel());
			emp.setGender(e.getGender());
			employeeDao.update(emp);
			session.setAttribute("name", emp.getFullName());
	        redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa tài khoản thành công!"); 

			return "redirect:/admin/profile.htm";
		}
		catch (Exception ex)
		{
			System.out.println("Test1");
	        redirectAttributes.addFlashAttribute("errorMessage", "Chỉnh sửa tài khoản thất bại!"); 
			ex.printStackTrace();
			System.out.println("Test2");
			return "redirect:/admin/profile.htm";
			
		}
		
	}
	@RequestMapping(value="/profile/changepass", method= RequestMethod.POST)
	public String changePass(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		// Validate input
		if(!ValidationUtils.isValidPassword(req.getParameter("newpass"))) {
			redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu mới không hợp lệ!");
			return "redirect:/admin/profile.htm";
		}
		if(!ValidationUtils.isValidPassword(req.getParameter("confirmpass"))) {
			redirectAttributes.addFlashAttribute("errorMessage", "Xác nhận mật khẩu không hợp lệ!");
			return "redirect:/admin/profile.htm";
		}
		if(!req.getParameter("newpass").equals(req.getParameter("confirmpass"))) {
			redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
			return "redirect:/admin/profile.htm";
		}
		if(req.getParameter("newpass").equals(req.getParameter("pass"))) {
			redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu mới không được trùng với mật khẩu cũ!");
			return "redirect:/admin/profile.htm";
		}
		if(!ValidationUtils.isValidPassword(req.getParameter("pass"))) {
			redirectAttributes.addFlashAttribute("errorMessage", "Mật khẩu mới quá yếu, vui lòng chọn mật khẩu khác!");
			return "redirect:/admin/profile.htm";
		}
		try
		{
			String pass= req.getParameter("pass");
			String newPass = req.getParameter("newpass");
			String confirmPass = req.getParameter("confirmpass");
			HttpSession session = req.getSession();
			Account acc = (Account) session.getAttribute("account");
			if(getMD5Hash(pass).equals(acc.getPassword())) {
				acc.setPassword(getMD5Hash(confirmPass));
				accountDao.update(acc);
			}
			else {
				throw new Exception("Mật khẩu không đúng hoặc không khớp");
			}
	        redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công!"); 

			return "redirect:/admin/profile.htm";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        redirectAttributes.addFlashAttribute("errorMessage", "Đổi mật khẩu thất bại!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/admin/profile.htm";
			
		}
		
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.removeAttribute("account");
		session.removeAttribute("name");
		return "redirect:/admin/login.htm";
	}
	
}

