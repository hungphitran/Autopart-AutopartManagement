package com.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
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
import com.entity.ImportDetailId;
import com.entity.Product;
import com.entity.ProductGroup;
import com.utils.ValidationUtils;

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
		try
		{
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
		catch (Exception e)
		{
			System.out.println("Test1");
	        req.setAttribute("errorMessage", "Có lỗi khi tải danh sách sản phẩm!"); 
			e.printStackTrace();
			System.out.println("Test2");
		    return "adminview/product/index";
			
		}
	    
	}
	
	@RequestMapping(value = "/product/add", method= RequestMethod.GET)
	public String addProduct(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			model.addAttribute("product", new Product());
			model.addAttribute("nextProductId", productDao.generateNextProductId());
			
			List<Brand> brandList = brandDao.getAll();
			List<ProductGroup> productGroupList = productGroupDao.getAll();
			
			req.setAttribute("brandList", brandList);
			req.setAttribute("productGroupList", productGroupList);
			
			return "adminview/product/add";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải trang thêm sản phẩm!"); 
			e.printStackTrace();
			return "redirect:/admin/product.htm";
			
		}

			
	}
	
	@RequestMapping(value = "/product/add", method= RequestMethod.POST)
	public String addProductPost(@ModelAttribute("product") Product product, @RequestParam("imageFiles") MultipartFile[] imageFiles, HttpServletRequest req, RedirectAttributes redirectAttributes) throws IOException {
		  // Validate stock (non-negative)
	    if (product.getWeight() <= 0) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Khối lượn không đucợ bằng 0 hay nhỏ hơn 0!");
	        return "redirect:/admin/product/add.htm";
	    }
		
		// Validate image files (at least one valid image)
	    if (imageFiles == null || imageFiles.length == 0 || 
	        Arrays.stream(imageFiles).noneMatch(ValidationUtils::isValidImageFile)) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng tải lên ít nhất một file ảnh định dạng JPG hoặc PNG!");
	        return "redirect:/admin/product/add.htm";
	    }
		
		try
		{
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
	        redirectAttributes.addFlashAttribute("successMessage", "Thêm sản phẩm thành công!"); 

			return "redirect:/admin/product.htm";
		}
		catch (Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);

	        redirectAttributes.addFlashAttribute("errorMessage", "Thêm sản phẩm thất bại!"); 
			e.printStackTrace();
			return "redirect:" + referer;
			
		}
	}

	@RequestMapping(value = "/product/edit", method= RequestMethod.GET)
	public String editProduct(@RequestParam("productId") String productId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			Product product = productDao.getById(productId);
			List<Brand> brandList = brandDao.getAll();
			List<ProductGroup> productGroupList = productGroupDao.getAll();
			req.setAttribute("product", product);
			req.setAttribute("brandList", brandList);
			req.setAttribute("productGroupList", productGroupList);
			return "adminview/product/edit";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tải sản phẩm!"); 
			e.printStackTrace();
			return "redirect:/admin/product.htm";
			
		}
	
	}
	
	@RequestMapping(value = "/product/edit", method= RequestMethod.POST)
	public String editProductPatch(@ModelAttribute("product") Product product,
            @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles,
            @RequestParam(value = "confirmDeleteImg", required = false) String confirmDeleteImg,
            HttpServletRequest req, RedirectAttributes redirectAttributes) {
		
		// Validate sale price (positive)
	    if ( product.getSalePrice() <= 0) {
	    	String referer = req.getHeader("Referer");
			System.out.println(referer);
	        redirectAttributes.addFlashAttribute("errorMessage", "Giá bán phải lớn hơn 0!");
			return "redirect:" + referer;
	    }
	    
	    // Validate stock (non-negative)
	    if (product.getWeight() <= 0) {
	    	String referer = req.getHeader("Referer");
			System.out.println(referer);
	        redirectAttributes.addFlashAttribute("errorMessage", "Khối lượn không đucợ bằng 0 hay nhỏ hơn 0!");
			return "redirect:" + referer;
	    }
		
		// Validate image files (at least one valid image)
	    if (imageFiles == null || imageFiles.length == 0 || 
	        Arrays.stream(imageFiles).noneMatch(ValidationUtils::isValidImageFile)) {
	    	String referer = req.getHeader("Referer");
			System.out.println(referer);
	        redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng tải lên ít nhất một file ảnh định dạng JPG hoặc PNG!");
			return "redirect:" + referer;
	    }
		
		try
		{
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
	        redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa sản phẩm thành công!"); 

			return "redirect:/admin/product.htm";
		}
		catch (Exception e)
		{
			String referer = req.getHeader("Referer");
			System.out.println(referer);
	        redirectAttributes.addFlashAttribute("errorMessage", "Chỉnh sửa sản phẩm thất bại!"); 
			e.printStackTrace();
            return "redirect:" + referer;
			
		}
		
	}  
	
	@RequestMapping("/product/delete")
	public String deleteProduct(@RequestParam("productId") String productId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			productDao.delete(productId);
	        redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phẩm thành công!"); 
			return "redirect:/admin/product.htm";
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Xóa sản phẩm thất bại!"); 
			e.printStackTrace();
			return "redirect:/admin/product.htm";
			
		}
		
	}
	
	@RequestMapping(value = "/product/detail", method= RequestMethod.GET)
	public String detailProduct(@RequestParam("productId") String productId, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
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
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải sản phẩm!"); 
			e.printStackTrace();
			return "redirect:/admin/product.htm";
			
		}
	
	}
	
	@RequestMapping(value = "/product/changeStatus", method= RequestMethod.POST)
	public String changeStatusProduct(@RequestParam("productId") String productId, RedirectAttributes redirectAttributes) {
		try
		{
			productDao.changeStatus(productId);
			return "adminview/product/index";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi thay đổi trạng thái sản phẩm!"); 
			e.printStackTrace();
			return "redirect:/admin/product.htm";
			
		}

			
	}
	
	@RequestMapping(value = "/product/import", method= RequestMethod.GET)
	public String importProduct(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			List<Import> imports = importDao.getAll();
			req.setAttribute("imports", imports);
			return "adminview/product/import/index";
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải danh sách phiếu nhập!"); 
			e.printStackTrace();
			return "redirect:/admin/product/import.htm";
			
		}

	}
	
	@RequestMapping(value = "/product/import/add", method = RequestMethod.GET)
    public String addImportProduct(Model model, HttpServletRequest req, HttpSession session, RedirectAttributes redirectAttributes) {
		try
		{
			Import importEntity = new Import();
	        importEntity.setImportId(importDao.generateNextImportId());
	        importEntity.setImportDate(new java.sql.Date(System.currentTimeMillis())); // Ngày hiện tại
	        model.addAttribute("importForm", importEntity);

	        List<Product> productList = productDao.getAll();

	        model.addAttribute("productList", productList);
	        model.addAttribute("empName", session.getAttribute("name"));
	        model.addAttribute("empEmail", session.getAttribute("email"));
	        
	        return "adminview/product/import/add";

		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải giao diện tạo phiếu nhập!"); 
			e.printStackTrace();
			return "redirect:/admin/product/import.htm";
			
		}
   
	}
	
	@RequestMapping(value = "/product/import/add", method = RequestMethod.POST)
	public String addImportProductPost(@ModelAttribute("importForm") Import importForm, 
	                                   BindingResult result, 
	                                   Model model, RedirectAttributes redirectAttributes, HttpSession session) {
		try
		{
			 if (result.hasErrors()) {
				 	model.addAttribute("empName", session.getAttribute("name"));
			        model.addAttribute("productList", productDao.getAll());
			        model.addAttribute("error", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại.");
			        return "adminview/product/import/add";
			    }
			    System.out.println(importForm);
			    if (importForm.getImportDetails() != null) {
			        importForm.getImportDetails().forEach(detail -> {
			        	// Gán importId cho các ImportDetail
			            ImportDetailId id = new ImportDetailId();
			            id.setImportId(importForm.getImportId());
			            id.setProductId(detail.getId().getProductId());
			            detail.setId(id);
			            detail.setImportEntity(importForm);
			            
			            // Cập nhật số lượng sản phẩm vào kho
			            String productId = detail.getId().getProductId();
			            Product product = productDao.getById(productId);
			            
			            product.setCostPrice((product.getCostPrice() * product.getStock() + detail.getAmount() * detail.getPrice()) / (product.getStock() + detail.getAmount()));
			            product.setStock(product.getStock() + detail.getAmount());
			            
			            
			            productDao.update(product);
			        });
			    }
			    
			    importForm.setImportDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
			    importDao.add(importForm);
		        redirectAttributes.addFlashAttribute("successMessage", "Thêm phiếu nhập thành công!"); 

			    return "redirect:/admin/product/import.htm";	
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Thêm phiếu nhập thất bại!"); 
			e.printStackTrace();
            return "redirect:/admin/product/import/add.htm";
			
		}
	   
	}
	
	@RequestMapping(value = "/product/import/detail", method = RequestMethod.GET)
    public String detailImportProduct(@RequestParam("importId") String importId, Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {
		try
		{
			// Lấy thông tin phiếu nhập từ database
	        Import importEntity = importDao.getById(importId); // Giả định Import_DAO có phương thức getById
	        if (importEntity == null) {
	            model.addAttribute("error", "Phiếu nhập không tồn tại!");
	            return "adminview/product/import/detail"; // Trả về trang với thông báo lỗi
	        }

	        // Lấy thông tin nhân viên để hiển thị tên đầy đủ
	        String employeeFullName = employeeDao.getByEmail(importEntity.getEmployeeEmail()) != null
	                ? employeeDao.getByEmail(importEntity.getEmployeeEmail()).getFullName()
	                : "Không xác định";

	        // Lấy danh sách sản phẩm để hiển thị tên sản phẩm
	        List<Product> productList = productDao.getAll();
	        model.addAttribute("importEntity", importEntity);
	        model.addAttribute("employeeFullName", employeeFullName);
	        model.addAttribute("productList", productList);

	        return "adminview/product/import/detail";
		}
		catch (Exception e)
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi khi tải phiếu nhập!"); 
			e.printStackTrace();
			return "redirect:/admin/product/import.htm";
			
		}

    }
	// -- End product -

}
