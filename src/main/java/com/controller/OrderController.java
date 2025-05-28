package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Cart_DAO;
import com.dao.Customer_DAO;
import com.dao.Discount_DAO;
import com.dao.OrderDetail_DAO;
import com.dao.Order_DAO;
import com.dao.Product_DAO;
import com.entity.Account;
import com.entity.Cart;
import com.entity.Customer;
import com.entity.Discount;
import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;
import com.entity.XMailer;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Controller
public class OrderController {
	@Autowired
	Order_DAO orderDao;
	
	
	@Autowired
	OrderDetail_DAO orderDetailDao;
	
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Cart_DAO cartDao;
	
	@Autowired
	Product_DAO productDao;
	
	@Autowired
	Discount_DAO discountDao;
	
	@Autowired
	XMailer xmailer;
	
	@RequestMapping(value="/order/create", method = RequestMethod.POST)
	public String showOrder(HttpServletRequest req,RedirectAttributes redirectAttributes) {
		System.out.println("Creating order ------------------------------------------------------------------------------------------------------------------------------------------------");
		
		try
		{
			HttpSession session = req.getSession();
			Account acc =(Account) session.getAttribute("user");
			if(acc ==null)
			{
				return "redirect:/login.htm";
			}
			

			Customer cus = customerDao.getByEmail(acc.getEmail());
			Cart cart =cartDao.getById(cus.getCartId());
			
			//get all products that user choosed
			Map<String,Integer> productsInCart =cart.getProducts();
			Map<Product,Integer> products= new HashMap<Product, Integer>();
			
	        StringBuilder query =  new StringBuilder("redirect:/order.htm?");
	        
			for(String key : productsInCart.keySet()) {
				//get id and quantity from req
				String quantity = req.getParameter(key);
				System.out.println(req.getParameter(key));
				if(quantity != null) {
					if(Integer.parseInt(quantity.trim()) > productDao.getById(key).getStock())
					{
						String referer =req.getHeader("Referer");
						redirectAttributes.addFlashAttribute("errorMessage", "Số lượng sản phẩm đã hết");  
						return "redirect:" + referer;
					}
					products.put(productDao.getById(key),Integer.parseInt(quantity.trim()));
					query.append(key + "=" + Integer.parseInt(quantity.trim()) +"&");

				}
				
			}		
	        query.deleteCharAt(query.length()-1);
	        System.out.println(query);

			
			Double totalCost= Double.valueOf(req.getParameter("totalCost"));
			String shipAddress= req.getParameter("shipAddress");
			String shippingType = req.getParameter("shippingType");
			switch(shippingType) {
			  case "20000":
				  shippingType ="Normal";
			    break;
			  case "50000":
				  shippingType ="Express";
			    break;
			  default:
				  shippingType ="Economy";
			}
			
			String discountId =( req.getParameter("discountId")) != ""? req.getParameter("discountId") : null;
			System.out.println(discountId);
			Discount discount = discountDao.getById(discountId);
			
			System.out.println("Shipping Type: "+shippingType);
			if(shippingType =="") 
			{
		        redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn loại vận chuyển");  
		       System.out.println(query);
		        
		        return query.toString();
		    }
			
			if(discount!=null && discount.getUsageLimit()==0) 
			{
		        redirectAttributes.addFlashAttribute("errorMessage", "Số lượng mã giảm giá đã hết");  
		       System.out.println(query);
		        
		        return query.toString();
		    }
			
			
		
////				create new order then add it to database
			Order newOrder = new Order(orderDao.generateNextOrderId(),discountId,acc.getEmail() , shipAddress, shippingType ,BigDecimal.valueOf(totalCost), Date.valueOf( LocalDate.now()), null, "Pending", null, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
			orderDao.add(newOrder);
			if(discount!=null) 
			{
				discountDao.discountUsed(acc.getEmail(), discountId);
				discount.setUsageLimit(discount.getUsageLimit()-1);
		    }
			
			//update cart
			for(Product p : products.keySet()) {
				OrderDetail newOrderDetail = new OrderDetail(newOrder.getOrderId(),p.getProductId(),p.getProductName(),products.get(p),BigDecimal.valueOf( p.getSalePrice()));
				orderDetailDao.add(newOrderDetail);
				p.setStock(p.getStock()-products.get(p));
				productDao.update(p);
			}
			for(Product p: products.keySet()) {
				productsInCart.remove(p.getProductId());
			}
			cart.setProducts(productsInCart);
			cartDao.update(cart);
			Map<Product,Integer> p= new HashMap<Product, Integer>();
			for(String key : productsInCart.keySet()) {
				p.put(productDao.getById(key),productsInCart.get(key));
			}
			session.setAttribute("productInCart",p);			
			
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
	            "<strong>Tổng tiền:</strong> %,d ₫\n" +
	            "<strong>Địa chỉ giao hàng:</strong> %s\n" +
	            "<strong>Loại vận chuyển:</strong> %s\n\n" +
	            "Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất và thông báo khi hàng được giao. " +
	            "Bạn có thể theo dõi trạng thái đơn hàng trong phần \"Tài khoản\" trên website của chúng tôi.\n\n" +
	            "Nếu có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi qua email hoặc hotline.\n\n" +
	            "Trân trọng,\n" +
	            "Đội ngũ AutoPart",
	            cus.getFullName(),
	            cus.getPhone(),
	            newOrder.getOrderId(),
	            newOrder.getTotalCost(),
	            newOrder.getShipAddress(),
	            newOrder.getShippingType()
	        );
	        
	       xmailer.send(from, to, subject, body);

			return "success";
		
			
		}
		catch(Exception e)
		{			
			String referer =req.getHeader("Referer");
			System.out.println(referer);
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi đặt đơn hàng");
			e.printStackTrace();
			return "redirect:" + referer;
		}

		
	}
	
