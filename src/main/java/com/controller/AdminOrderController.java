package com.controller;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

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
import com.entity.XMailer;

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
	
	@Autowired
	XMailer xmailer;
	
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

	
	// -- Order --
	@RequestMapping("/order")
	public String showOrders(@RequestParam("status") String status, HttpServletRequest req) {
		try
		{
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
		catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Có lỗi khi tải danh sách hóa đơn!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "adminview/order/orderConfirm/index";
			
		}

			
	}
	
	@RequestMapping(value = "/order/add", method= RequestMethod.GET)
	public String addOrder(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			model.addAttribute("order", new Order());
		    
		    List<Product> products = productDao.getAll();
		    List<Discount> discounts = discountDao.getAll();
		    String nextOrderId = orderDao.generateNextOrderId();
		    
		    model.addAttribute("products", products);
		    model.addAttribute("discounts", discounts);
		    model.addAttribute("nextOrderId", nextOrderId);
		    
			return "adminview/order/add";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải giao diện đặt đơn hàng!"); 
			e.printStackTrace();
			return "redirect:/admin/order.htm?status=processing";
			
		}
	
	}
	
	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public String addOrderPost(@ModelAttribute("order") Order order, HttpServletRequest req, RedirectAttributes redirectAttributes, HttpSession session) {
		try
		{
			// Tạo tài khoản khách hàng
			Account acc;
			Customer cus;
			if(accountDao.checkExistByEmail(order.getUserEmail()))
			{
				acc = accountDao.getByEmail(order.getUserEmail());
				cus = customerDao.getByEmail(order.getUserEmail());
			}
			else
			{
				acc = new Account(order.getUserEmail(), getMD5Hash("1111"), null, "RG002", "Active", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
				accountDao.add(acc);
				
				cus = new Customer(null, req.getParameter("userName"), order.getUserEmail(),  order.getShipAddress(), "Active", Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));
				customerDao.add(cus); 
			}
			
			
			
			// Đặt các giá trị mặc định
		    if (order.getStatus() == null) {
		        order.setStatus("Processing");
		    }
		    order.setConfirmedBy((String)session.getAttribute("email"));
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
		    
		    
		    
		    String from = "no-reply@autopart.com"; // Địa chỉ email gửi
	        String to = acc.getEmail(); // Địa chỉ email người nhận
	        String subject = "Đặt đơn hàng thành công"; // Tiêu đề email
	     // Nội dung email
	        String body = String.format(
	            "Chào bạn,\n\n" +
	            "Cảm ơn bạn đã đặt hàng tại AutoPart! Đơn hàng của bạn đã được đặt thành công với các thông tin sau:\n\n" +
	            "<strong>Tên khách hàng:</strong> %s\n" +
	            "<strong>Số điện thoại:</strong> %s\n" +
	            "<strong>Mã đơn hàng:</strong> %s\n" +
	            "<strong>Tổng tiền:</strong> %s ₫\n" +
	            "<strong>Địa chỉ giao hàng:</strong> %s\n" +
	            "<strong>Loại vận chuyển:</strong> %s\n\n" +
	            "Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất và thông báo khi hàng được giao. " +
	            "Bạn có thể theo dõi trạng thái đơn hàng trong phần \"Tài khoản\" trên website của chúng tôi.\n\n" +
	            "Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc hotline.\n\n" +
	            "Trân trọng,\n" +
	            "Đội ngũ AutoPart",
	            cus.getFullName() != null ? cus.getFullName() : "Không có",
	            cus.getPhone(),
	            order.getOrderId(),
	            order.getTotalCost(),
	            order.getShipAddress() != null ? order.getShipAddress() : "Không có",
	            order.getShippingType()
	        );
	        
	       xmailer.send(from, to, subject, body);
	       
	       
	       
	        redirectAttributes.addFlashAttribute("successMessage", "Thêm đơn hàng thành công!"); 
		    return "redirect:/admin/order.htm?status=processing";
		}
		catch (Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);

			System.out.println("Test1");
	        redirectAttributes.addFlashAttribute("errorMessage", "Thêm đơn hàng thất bại!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect" + referer;
			
		}
		
	}

	@RequestMapping(value = "/order/edit", method = RequestMethod.GET)
	public String editOrder(@RequestParam("orderId") String orderId, Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
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
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải đơn hàng!"); 
			e.printStackTrace();
			return "redirect:/admin/order.htm?status=processing";
			
		}

	    	
	}
	
	@RequestMapping(value = "/order/edit", method = RequestMethod.POST)
	public String editOrderPost(@ModelAttribute("order") Order order, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			// Tìm thông tin khách hàng
			Customer existedCus = customerDao.getByEmail(order.getUserEmail());
			if (existedCus == null) {
				Account acc = new Account(order.getUserEmail(), getMD5Hash("1111"), null, "RG002", "Active",  Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false); 
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
	public String detailOrder(@RequestParam("orderId") String orderId, Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
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
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải đơn hàng!"); 
			e.printStackTrace();
			return "redirect:/admin/order.htm?status=processing";
			
		}
	
	}
	
	@RequestMapping(value = "/order/changeStatus", method= RequestMethod.POST)
	public String changeStatusOrder(@RequestParam("orderId") String orderId, @RequestParam("status") String status, Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) throws Exception {
		try
		{
			Order order = orderDao.getById(orderId);
			
			String from = "no-reply@autopart.com";
	        String to = order.getUserEmail();
	        String subject;
	        String body;

			if (order != null) {
				if ("Pending".equals(status)) {
					order.setStatus("Processing");
					orderDao.update(order);	
					
					subject = "Đơn hàng đã được xác nhận";
	                body = String.format(
	                    "Chào bạn,<br><br>" +
	                    "Đơn hàng của bạn tại AutoPart đã được xác nhận thành công! Dưới đây là thông tin chi tiết về đơn hàng:<br><br>" +
	                    "<strong>Mã đơn hàng:</strong> %s<br>" +
	                    "<strong>Tổng tiền:</strong> %s ₫<br>" +
	                    "<strong>Địa chỉ giao hàng:</strong> %s<br>" +
	                    "<strong>Loại vận chuyển:</strong> %s<br><br>" +
	                    "Đơn hàng của bạn hiện đang được chuẩn bị và sẽ sớm được giao đến bạn. " +
	                    "Bạn có thể theo dõi trạng thái đơn hàng trong phần \"Tài khoản\" trên website của chúng tôi.<br><br>" +
	                    "Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc hotline.<br><br>" +
	                    "Trân trọng,<br>" +
	                    "Đội ngũ AutoPart",
	                    order.getOrderId(),
	                    order.getTotalCost(),
	                    order.getShipAddress(),
	                    order.getShippingType()
	                );

	                xmailer.send(from, to, subject, body);
					
			        redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng đã được xác nhận!"); 
					return "redirect:/admin/order.htm?status=pending";
				}
				else if ("Cancelled".equals(status)) {
					order.setStatus("Cancelled");
					orderDao.update(order);	
					
					subject = "Đơn hàng đã bị hủy";
	                body = String.format(
	                    "Chào bạn,<br><br>" +
	                    "Đơn hàng của bạn tại AutoPart đã bị hủy! Dưới đây là thông tin chi tiết về đơn hàng:<br><br>" +
	                    "<strong>Mã đơn hàng:</strong> %s<br>" +
	                    "<strong>Tổng tiền:</strong> %s ₫<br>" +
	                    "<strong>Địa chỉ giao hàng:</strong> %s<br>" +
	                    "<strong>Loại vận chuyển:</strong> %s<br><br>" +
	                    "Đơn hàng của bạn đã bị hủy vì lý do không xác định. " +
	                    "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc hotline.<br><br>" +
	                    "Trân trọng,<br>" +
	                    "Đội ngũ AutoPart",
	                    order.getOrderId(),
	                    order.getTotalCost(),
	                    order.getShipAddress(),
	                    order.getShippingType()
	                );

	                xmailer.send(from, to, subject, body);
					
			        redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng đã được hủy!"); 
					return "redirect:/admin/order.htm?status=history";
				}
				else if ("Processing".equals(status)) {
					order.setStatus("Shipping");
					orderDao.update(order);	
					
					subject = "Đơn hàng đang được giao";
	                body = String.format(
	                    "Chào bạn,<br><br>" +
	                    "Đơn hàng của bạn tại AutoPart đang được giao đến bạn! Dưới đây là thông tin chi tiết về đơn hàng:<br><br>" +
	                    "<strong>Mã đơn hàng:</strong> %s<br>" +
	                    "<strong>Tổng tiền:</strong> %s ₫<br>" +
	                    "<strong>Địa chỉ giao hàng:</strong> %s<br>" +
	                    "<strong>Loại vận chuyển:</strong> %s<br><br>" +
	                    "Đơn hàng của bạn đã được chuyển đến đơn vị vận chuyển và sẽ sớm đến tay bạn. " +
	                    "Bạn có thể theo dõi trạng thái giao hàng trong phần \"Tài khoản\" trên website của chúng tôi hoặc liên hệ đơn vị vận chuyển để biết thêm chi tiết.<br><br>" +
	                    "Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc hotline.<br><br>" +
	                    "Trân trọng,<br>" +
	                    "Đội ngũ AutoPart",
	                    order.getOrderId(),
	                    order.getTotalCost(),
	                    order.getShipAddress(),
	                    order.getShippingType()
	                );

	                xmailer.send(from, to, subject, body);
					
			        redirectAttributes.addFlashAttribute("successMessage", "Chuyển trạng thái giao hàng thành công"); 
					return "redirect:/admin/order.htm?status=delivery";
				}
				else if ("Shipping".equals(status)) {
					order.setStatus("Completed");
					orderDao.update(order);	
					
					subject = "Đơn hàng đã hoàn tất";
	                body = String.format(
	                    "Chào bạn,<br><br>" +
	                    "Đơn hàng của bạn tại AutoPart đã hoàn tất! Dưới đây là thông tin chi tiết về đơn hàng:<br><br>" +
	                    "<strong>Mã đơn hàng:</strong> %s<br>" +
	                    "<strong>Tổng tiền:</strong> %s ₫<br>" +
	                    "<strong>Địa chỉ giao hàng:</strong> %s<br>" +
	                    "<strong>Loại vận chuyển:</strong> %s<br><br>" +
	                    "Đơn hàng của bạn đã được giao thành công. Cảm ơn bạn đã mua sắm tại AutoPart!<br><br>" +
	                    "Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc hotline.<br><br>" +
	                    "Trân trọng,<br>" +
	                    "Đội ngũ AutoPart",
	                    order.getOrderId(),
	                    order.getTotalCost(),
	                    order.getShipAddress(),
	                    order.getShippingType()
	                );

	                xmailer.send(from, to, subject, body);
					
			        redirectAttributes.addFlashAttribute("successMessage", "Đơn hàng hoàn thành"); 
					return "redirect:/admin/order.htm?status=history";
				}				
			}
		    
			return "redirect:/admin/order.htm?status=processing";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi thay đổi trạng thái đơn hàng!"); 
			e.printStackTrace();
			return "redirect:/admin/order.htm?status=processing";
			
		}

	}
}
