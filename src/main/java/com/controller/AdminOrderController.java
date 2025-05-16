package com.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Account_DAO;
import com.dao.Customer_DAO;
import com.dao.Discount_DAO;
import com.dao.OrderDetail_DAO;
import com.dao.Order_DAO;
import com.dao.Product_DAO;
import com.entity.Account;
import com.entity.Customer;
import com.entity.Discount;
import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {
	
	@Autowired
	Order_DAO orderDao;
	
	@Autowired
	Product_DAO productDao;
	
	@Autowired
	Discount_DAO discountDao;
	
	@Autowired
	Account_DAO accountDao;
	
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	OrderDetail_DAO orderDetailDao;
	
	// -- Order --
	@RequestMapping("/order")
	public String showOrders(@RequestParam("status") String status, HttpServletRequest req) {
		List<Order> orders;
		System.out.println("status: "+status);
		switch (status) {
			case "pending":
				orders = orderDao.getOrderByStatus("Pending");
				req.setAttribute("orders", orders);
				return "adminview/order/orderConfirm/index";
			case "processing":
				orders = orderDao.getOrderByStatus("Processing");
				req.setAttribute("orders", orders);
				return "adminview/order/orderProcessing";
			case "delivery":
				orders = orderDao.getOrderByStatus("Shipping");
				req.setAttribute("orders", orders);
				return "adminview/order/orderDeli";
			default:
				orders = orderDao.getOrderByStatus("History");
				req.setAttribute("orders", orders);
				return "adminview/order/orderHistory";
		}
	}
	
	@RequestMapping(value = "/order/add", method= RequestMethod.GET)
	public String addOrder(Model model, HttpServletRequest req) {
		model.addAttribute("order", new Order());
	    
	    List<Product> products = productDao.getAll();
	    List<Discount> discounts = discountDao.getAll();
	    String nextOrderId = orderDao.generateNextOrderId();
	    
	    model.addAttribute("products", products);
	    model.addAttribute("discounts", discounts);
	    model.addAttribute("nextOrderId", nextOrderId);
	    
		return "adminview/order/add";
	}
	
	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public String addOrderPost(@ModelAttribute("order") Order order, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			// Tạo tài khoản khách hàng
			Account acc = new Account(order.getUserEmail(), "1111", null, "RG002", "Active", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
			accountDao.add(acc);
			
			// Lưu khách hàng
			Customer cus = new Customer(null, req.getParameter("userName"), order.getUserEmail(),  order.getShipAddress(), "Active", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
			customerDao.add(cus);
			
			// Đặt các giá trị mặc định
		    if (order.getStatus() == null) {
		        order.setStatus("Processing");
		    }
		    order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));

		    // Chuyển totalCost từ String sang BigDecimal
		    String totalCostStr = req.getParameter("totalCost");
		    if (totalCostStr != null && !totalCostStr.isEmpty()) {
		        order.setTotalCost(new BigDecimal(totalCostStr));
		    }

		    // Lưu Order vào database
		    orderDao.add(order);

		    // Lưu chi tiết đơn hàng
		    if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
		        for (OrderDetail detail : order.getOrderDetails()) {
		        	// Cập nhật số lượng sản phẩm
		        	Product product = productDao.getById(detail.getProductId());
		        	product.setStock(Math.abs(product.getStock() - detail.getAmount()));
		        	productDao.update(product);
		        	
		        	// Lưu chi tiết đơn hàng
		            detail.setOrderId(order.getOrderId()); // Gán orderId cho chi tiết
		            orderDetailDao.add(detail); // Lưu từng chi tiết
		        }
		    }
	        redirectAttributes.addFlashAttribute("successMessage", "Thêm đơn hàng thành công!"); 

		    return "redirect:/admin/order.htm?status=processing";
		}
		catch (Exception e)
		{
			System.out.println("Test1");
	        redirectAttributes.addFlashAttribute("errorMessage", "Thêm đơn hàng thất bại!"); 
			e.printStackTrace();
			System.out.println("Test2");
            return "redirect:/admin/order/add.htm";
			
		}
		
	}

	@RequestMapping(value = "/order/edit", method = RequestMethod.GET)
	public String editOrder(@RequestParam("orderId") String orderId, Model model, HttpServletRequest req) {
	    Order order = orderDao.getById(orderId);
	    if (order == null) {
	        return "redirect:/admin/order.htm?status=processing"; // Nếu không tìm thấy đơn hàng
	    }
	    
	    Customer cus = customerDao.getByEmail(order.getUserEmail());

	    // Lấy danh sách chi tiết đơn hàng
	    List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(orderId);
	    order.setOrderDetails(orderDetails); // Gán danh sách chi tiết vào order

	    // Lấy danh sách sản phẩm để thêm
	    List<Product> products = productDao.getAll();
	    
	    // Lấy danh sách khuyến mãi
	    List<Discount> discounts = discountDao.getAll();
	    
	    model.addAttribute("order", order);
	    model.addAttribute("products", products);
	    model.addAttribute("discounts", discounts);
	    req.setAttribute("title", "Sửa Đơn Hàng");
	    req.setAttribute("userName", cus.getFullName());
	    
	    return "adminview/order/orderConfirm/edit";
	}
	
	@RequestMapping(value = "/order/edit", method = RequestMethod.POST)
	public String editOrderPost(@ModelAttribute("order") Order order, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			// Tìm thông tin khách hàng
			Customer existedCus = customerDao.getByEmail(order.getUserEmail());
			if (existedCus == null) {
				Account acc = new Account(order.getUserEmail(), "1111", null, "RG002", "Active",  Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false); 
				accountDao.add(acc);
				
				// Lưu khách hàng
				Customer cus = new Customer(null, req.getParameter("userName"), order.getUserEmail(), order.getShipAddress(), "Active",Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()) );
				customerDao.add(cus);
			}
			
		    // Đặt các giá trị mặc định nếu cần
		    if (order.getStatus() == null) {
		        order.setStatus("Pending");
		    }
		    if (order.getOrderDate() == null) {
		        order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
		    }

		    // Chuyển totalCost từ String sang BigDecimal (nếu cần)
		    String totalCostStr = req.getParameter("totalCost");
		    if (totalCostStr != null && !totalCostStr.isEmpty()) {
		        order.setTotalCost(new BigDecimal(totalCostStr));
		    }

		    // Cập nhật Order
		    orderDao.update(order);

		    // Cập nhật chi tiết đơn hàng
		    if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
		        // Xóa chi tiết cũ (nếu cần) để tránh trùng lặp
		    	List<OrderDetail> odList = orderDetailDao.getAllByOrderId(order.getOrderId());
		    	for (OrderDetail detail : odList) {
		    		Product product = productDao.getById(detail.getProductId());
		        	product.setStock(Math.abs(product.getStock() + detail.getAmount()));
		        	productDao.update(product);
		    	}
		        orderDetailDao.deleteByOrderId(order.getOrderId());
		        
		        // Lưu lại chi tiết mới
		        for (OrderDetail detail : order.getOrderDetails()) {
		        	// Cập nhật số lượng sản phẩm
		        	Product product = productDao.getById(detail.getProductId());
		        	product.setStock(Math.abs(product.getStock() - detail.getAmount()));
		        	productDao.update(product);
		        	
		            detail.setOrderId(order.getOrderId()); // Đảm bảo orderId được gán
		            orderDetailDao.add(detail);
		        }
		    }
	        redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa đơn hàng thành công!"); 

		    return "redirect:/admin/order.htm?status=pending";
		}
		catch (Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);
	        redirectAttributes.addFlashAttribute("errorMessage", "Chỉnh sửa đơn hàng thất bại!"); 
			e.printStackTrace();
            return "redirect:" + referer;
			
		}
		
	}
	
	@RequestMapping(value = "/order/detail", method= RequestMethod.GET)
	public String detailOrder(@RequestParam("orderId") String orderId, Model model, HttpServletRequest req) {
		Order order = orderDao.getById(orderId);
	    if (order == null) {
	        return "redirect:/admin/order.htm?status=processing"; 
	    }
	    
	    Customer cus = customerDao.getByEmail(order.getUserEmail());

	    // Lấy danh sách chi tiết đơn hàng
	    List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(orderId);
	    order.setOrderDetails(orderDetails); // Gán danh sách chi tiết vào order
	    
	    // Lấy danh sách khuyến mãi
	    List<Discount> discounts = discountDao.getAll();
	    
	    model.addAttribute("order", order);
	    model.addAttribute("discounts", discounts);
	    req.setAttribute("userName", cus.getFullName());
		return "adminview/order/detail";
	}
	
	@RequestMapping(value = "/order/changeStatus", method= RequestMethod.POST)
	public String changeStatusOrder(@RequestParam("orderId") String orderId, @RequestParam("status") String status, Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) throws Exception {
		Order order = orderDao.getById(orderId);

		if (order != null) {
			if ("Pending".equals(status)) {
				order.setStatus("Processing");
				orderDao.update(order);	
		        redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng đã được xác nhận!"); 
				return "redirect:/admin/order.htm?status=pending";
			}
			else if ("Cancelled".equals(status)) {
				order.setStatus("Cancelled");
				orderDao.update(order);	
		        redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng đã được hủy!"); 
				return "redirect:/admin/order.htm?status=history";
			}
			else if ("Processing".equals(status)) {
				order.setStatus("Shipping");
				orderDao.update(order);	
		        redirectAttributes.addFlashAttribute("successMessage", "Chuyển trạng thái giao hàng thành công"); 
				return "redirect:/admin/order.htm?status=delivery";
			}
			else if ("Shipping".equals(status)) {
				order.setStatus("Completed");
				orderDao.update(order);	
		        redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng hoàn thành"); 
				return "redirect:/admin/order.htm?status=history";
			}				
		}
	    
		return "redirect:/admin/order.htm?status=processing";
	}
}
