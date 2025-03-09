package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dao.Brand_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.entity.Brand;
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
	
	@RequestMapping("/product")
	public String showProducts(HttpServletRequest req) {
	    List<Product> products = productDao.getAll();
	    String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/resources/img/";
	    
	    for (int i = 0; i < products.size(); i++) {
	        String img = products.get(i).getImageUrls();
	        if (img != null) {
	        	String[] imgArray = img.split(",");
	        	if (imgArray[0].startsWith("https")) {
	        		products.get(i).setImageUrls(imgArray[0]);
	        	}
	        	else {
	        		products.get(i).setImageUrls(baseUrl + imgArray[0]); 	        	
	        	}	        	
	        }
	    }
	    req.setAttribute("products", products);
	    req.setAttribute("title", "Sản phẩm");
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
	public String addProductPost(@ModelAttribute("product") Product product, @RequestParam("imageFiles") MultipartFile[] imageFiles, HttpServletRequest req) throws IOException {
		if (product.getStatus() == null) {
			product.setStatus("Inactive");
	    }
		
		// Đường dẫn thư mục lưu ảnh
	    String uploadDir = req.getServletContext().getRealPath("/resources/img/");
	    File dir = new File(uploadDir);
	    if (!dir.exists()) {
	        dir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
	    }

	    // Xử lý và lưu từng file ảnh
	    String imageUrls = "";
	    if (imageFiles != null && imageFiles.length > 0) {
	        imageUrls = Arrays.stream(imageFiles)
	                .filter(file -> !file.isEmpty())
	                .map(file -> {
	                    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // Thêm timestamp để tránh trùng tên
	                    try {
	                        File destination = new File(uploadDir + fileName);
	                        file.transferTo(destination); // Lưu file vào thư mục
	                        return fileName;
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                        return "";
	                    }
	                })
	                .filter(fileName -> !fileName.isEmpty())
	                .collect(Collectors.joining(","));
	    }

	    // Gán danh sách tên file vào product
	    product.setImageUrls(imageUrls);
	    
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
	public String editProductPatch(@ModelAttribute("product") Product product,
            @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles,
            @RequestParam(value = "confirmDeleteImg", required = false) String confirmDeleteImg,
            HttpServletRequest req) throws IOException {
		if (product.getStatus() == null) {
			product.setStatus("Inactive");
	    }
		
		String uploadDir = req.getServletContext().getRealPath("/resources/img/");
		File dir = new File(uploadDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		String newImageUrls = "";
		if (imageFiles != null && imageFiles.length > 0) {
			newImageUrls = Arrays.stream(imageFiles)
					.filter(file -> !file.isEmpty())
					.map(file -> {
						String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
						
						try {
							file.transferTo(new File(uploadDir + fileName));
							return fileName;
						} catch (IOException e) {
							e.printStackTrace();
							return "";
						}
					})
					.filter(fileName -> !fileName.isEmpty())
					.collect(Collectors.joining(","));
		}
		
		if ("confirm".equals(confirmDeleteImg)) {
			product.setImageUrls(newImageUrls.isEmpty() ? null : newImageUrls);
		}
		else {
			product.setImageUrls((product.getImageUrls() == null ? "" : product.getImageUrls() + ",") + (newImageUrls.isEmpty() ? "" : newImageUrls));
		}
		
		System.out.println(product);
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
	
	@RequestMapping(value = "/product/import", method= RequestMethod.GET)
	public String importProduct(Model model, HttpServletRequest req) {
		
		return "adminview/product/import";
	}
	// -- End product --

}
