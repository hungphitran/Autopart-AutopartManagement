package com.controller;

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

import com.dao.Brand_DAO;
import com.dao.Employee_DAO;
import com.dao.Import_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.entity.Brand;
import com.entity.Employee;
import com.entity.Import;
import com.entity.ImportDetail;
import com.entity.Product;
import com.entity.ProductGroup;

@Controller
@RequestMapping("/admin")
public class AdminProductController {
	
	@Autowired
	Product_DAO productDao;
	
	@Autowired
	Brand_DAO brandDao;
	
	@Autowired
	ProductGroup_DAO productGroupDao;
	
	@Autowired
	Import_DAO importDao;
	
	@Autowired
	Employee_DAO employeeDao;
	
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
		model.addAttribute("nextProductId", productDao.generateNextProductId());
		
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
		
		String brandName=brandDao.getById(product.getBrandId()).getBrandName();
		String groupName = productGroupDao.getById(product.getProductGroupId()).getGroupName();
		
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
	
	@RequestMapping(value = "/product/import", method = RequestMethod.GET)
	    public String importProduct(Model model, HttpServletRequest req) {
	        Import importEntity = new Import();
	        importEntity.setImportId(importDao.generateNextImportId());
	        importEntity.setImportDate(new java.sql.Date(System.currentTimeMillis())); // Ngày hiện tại
	        model.addAttribute("importForm", importEntity);

	        List<Employee> employeeList = employeeDao.getAll();
	        List<Product> productList = productDao.getAll();

	        model.addAttribute("employeeList", employeeList);
	        model.addAttribute("productList", productList);

	        return "adminview/product/import";
	    }

	    @RequestMapping(value = "/product/import", method = RequestMethod.POST)
	    public String importProductPost(
	            @ModelAttribute("importForm") Import importEntity,
	            @RequestParam(value = "importDetails", required = false) List<ImportDetail> importDetails,
	            RedirectAttributes redirectAttributes,
	            HttpServletRequest req) {

	        // Kiểm tra dữ liệu
	        if (importDetails == null || importDetails.isEmpty()) {
	            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn ít nhất một sản phẩm để nhập hàng!");
	            return "redirect:/admin/product/import.htm";
	        }

	        // Gán importId cho các chi tiết phiếu nhập
	        for (ImportDetail detail : importDetails) {
	            detail.setImportId(importEntity.getImportId());
	        }

	        // Lưu phiếu nhập
	        importEntity.setImportDetails(importDetails);
	        boolean importSuccess = importDao.add(importEntity);
	        if (!importSuccess) {
	            redirectAttributes.addFlashAttribute("error", "Lưu phiếu nhập thất bại!");
	            return "redirect:/admin/product/import.htm";
	        }

	        // Cập nhật số lượng sản phẩm trong bảng Product
	        for (ImportDetail detail : importDetails) {
	            Product product = productDao.getById(detail.getProductId());
	            if (product != null) {
	                int newStock = product.getStock() + detail.getAmount();
	                product.setStock(newStock);
	                productDao.update(product);
	            }
	        }

	        redirectAttributes.addFlashAttribute("message", "Nhập hàng thành công!");
	        return "redirect:/admin/product.htm";
	    }

}
