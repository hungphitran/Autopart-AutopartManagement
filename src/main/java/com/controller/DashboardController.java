package com.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dao.Account_DAO;
import com.dao.BlogGroup_DAO;
import com.dao.Brand_DAO;
import com.dao.Cart_DAO;
import com.dao.Customer_DAO;
import com.dao.GeneralSettings_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.entity.Account;
import com.entity.BlogGroup;
import com.entity.Cart;
import com.entity.Customer;
import com.entity.GeneralSettings;
import com.entity.Product;
import com.entity.ProductGroup;

@Controller
public class DashboardController {
	
	@Autowired
	Product_DAO productDao;
	@Autowired
	Customer_DAO customerDao;
	@Autowired
	Account_DAO accountDao;
	@Autowired 
	Cart_DAO cartDao;
	
	@Autowired
	Brand_DAO brandDao;
	
	@Autowired
	ProductGroup_DAO pgDao;
	
	@Autowired 
	BlogGroup_DAO blogGroupDao;
	
	@Autowired
	GeneralSettings_DAO generalDao;
	
	@RequestMapping("/index")
	public String showDashboard(HttpServletRequest req) {
		// Fetch top 12 products sorted by stock, including product group name
		List<Product> proLstFav = productDao.getTopByStock(12);
		for (Product product : proLstFav) {
			String img = product.getImageUrls();
			product.setImageUrls(img != null ? img.split(",")[0] : "");
		}
		req.setAttribute("products", proLstFav);

		// Fetch top 4 most-ordered products
		List<Product> proMostOrder = productDao.getTopProductsByOrders(4);
		for (Product product : proMostOrder) {
			String img = product.getImageUrls();
			product.setImageUrls(img != null ? img.split(",")[0] : "");
		}
		req.setAttribute("productOrderMost", proMostOrder);

		// Fetch top 6 categories with most active products
		List<ProductGroup> categoriesHaveMostPro = pgDao.getTopByProductCount(6);
//		req.setAttribute("categories", categoriesHaveMostPro);
		
		// Fetch all active product groups for categories section
//		List<ProductGroup> groups = pgDao.getAll();
		req.setAttribute("groups", categoriesHaveMostPro);
		
		//check user in session
		HttpSession session = req.getSession();
		Account acc =(Account) session.getAttribute("user");
		
		//separate parentgroup and childgroup
//		List<ProductGroup> pgLst = pgDao.getAll();
//		List<ProductGroup> parentGroups = new ArrayList<ProductGroup>();
//		for(ProductGroup pg: pgLst) {
//			if(pg.getParentGroupId()==null) {
//				parentGroups.add(pg);
//			}
//		}
//	
//		Map<String, List<String>> groups = new HashMap<String, List<String>>();
//		for(ProductGroup pg: parentGroups) {
//			List<String> childGroups= new ArrayList<String>();
//			for(ProductGroup pgr: pgLst) {
//				if(pg.getProductGroupId().equals(pgr.getProductGroupId())) {
//					continue;
//				}
//				else if(pgr.getParentGroupId()!=null &&  pgr.getParentGroupId().equals(pg.getProductGroupId())) {
//					childGroups.add(pgr.getGroupName());
//				}
//			}
//			groups.put(pg.getGroupName(), childGroups);
//		}
		
//		pgLst.clear();
//		parentGroups.clear();

		
		
		List<BlogGroup> blogGroups = blogGroupDao.getAll();
		session.setAttribute("blogGroups", blogGroups);
		
		
		if(acc != null ) {//get cart if user logged in
			Customer cus = customerDao.getByEmail(acc.getEmail());
			Cart cart =cartDao.getById(cus.getCartId());
			req.setAttribute("cart", cart);
			Map<String,Integer> productsInCart =cart.getProducts();

			Map<Product,Integer> products= new HashMap<Product, Integer>();
			for(String key : productsInCart.keySet()) {
				products.put(productDao.getById(key),productsInCart.get(key));
			}
			req.setAttribute("productInCart", products);
		}
		
		GeneralSettings g=  generalDao.get();
		session.setAttribute("general", g);
//		session.setAttribute("groups", pgLst);
		session.setAttribute("brands", brandDao.getAll());

		return "dashboard";
	}
}
