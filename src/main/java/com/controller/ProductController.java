package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping("/detailproduct")
	public String showProduct(@RequestParam("productId") String id,HttpServletRequest req) {
		Product p = productDao.getById(id);
		String[] imgUrls = p.getImageUrls().split(",");
		Brand brand= brandDao.getByBrandId(p.getBrandId());
		ProductGroup group = pgDao.getByProductGroupId(p.getProductGroupId());
		req.setAttribute("product", p);
		req.setAttribute("imgUrls", imgUrls);
		req.setAttribute("brand", brand);
		req.setAttribute("group", group);
		
		return "detailproduct";
	}
	@RequestMapping("/addproduct") // quantity && id
	public String addProduct(HttpServletRequest req) {
		String productId=  req.getParameter("productId");
		String quantity = req.getParameter("quantity");
		
		//check if user logged in
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc == null ) {//user need to log in
			return "redirect:/login.htm";
		}
		Customer cus = customerDao.getByPhone(acc.getPhone());
		Cart cart =cartDao.getById(cus.getCartId());
		productDao.getById(productId);
		Map<String,Integer> products =cart.getProducts();
		if(products.containsKey(productId)) {//this product was in cart before
			products.put(productId,Integer.parseInt(quantity)+products.get(productId));
		}
		else {//product is not in cart
			products.put(productId, Integer.parseInt(quantity));
		}
		cart.setProducts(products);//update products in cart
		if(cartDao.update(cart)) {//update cart's infor in database
			Product p = productDao.getById(productId);
		}
		else {

		}
		return "redirect:/product/detailproduct.htm?productId="+productId;
	}
	
	@RequestMapping("/deleteProduct")
	@ResponseBody
	public String delete(HttpServletRequest req) {
		return "chưa làm!!!";
	}
	
	
	
	@RequestMapping("/search")
	public String showFilter(HttpServletRequest req) {
		String key = req.getParameter("keyword").toLowerCase().strip();
		String brand=req.getParameter("brand");
		String category = req.getParameter("category");
		req.setAttribute("keyword", key);
		List<Product> filteredLst = new ArrayList<Product>();		
		List<Brand> brands = brandDao.getAll();
		List<ProductGroup> categories = pgDao.getAll();
		
		//find brand id and group id
		String brandId="";
		String groupId="";
		
		if(brand.equals("All")) {
			brandId="";
		}
		else {
			for(int i=0;i<brands.size();i++) {
				if(brands.get(i).getBrandName().equals(brand)) {
					brandId=brands.get(i).getBrandId();
					break;
				}
			}
		}
		if(category.equals("All")) {
			groupId="";
		}
		else {
			for(int i=0;i<categories.size();i++) {
				if(categories.get(i).getGroupName().equals(category)) {
					groupId=categories.get(i).getProductGroupId();
					break;
				}
			}
		}
		
		List<Product> pLst = productDao.getAll();
		for(int i=0;i<pLst.size();i++) {
			Product p=pLst.get(i);
			String img= p.getImageUrls();
			pLst.get(i).setImageUrls(img.split(",", i)[0]);
			//find all product that contain keyword, brand and group)
			if((p.getProductId().toLowerCase().contains(key) || p.getProductName().toLowerCase().contains(key)|| p.getDescription().toLowerCase().contains(key))&&(p.getBrandId().contains(brandId)&& p.getProductGroupId().contains(groupId))) {
				filteredLst.add(p);
			}
			
		}
		pLst.clear();

		
		req.setAttribute("brands",brands);
		req.setAttribute("categories", categories);
		req.setAttribute("products", filteredLst);
		
		req.setAttribute("brand", brand);
		req.setAttribute("category",category);
		
		return "filterproduct";
	}
	
	
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest req) {
		
		//check if user logged in
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		if(acc == null ) {
			return "redirect:/login.htm";
		}
		Customer cus = customerDao.getByPhone(acc.getPhone());
		Cart cart= cartDao.getById(cus.getCartId());
		
		Map<String,Integer> productsInCart =cart.getProducts();
		
		req.setAttribute("products", productsInCart);

		return "cart";
	}
}
