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
	
	@RequestMapping(value="/order/create", method = RequestMethod.POST)
	public String showOrder(HttpServletRequest req,RedirectAttributes redirectAttributes) throws MalformedURLException {
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc != null ) {//get cart if user logged in
			Customer cus = customerDao.getByEmail(acc.getEmail());
			Cart cart =cartDao.getById(cus.getCartId());
			
			//get all products that user choosed
			Map<String,Integer> productsInCart =cart.getProducts();
			Map<Product,Integer> products= new HashMap<Product, Integer>();
			
	        
			for(String key : productsInCart.keySet()) {
				//get id and quantity from req
				String quantity = req.getParameter(key);
				if(quantity != null) {
					products.put(productDao.getById(key),productsInCart.get(key));
					System.out.println(key +" " + productsInCart.get(key));
				}
				
			}		
			
			


	        

			
			Double totalCost= Double.valueOf(req.getParameter("totalCost"));
			String shipAddress= req.getParameter("shipAddress");
			String shippingType = req.getParameter("shippingType");
			String discountId = req.getParameter("discountId");
			System.out.println(discountId);
			Discount discount = discountDao.getById(discountId);
			
			System.out.println("Shipping Type: "+shippingType);
	        String referer = req.getHeader("Referer");
	        String path = referer.replaceFirst("http://localhost:8080/autopart/order.htm", "");
      	     // Get query parameters from the original URL
      	     String queryString = req.getQueryString();
      	     
      	     if (queryString != null && !queryString.isEmpty()) {
      	         // Parse the query string and add each parameter individually using addAttribute
      	         String[] params = queryString.split("&");
      	         for (String param : params) {
      	             String[] keyValue = param.split("=");
      	             if (keyValue.length == 2) {
      	                 redirectAttributes.addAttribute(keyValue[0], keyValue[1]);
      	             }
      	         }
      	     }
       	// In OrderController.java - modify the redirect code in /order/create
       	 if(shippingType.isEmpty()) {
       	     // Add flash attribute for message
       	     redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn loại vận chuyển");
       	     

       	     
             return queryString != null && !queryString.isEmpty() 
                     ? "redirect:/order.htm?" + queryString 
                     : "redirect:/order.htm";
       	 }


			
			if(discount!=null && discount.getUsageLimit()==0) 
			{
		        redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng chọn mã");  
		        return queryString != null && !queryString.isEmpty() 
		                ? "redirect:/order.htm?" + queryString 
		                : "redirect:/order.htm";

		    }
			
			
		
			//create new order then add it to database
//			Order newOrder = new Order(orderDao.generateNextOrderId(),discountId,acc.getEmail() , shipAddress,BigDecimal.valueOf(totalCost), Date.valueOf( LocalDate.now()), null, "Pending", null, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
//			discountDao.discountUsed(acc.getEmail(), discountId);
//			//update cart
//			for(Product p : products.keySet()) {
//				OrderDetail newOrderDetail = new OrderDetail(newOrder.getOrderId(),p.getProductId(),p.getProductName(),products.get(p),BigDecimal.valueOf( p.getSalePrice()));
//				orderDetailDao.add(newOrderDetail);
//			}
//			for(Product p: products.keySet()) {
//				productsInCart.remove(p.getProductId());
//			}
//			cart.setProducts(productsInCart);
//			cartDao.update(cart);
//			Map<Product,Integer> p= new HashMap<Product, Integer>();
//			for(String key : productsInCart.keySet()) {
//				p.put(productDao.getById(key),productsInCart.get(key));
//			}
//			session.setAttribute("productInCart",p);
			
			
			
			return "success";

		}
		else {
			return "redirect:/login.htm";
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
		
		req.setAttribute("products", products);
		
		List<Discount> discounts = discountDao.getByCustomer(acc.getEmail());
		req.setAttribute("discounts", discounts);
		

		return "order";
	}

}
