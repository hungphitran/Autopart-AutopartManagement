package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.Account_DAO;
import com.dao.Blog_DAO;
import com.dao.Brand_DAO;
import com.dao.Employee_DAO;
import com.dao.GeneralSettings_DAO;
import com.dao.Order_DAO;
//import com.dao.Permission_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.dao.RoleGroup_DAO;
import com.entity.Account;
import com.entity.Brand;
import com.entity.Product;
import com.entity.ProductGroup;

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
	Blog_DAO blogDao;

	@Autowired
	ProductGroup_DAO productGroupDao;

	@Autowired
	Brand_DAO brandDao;

	@Autowired
	Employee_DAO employeeDao;

	@Autowired
	Order_DAO orderDao;

	@Autowired
	Product_DAO productDao;
	
	@Autowired
	GeneralSettings_DAO gsdao;
	@RequestMapping(value ="/login",method=RequestMethod.GET )
	public String showLogin(HttpServletRequest req) {
		return "adminview/login";
	}
	
	@RequestMapping(value = "/login", method= RequestMethod.POST)
	public String login(HttpServletRequest req) {
		req.setAttribute("title","Tài khoản");
		return "redirect:/admin/account.htm";
	}
	
	@RequestMapping("/statistic")
	public String showStatistic(HttpServletRequest req) {
		req.setAttribute("title","Thống kê");
		return "adminview/statistic";
	}
	
	@RequestMapping("/account")
	public String showInfo(HttpServletRequest req) {
		//HttpSession session = req.getSession();
		//Account account= (Account) session.getAttribute("user");
		req.setAttribute("title","Tài khoản");
		return "adminview/profile";
	}
	
	@RequestMapping("/blog")
	public String showBlog(HttpServletRequest req) {
		return "adminview/blog";
	}
	
	// -- Product --
	@RequestMapping("/product")
	public String showProducts(HttpServletRequest req) {
		List<Product> products = productDao.getAll();
		for(int i=0;i<products.size();i++) {
			String img= products.get(i).getImageUrls();
			products.get(i).setImageUrls(img.split(",", i)[0]);
		}
		req.setAttribute("products", products);
		req.setAttribute("title","Sản phẩm");
		return "adminview/product/index";
	}
	
	@RequestMapping(value = "/product/add", method= RequestMethod.GET)
	public String addProduct(Model model, HttpServletRequest req) {
		model.addAttribute("product", new Product());
		
		List<Brand> brandList = brandDao.getAll();
		List<ProductGroup> productGroupList = productGroupDao.getAll();
		req.setAttribute("brandList", brandList);
		req.setAttribute("productGroupList", productGroupList);
		
		return "adminview/product/add";
	}
	
	@RequestMapping(value = "/product/add", method= RequestMethod.POST)
	public String addProductPost(@ModelAttribute("product") Product product, HttpServletRequest req) {
		if (product.getStatus() == null) {
			product.setStatus("Inactive");
	    }
		
		productDao.add(product);
		
		return "redirect:/admin/product.htm";
	}

	@RequestMapping(value = "/product/edit", method= RequestMethod.GET)
	public String editProduct(@RequestParam("productId") String productId, HttpServletRequest req) {
		Product product = productDao.getById(productId);
		List<Brand> brandList = brandDao.getAll();
		List<ProductGroup> productGroupList = productGroupDao.getAll();
		req.setAttribute("product", product);
		req.setAttribute("brandList", brandList);
		req.setAttribute("productGroupList", productGroupList);
		return "adminview/product/edit";
	}
	
	@RequestMapping(value = "/product/edit", method= RequestMethod.POST)
	public String editProductPatch(@ModelAttribute("product") Product product) {
		if (product.getStatus() == null) {
			product.setStatus("Inactive");
	    }
		productDao.update(product);
		
		return "redirect:/admin/product.htm";
	}  
	
	@RequestMapping("/product/delete")
	public String deleteProduct(@RequestParam("productId") String productId, HttpServletRequest req) {
		productDao.delete(productId);
		return "redirect:/admin/product.htm";
	}
	
	@RequestMapping(value = "/product/detail", method= RequestMethod.GET)
	public String detailProduct(@RequestParam("productId") String productId, HttpServletRequest req) {
		System.out.println(productId);
		Product product = productDao.getById(productId);
		String[] imgUrls = product.getImageUrls().split(",");
		
		String brandName=brandDao.getByBrandId(product.getBrandId()).getBrandName();
		String groupName = productGroupDao.getByProductGroupId(product.getProductGroupId()).getGroupName();
		
		req.setAttribute("imgUrls", imgUrls);
		req.setAttribute("product", product);
		req.setAttribute("brandName", brandName);
		req.setAttribute("groupName", groupName);
		return "adminview/product/detail";
	}
	
	@RequestMapping(value = "/product/changeStatus", method= RequestMethod.POST)
	public String changeStatusProduct(@RequestParam("productId") String productId) {
		productDao.changeStatus(productId);
		return "adminview/product/index";
	}
	// -- End product --
	
	// -- Brand --
		@RequestMapping("/brand")
		public String showBrands(HttpServletRequest req) {
			List<Brand> brands = brandDao.getAll();
			req.setAttribute("brands", brands);
			return "adminview/brand/index";
		}
		
		@RequestMapping(value = "/brand/add", method= RequestMethod.GET)
		public String addBrand(HttpServletRequest req) {
			return "adminview/brand/add";
		}
		
		@RequestMapping(value = "/brand/add", method= RequestMethod.POST)
		public String addBrandPost(@ModelAttribute("brand") Brand brand, HttpServletRequest req) {
			List<Brand> brands = brandDao.getAll();
			for(int i=0;i<brands.size();i++) {
				if(brands.get(i).getBrandName().toLowerCase().equals(brand.getBrandName().toLowerCase())){
					System.out.println("brand is existed");
					return "redirect:/admin/brand.htm";
				}
			}
			
			
			brand.setBrandId(brandDao.generateNextBrandId());
			Boolean success= brandDao.add(brand);
			System.out.println("add brand succeed:"+success);
			
			return "redirect:/admin/brand.htm";
		}
		
		@RequestMapping(value="/brand/delete",method=RequestMethod.GET)
		public String deleteBrand(@RequestParam("brandId") String brandId, HttpServletRequest req) {
			System.out.println(brandId);
			
			System.out.println("deleted: "+brandDao.delete(brandId));
			return "redirect:/admin/brand.htm";
		}
		
		@RequestMapping(value = "/brand/detail", method= RequestMethod.GET)
		public String detailBrand(@RequestParam("brandId") String brandId, HttpServletRequest req) {
			System.out.println("detail for brand : "+brandId);
			Brand brand = brandDao.getByBrandId(brandId);
			req.setAttribute("brand", brand);
			return "adminview/brand/detailModal";
		}
		
		@RequestMapping(value = "/brand/changeStatus", method= RequestMethod.POST)
		public String changeStatusBrand(@RequestParam("brandId") String brandId) {
			Brand brand=  brandDao.getByBrandId(brandId);
			brand.setStatus("Inactive");
			brandDao.update(brand);
			return "adminview/brand/index";
		}
		// -- End brand --
}
