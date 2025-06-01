package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dao.Account_DAO;
import com.dao.Brand_DAO;
import com.dao.Cart_DAO;
import com.dao.Customer_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.entity.Account;
import com.entity.Brand;
import com.entity.Cart;
import com.entity.Customer;
import com.entity.Product;
import com.entity.ProductGroup;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	Product_DAO productDao;
	
	@Autowired
	Brand_DAO brandDao;
	
	@Autowired
	ProductGroup_DAO pgDao;
	
	@Autowired
	Cart_DAO cartDao;
	
	@Autowired
	Customer_DAO customerDao;
	
	@Autowired
	Account_DAO accountDao;
	
	@RequestMapping("/detailproduct")//show detail of a product
	public String showProduct(@RequestParam("productId") String id,HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Product p = productDao.getById(id);
			String[] imgUrls = p.getImageUrls().split(",");
			String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/resources/img/";
			
			for (int i = 0; i < imgUrls.length; i++) {
			    if (!imgUrls[i].startsWith("https")) {
			        imgUrls[i] = baseUrl + imgUrls[i];  
			    }
			}
			
			Brand brand= brandDao.getById(p.getBrandId());
			ProductGroup group = pgDao.getById(p.getProductGroupId());
			req.setAttribute("product", p);
			req.setAttribute("imgUrls", imgUrls);
			req.setAttribute("brand", brand);
			req.setAttribute("group", group);
			
			return "detailproduct";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải sản phẩm!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/index.htm";
			
		}

			
	}
	@RequestMapping("/addproduct") // quantity && id
	public String addProduct(HttpServletRequest req,RedirectAttributes redirectAttributes) {
		String productId=  req.getParameter("productId");
		String quantity = req.getParameter("quantity");
		
		try
		{
			//check if user logged in
			HttpSession session = req.getSession();
			Account acc =(Account) session.getAttribute("user");
			if(acc == null ) {//user need to log in
				return "redirect:/login.htm";
			}
			Customer cus = customerDao.getByEmail(acc.getEmail());
			Cart cart =cartDao.getById(cus.getCartId());
			Product pro=productDao.getById(productId);
			Map<String,Integer> productInCart =cart.getProducts();
			if(productInCart.containsKey(productId)) {//this product was in cart before
				if(productInCart.get(productId) + Integer.parseInt(quantity) > pro.getStock()) {//check if quantity is enough
					redirectAttributes.addFlashAttribute("errorMessage", "Số lượng sản phẩm không đủ");
					return "redirect:/product/detailproduct.htm?productId="+productId;
				}
				productInCart.put(productId,Integer.parseInt(quantity)+productInCart.get(productId));
			}
			else {//product is not in cart
				productInCart.put(productId, Integer.parseInt(quantity));
			}
			cart.setProducts(productInCart);//update products in cart
			if(cartDao.update(cart)) {//update cart's infor in database
				Product p = productDao.getById(productId);
				Map<Product,Integer> products= new HashMap<Product, Integer>();
				for(String key : productInCart.keySet()) {
					products.put(productDao.getById(key),productInCart.get(key));
				}
				req.getSession().setAttribute("productInCart", products);
		        // Add flash attribute for success message
		        redirectAttributes.addFlashAttribute("successMessage", "Thêm vào giỏ hàng thành công");
		    } else {
		        // Add flash attribute for error message
		        redirectAttributes.addFlashAttribute("errorMessage", "Thêm vào giỏ hàng thất bại");
		    }
			return "redirect:/product/detailproduct.htm?productId="+productId;
		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi thêm sản phẩm!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/product/detailproduct.htm?productId="+productId;
			
		}

		
		
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest req,RedirectAttributes redirectAttributes) {
		try
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);
			String productId=  req.getParameter("productId");
			
			HttpSession session = req.getSession();
			Account acc = (Account) session.getAttribute("user");
			Customer cus = customerDao.getByEmail(acc.getEmail());
			Cart cart =cartDao.getById(cus.getCartId());
			Map<String,Integer> productsInCart =cart.getProducts();
			productsInCart.remove(productId);
			cartDao.update(cart);
			Map<Product,Integer> products= new HashMap<Product, Integer>();
			for(String key : productsInCart.keySet()) {
				products.put(productDao.getById(key),productsInCart.get(key));
			}
			session.setAttribute("productInCart", products);
			// Add flash attribute for success message
			redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công");
			// Redirect to the referer URL
			return "redirect:"+referer;

		}
		catch (Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);

			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi xóa sản phẩm!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:"+referer;
			
		}
	
	}
	
	@RequestMapping("/search")
	public String showFilter(HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			String key = req.getParameter("keyword");
			if(key == null) {
				key="";
			}
			else {
				 key = key.toLowerCase().trim();
			}
			String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/resources/img/";
			String brand = req.getParameter("brandName");
			String group = req.getParameter("groupName");
			req.setAttribute("keyword", key);
			List<Product> filteredLst = new ArrayList<Product>();		
			List<Brand> brands = brandDao.getAll();
			List<ProductGroup> categories = pgDao.getAll();
			
			List<Product> pLst = productDao.getAll();
			for(int i=0;i<pLst.size();i++) {
				String img= pLst.get(i).getImageUrls();
				pLst.get(i).setImageUrls(img.split(",", i)[0]);
			}
			for(int i=0;i<pLst.size();i++) {
				Product p=pLst.get(i);
				String img= p.getImageUrls();
				if (img.split(",", i)[0].startsWith("https")) {
					pLst.get(i).setImageUrls(img.split(",", i)[0]);
	        	}
	        	else {
	        		pLst.get(i).setImageUrls(baseUrl + img.split(",", i)[0]); 	        	
	        	}	   
				//find all product that contain keyword, brand and group)
				if(p.getProductId().toLowerCase().contains(key) || p.getProductName().toLowerCase().contains(key) || p.getDescription().toLowerCase().contains("key")) {
					filteredLst.add(p);
				}
				
			}
			pLst.clear();

			
			req.setAttribute("brands",brands);
			req.setAttribute("categories", categories);
			req.setAttribute("products", filteredLst);
			req.setAttribute("brand", brand);
			req.setAttribute("group", group);
			

			
			return "filterproduct";

		}
		catch (Exception e)
		{
			System.out.println("Test1");
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải danh sách sản phẩm!"); 
			e.printStackTrace();
			System.out.println("Test2");
			return "redirect:/index";
			
		}
	
	}
	
}
