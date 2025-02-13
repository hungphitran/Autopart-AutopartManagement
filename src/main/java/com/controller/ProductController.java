package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.Brand_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.entity.Brand;
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
	
	@RequestMapping("/detailproduct")
	public String showProduct(@RequestParam("productId") String id,HttpServletRequest req) {
		Product p = productDao.getById(id);
		String[] imgUrls = p.getImageUrls().split(",");
		req.setAttribute("product", p);
		req.setAttribute("imgUrls", imgUrls);
		return "detailproduct";
	}
	@RequestMapping("/addproduct") // quantity && id
	@ResponseBody
	public String addProduct(HttpServletRequest req) {
		return "Quantity"+ req.getParameter("quantity");
	}
	
	@RequestMapping("/search")
	public String showFilter(HttpServletRequest req) {
		String key = req.getParameter("keyword");
		req.setAttribute("keyword", key);
		
		List<Product> pLst = productDao.getAll();
		for(int i=0;i<pLst.size();i++) {
			String img= pLst.get(i).getImageUrls();
			pLst.get(i).setImageUrls(img.split(",", i)[0]);
		}
		
		
		List<Brand> brands = brandDao.getAll();
		List<ProductGroup> categories = pgDao.getAll();
		
		req.setAttribute("brands",brands);
		req.setAttribute("categories", categories);
		req.setAttribute("products", pLst);
		
		req.setAttribute("brand", req.getParameter("brand"));
		req.setAttribute("category",req.getParameter("category"));
		
		return "filterproduct";
	}
	
	
	@RequestMapping("/cart")
	public String showCart() {
		return "cart";
	}
}