	@RequestMapping("/order/detail")
	public String showDetail(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc==null){
			return "redirect:/login.htm";
		}
		
		String orderId=req.getParameter("orderId");
		Order order= orderDao.getById(orderId);
		
		List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(orderId);
		
		req.setAttribute("order", order);
		req.setAttribute("products", orderDetails);
		return "orderdetail";
	}
	
	@RequestMapping(value="/order",method=RequestMethod.GET)// redirect to payment page
	public String showCart(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		
		//check if user logged in
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc == null ) {
			return "redirect:/login.htm";
		}
		
		//check if user has products in cart
		//get all product of user
		Customer cus = customerDao.getByEmail(acc.getEmail());
		Cart cart= cartDao.getById(cus.getCartId());
		
		Map<String,Integer> productsInCart =cart.getProducts();
		System.out.println(productsInCart);

		
		String[] pids = new String[productsInCart.size()];
		
		pids= productsInCart.keySet().toArray(pids);
		
	
		
		for(String key : pids) {
			// remove products that are not selected
			if(req.getParameter(key)==null) {
				productsInCart.remove(key);
			}
		}
		// show selected products
		Map<Product,Integer> products= new HashMap<Product, Integer>();
		for(String key : productsInCart.keySet()) {
			products.put(productDao.getById(key),productsInCart.get(key));
		}
		
		System.out.println(products);
		
		if(products.size() == 0) {
			redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng trống");
			return "redirect:/index.htm";
		}
		req.setAttribute("products", products);
		
		List<Discount> discounts = discountDao.getByCustomer(acc.getEmail());
		req.setAttribute("discounts", discounts);
		req.setAttribute("user", cus);

		return "order";
	}
	
	@RequestMapping(value ="order/cancel", method = RequestMethod.GET)
	public String cancel(HttpServletRequest req) throws Exception
	{
		String orderId = req.getParameter("orderId");
		System.out.println(orderId);
		
		Order order = orderDao.getById(orderId);
		for(OrderDetail detail: order.getOrderDetails())
		{
			System.out.println(detail);
			Product product = productDao.getById(detail.getProductId());
			System.out.println(product);
			product.setStock(product.getStock()+detail.getAmount());
			System.out.println(product);
			productDao.update(product);
		}
		order.setDeleted(true);
		orderDao.update(order);
		
		
		return "redirect:/account.htm";
	}
	
	@RequestMapping(value ="order/edit", method = RequestMethod.GET)
	public String showEditForm(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc==null){
			return "redirect:/login.htm";
		}
		
		String orderId=req.getParameter("orderId");
		Order order= orderDao.getById(orderId);
				
		Map<Product,Integer> products= new HashMap<Product, Integer>();
		
		for(OrderDetail detail: order.getOrderDetails())
		{
			System.out.println(detail);
			Product product = productDao.getById(detail.getProductId());
			products.put(product, detail.getAmount());
		}
		
		List<Discount> discounts = discountDao.getByCustomer(acc.getEmail());
		Discount discount = discountDao.getById(order.getDiscountId());
		System.out.println(discount);
		if(discount!=null)
		{
			discounts.add(discount);
		}
		
		
		//System.out.println(discounts);
		req.setAttribute("discounts", discounts);
		req.setAttribute("products", products);
		req.setAttribute("order", order);

		
		return "orderEdit";
	}
	
	
	@RequestMapping(value ="order/edit", method = RequestMethod.POST)
	public String orderEdit(HttpServletRequest req, RedirectAttributes redirectAttributes)
	{
		System.out.println("Editing order ------------------------------------------------------------------------------------------------------------------------------------------------");
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc==null){
			return "redirect:/login.htm";
		}
	
		Order order = orderDao.getById(req.getParameter("orderId"));
		//get all products that user choosed
		List<OrderDetail> productsInOrder = order.getOrderDetails();
		Map<Product,Integer> products= new HashMap<Product, Integer>();
		
		String referer = req.getHeader("Referer");
		System.out.println(referer);

        
		for(OrderDetail key : productsInOrder) {
			//get id and quantity from req
			String quantity = req.getParameter(key.getProductId());
			if(quantity != null) {
				products.put(productDao.getById(key.getProductId()),Integer.parseInt(quantity));
			}
			else
			{
				orderDetailDao.delete(key.getOrderId(), key.getProductId());
			}
			
		}		
     
        Double totalCost= Double.valueOf(req.getParameter("totalCost"));
		String shipAddress= req.getParameter("shipAddress");
		String shippingType = req.getParameter("shippingType");
		switch(shippingType) {
		  case "20000":
			  shippingType ="Normal";
		    break;
		  case "50000":
			  shippingType ="Express";
		    break;
		  default:
			  shippingType ="Economy";
		}
		
		String discountId =( req.getParameter("discountId")) != ""? req.getParameter("discountId") : null;
		System.out.println(discountId);
		Discount discount = discountDao.getById(discountId);
		Discount orderDiscount = discountDao.getById(order.getDiscountId());
		System.out.println(orderDiscount);
		//orderDiscount.setUsageLimit(orderDiscount.getUsageLimit()+1);
		
		System.out.println("Shipping Type: "+shippingType);
		
		if(shippingType =="") 
		{
			redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn loại vận chuyển");  
	        
	        return "redirect:"+referer;
	    }
		
		if(discount!=null && discount.getUsageLimit()==0) 
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Số lượng mã giảm giá đã hết");  
	        
	        return "redirect:"+referer;
	    }
		
		Order newOrder = new Order(req.getParameter("orderId"),discountId,acc.getEmail() , shipAddress, shippingType ,BigDecimal.valueOf(totalCost), Date.valueOf( LocalDate.now()), null, "Pending", null, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
		
		if (discountId != null && order.getDiscountId() == null) {
	        // Case 2: No previous discount, apply new discount
			System.out.println(discount.getUsageLimit());
			//System.out.println(orderDiscount.getUpdatedAt());
	        discountDao.discountUsed(acc.getEmail(), discountId);
	        discount.setUsageLimit(discount.getUsageLimit() - 1);
	        discountDao.update(discount);
			System.out.println(discount.getUsageLimit());
			//System.out.println(orderDiscount.getUpdatedAt());
	    } 
		else if (discountId == null && order.getDiscountId() != null) {
	        // Case 3: Previous discount, remove discount
			//System.out.println(discount.getUsageLimit());
			System.out.println(orderDiscount.getUsageLimit());
	        discountDao.deleteDiscountUsed(order.getDiscountId(), acc.getEmail());
	        orderDiscount.setUsageLimit(orderDiscount.getUsageLimit() + 1);
	        discountDao.update(orderDiscount);
			//System.out.println(discount.getUsageLimit());
			System.out.println(orderDiscount.getUsageLimit());
	    }
		else if(discount !=null && orderDiscount!=null && !discount.getDiscountId().equals(orderDiscount.getDiscountId()))
		{
			System.out.println(discount.getUsageLimit());
			System.out.println(orderDiscount.getUsageLimit());
			discountDao.deleteDiscountUsed(order.getDiscountId(), acc.getEmail());
			discountDao.discountUsed(acc.getEmail(), discountId);
			discount.setUsageLimit(discount.getUsageLimit() - 1);
		    discountDao.update(discount);
		    orderDiscount.setUsageLimit(orderDiscount.getUsageLimit() + 1);
	        discountDao.update(orderDiscount);	
	        System.out.println(discount.getUsageLimit());
			System.out.println(orderDiscount.getUsageLimit());
		}
		
//		for(Product p : products.keySet()) {
//			OrderDetail newOrderDetail = new OrderDetail(newOrder.getOrderId(),p.getProductId(),p.getProductName(),products.get(p),BigDecimal.valueOf( p.getSalePrice()));
//			//newOrder.getOrderDetails().add(newOrderDetail);
//			orderDetailDao.update(newOrderDetail);
//		}
		
		System.out.println(orderDao.getById(req.getParameter("orderId")));
		System.out.println(newOrder.getOrderDetails());
		System.out.println(newOrder);
		//orderDao.update(newOrder);
		
		return "redirect:/account.htm";
	}
	
	
	@RequestMapping(value ="order/edit/remove-product", method = RequestMethod.GET)
	public String removeProduct(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc==null){
			return "redirect:/login.htm";
		}
		
		orderDetailDao.delete(req.getParameter("orderId"), req.getParameter("productId"));
		
		String referer = req.getHeader("Referer");
		System.out.println(referer);
		String productId=  req.getParameter("productId");
		
		referer = referer.replaceAll("&" + req.getParameter("productId") +"=[^&]*", "");
		System.out.println(referer);
		
		
		return "redirect:"+referer;
	}

}
