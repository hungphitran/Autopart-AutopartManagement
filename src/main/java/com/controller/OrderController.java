package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	public String showOrder(HttpServletRequest req,RedirectAttributes redirectAttributes) {
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
				}
				
			}			
			
			Double totalCost= Double.valueOf(req.getParameter("totalCost"));
			String code= req.getParameter("code");
			String shipAddress= req.getParameter("shipAddress");
			
			//create new order then add it to database
			Order newOrder = new Order(orderDao.generateNextOrderId(),code,acc.getEmail() , shipAddress,BigDecimal.valueOf(totalCost), Date.valueOf( LocalDate.now()), null, "Pending", null, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()), false);
			
			//update cart
			for(Product p : products.keySet()) {
				OrderDetail newOrderDetail = new OrderDetail(newOrder.getOrderId(),p.getProductId(),p.getProductName(),products.get(p),BigDecimal.valueOf( p.getSalePrice()));
				orderDetailDao.add(newOrderDetail);
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
			
			return "success";

		}
		else {
			return "redirect:/login.htm";
		}

		
	}
	
	@RequestMapping("/order")
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
	
	@RequestMapping(value="/order",method=RequestMethod.POST)// redirect to payment page
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
		
		if(products.size() == 0) {
			redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng trống");
			return "redirect:/index.htm";
		}
		req.setAttribute("products", products);
		
		List<Discount> discounts = discountDao.getAll();
		req.setAttribute("discounts", discounts);

		return "order";
	}

}
