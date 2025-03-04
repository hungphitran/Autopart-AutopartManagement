package com.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.Account_DAO;
import com.dao.BlogGroup_DAO;
import com.dao.Blog_DAO;
import com.dao.Brand_DAO;
import com.dao.Customer_DAO;
import com.dao.Employee_DAO;
import com.dao.GeneralSettings_DAO;
import com.dao.OrderDetail_DAO;
import com.dao.Order_DAO;
//import com.dao.Permission_DAO;
import com.dao.ProductGroup_DAO;
import com.dao.Product_DAO;
import com.dao.RoleGroup_DAO;
import com.dao.Discount_DAO;

import com.entity.Account;
import com.entity.Brand;
import com.entity.Product;
import com.entity.ProductGroup;
import com.entity.Customer;
import com.entity.Blog;
import com.entity.BlogGroup;
import com.entity.Discount;
import com.entity.Order;
import com.entity.OrderDetail;

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
	Customer_DAO customerDao;
	
	@Autowired
	Discount_DAO discountDao;

	@Autowired
	Blog_DAO blogDao;
	
	@Autowired
	BlogGroup_DAO blogGroupDao;

	@Autowired
	ProductGroup_DAO productGroupDao;

	@Autowired
	Brand_DAO brandDao;

	@Autowired
	Employee_DAO employeeDao;

	@Autowired
	Order_DAO orderDao;
	
	@Autowired
	OrderDetail_DAO orderDetailDao;

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
	
	// -- Brand --
	@RequestMapping("/brand")
	public String showBrands(HttpServletRequest req) {
		List<Brand> brands = brandDao.getAll();
		req.setAttribute("brands", brands);
		return "adminview/brand/index";
	}
	
	@RequestMapping(value = "/brand/add", method= RequestMethod.GET)
	public String addBrand(Model model, HttpServletRequest req) {
		model.addAttribute("brand", new Brand());
		model.addAttribute("nextBrandId", brandDao.generateNextBrandId());
		
		return "adminview/brand/add";
	}
	
	@RequestMapping(value = "/brand/add", method= RequestMethod.POST)
	public String addBrandPost(@ModelAttribute("brand") Brand brand, HttpServletRequest req) {
		List<Brand> brands = brandDao.getAll();
		for(int i=0;i<brands.size();i++) {
			if(brands.get(i).getBrandName().toLowerCase().equals(brand.getBrandName().toLowerCase())){
				return "redirect:/admin/brand.htm";
			} // sửa lại bằng cách viết thêm hàm checkExistByName
		}
		
		brand.setBrandId(brandDao.generateNextBrandId());
		Boolean success = brandDao.add(brand);
		
		return "redirect:/admin/brand.htm";
	}
	
	@RequestMapping(value = "/brand/edit", method= RequestMethod.GET)
	public String editBrand(@RequestParam("brandId") String brandId, HttpServletRequest req) {
		Brand brand = brandDao.getById(brandId);
		req.setAttribute("brand", brand);

		return "adminview/brand/editModal";
	}
	
	@RequestMapping(value = "/brand/edit", method= RequestMethod.POST)
	public String editBrandPatch(@ModelAttribute("brand") Brand brand) {
		System.out.println("-----------------------\n\n" + brand + "-----------------\n\n");
		if (brand.getStatus() == null) {
			brand.setStatus("Inactive");
	    }
		brandDao.update(brand);
		
		return "redirect:/admin/brand.htm";
	} 
	
	@RequestMapping(value = "/brand/detail", method= RequestMethod.GET)
	public String detailBrand(@RequestParam("brandId") String brandId, HttpServletRequest req) {
		Brand brand = brandDao.getById(brandId);
		req.setAttribute("brand", brand);
		return "adminview/brand/detailModal";
	}
	
	@RequestMapping(value = "/brand/changeStatus", method= RequestMethod.POST)
	public String changeStatusBrand(@RequestParam("brandId") String brandId) {
		brandDao.changeStatus(brandId);
		return "adminview/brand/index";
	}
	// -- End brand --
	
	// -- customer --
	@RequestMapping("/customer")
	public String showCustomers(HttpServletRequest req) {
		List<Customer> customers = customerDao.getAll();
		req.setAttribute("customers", customers);
		return "adminview/customer/index";
	}
	
	@RequestMapping(value = "/customer/detail", method= RequestMethod.GET)
	public String detailCustomer(@RequestParam("cusPhone") String cusPhone, HttpServletRequest req) {
		Customer customer = customerDao.getByPhone(cusPhone);
		req.setAttribute("customer", customer);
		return "adminview/customer/detailModal";
	}
	// -- End customer --
	
	// -- blog --
	@RequestMapping("/blog")
	public String showBlogs(HttpServletRequest req) {
		List<Blog> blogs = blogDao.getAll();
		req.setAttribute("blogs", blogs);
		return "adminview/blog/index";
	}
	
	@RequestMapping(value = "/blog/add", method= RequestMethod.GET)
	public String addBlog(Model model, HttpServletRequest req) {
		model.addAttribute("blog", new Blog());
		model.addAttribute("nextBlogId", blogDao.generateNextBlogId());
		
		List<BlogGroup> blogGroups = blogGroupDao.getAll();
		req.setAttribute("blogGroupList", blogGroups);
		
		return "adminview/blog/add";
	}
	
	@RequestMapping(value = "/blog/add", method = RequestMethod.POST)
    public String addBlogPost(@ModelAttribute("blog") Blog blog, HttpServletRequest req) {
        if (blog.getStatus() == null) {
            blog.setStatus("Inactive");
        }
        
        // Set blogGroupId and blogGroup based on the selected blogGroupId
        String blogGroupId = req.getParameter("blogGroupId"); // Get from the form
        if (blogGroupId != null && !blogGroupId.isEmpty()) {
            BlogGroup blogGroup = blogGroupDao.getById(blogGroupId);
            blog.setBlogGroupId(blogGroupId);
            blog.setBlogGroup(blogGroup);
        }
        
        blogDao.add(blog);
        return "redirect:/admin/blog.htm";
    }
	
	@RequestMapping(value = "/blog/edit", method= RequestMethod.GET)
	public String editBlog(@RequestParam("blogId") String blogId, HttpServletRequest req) {
		Blog blog = blogDao.getById(blogId);
		List<BlogGroup> blogGroup = blogGroupDao.getAll();
		req.setAttribute("blog", blog);
		req.setAttribute("blogGroupList", blogGroup);
		
		return "adminview/blog/edit";
	}
	
	@RequestMapping(value = "/blog/edit", method = RequestMethod.POST)
    public String editBlogPatch(@ModelAttribute("blog") Blog blog, @RequestParam("blogGroupId") String blogGroupId) {
        if (blog.getStatus() == null) {
            blog.setStatus("Inactive");
        }
        
        // Set blogGroupId and blogGroup based on the selected blogGroupId
        BlogGroup blogGroup = blogGroupDao.getById(blogGroupId);
        blog.setBlogGroupId(blogGroupId);
        blog.setBlogGroup(blogGroup);
        
        blogDao.update(blog);
        return "redirect:/admin/blog.htm";
    }
	
	@RequestMapping(value = "/blog/detail", method= RequestMethod.GET)
	public String detailBlog(@RequestParam("blogId") String blogId, HttpServletRequest req) {
		Blog blog = blogDao.getById(blogId);
		req.setAttribute("blog", blog);
		return "adminview/blog/detail";
	}
	
	@RequestMapping("/blog/delete")
	public String deleteBlog(@RequestParam("blogId") String blogId, HttpServletRequest req) {
		blogDao.delete(blogId);
		return "redirect:/admin/blog.htm";
	}
	
	@RequestMapping(value = "/blog/changeStatus", method= RequestMethod.POST)
	public String changeStatusBlog(@RequestParam("blogId") String blogId) {
		blogDao.changeStatus(blogId);
		return "adminview/blog/index";
	}
	// -- End blog --
	
	// -- Discount --
	@RequestMapping("/discount")
	public String showDiscounts(HttpServletRequest req) {
		List<Discount> discounts = discountDao.getAll();
		req.setAttribute("discounts", discounts);
		return "adminview/discount/index";
	}
	
	@RequestMapping(value = "/discount/add", method= RequestMethod.GET)
	public String addDiscount(Model model, HttpServletRequest req) {
		model.addAttribute("discount", new Discount());
		model.addAttribute("nextDiscountId", discountDao.generateNextDiscountId());
		
		return "adminview/discount/add";
	}
	
	@RequestMapping(value = "/discount/add", method= RequestMethod.POST)
	public String addDiscountPost(@ModelAttribute("discount") Discount discount, HttpServletRequest req) {
		if (discount.getStatus() == null) {
			discount.setStatus("Inactive");
		}
		
		discountDao.add(discount);
		
		return "redirect:/admin/discount.htm";
	}

	@RequestMapping(value = "/discount/edit", method= RequestMethod.GET)
	public String editDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req) {
		Discount discount = discountDao.getById(discountId);
		req.setAttribute("discount", discount);
		return "adminview/discount/edit";
	}
	
	@RequestMapping(value = "/discount/edit", method= RequestMethod.POST)
	public String editDiscountPatch(@ModelAttribute("discount") Discount discount) {
		if (discount.getStatus() == null) {
			discount.setStatus("Inactive");
		}
		discountDao.update(discount);
		
		return "redirect:/admin/discount.htm";
	}  
	
	@RequestMapping("/discount/delete")
	public String deleteDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req) {
		discountDao.delete(discountId);
		return "redirect:/admin/discount.htm";
	}
	
	@RequestMapping(value = "/discount/detail", method= RequestMethod.GET)
	public String detailDiscount(@RequestParam("discountId") String discountId, HttpServletRequest req) {
		Discount discount = discountDao.getById(discountId);

		req.setAttribute("discount", discount);
		return "adminview/discount/detailModal";
	}
	
	@RequestMapping(value = "/discount/changeStatus", method= RequestMethod.POST)
	public String changeStatusDiscount(@RequestParam("discountId") String discountId) {
		discountDao.changeStatus(discountId);
		return "adminview/discount/index";
	}
	// -- End discount --
	
	// -- Order --
	@RequestMapping("/order")
	public String showOrders(@RequestParam("status") String status, HttpServletRequest req) {
		List<Order> orders;
		
		switch (status) {
			case "confirm":
				orders = orderDao.getOrderByStatus("Wait for confirmation");
				req.setAttribute("orders", orders);
				return "adminview/order/orderConfirm/index";
			case "processing":
				orders = orderDao.getOrderByStatus("Processing");
				req.setAttribute("orders", orders);
				return "adminview/order/orderProcessing";
			case "delivery":
				orders = orderDao.getOrderByStatus("Shipping");
				req.setAttribute("orders", orders);
				return "adminview/order/orderDeli";
			default:
				orders = orderDao.getOrderByStatus("Completed");
				req.setAttribute("orders", orders);
				return "adminview/order/orderHistory";
		}
	}
	
	@RequestMapping(value = "/order/add", method= RequestMethod.GET)
	public String addOrder(Model model, HttpServletRequest req) {
		model.addAttribute("order", new Order());
	    
	    List<Product> products = productDao.getAll();
	    List<Discount> discounts = discountDao.getAll();
	    String nextOrderId = orderDao.generateNextOrderId();
	    
	    model.addAttribute("products", products);
	    model.addAttribute("discounts", discounts);
	    model.addAttribute("nextOrderId", nextOrderId);
	    
		return "adminview/order/add";
	}
	
	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public String addOrderPost(@ModelAttribute("order") Order order, HttpServletRequest req) {
		// Tạo tài khoản khách hàng
		Account acc = new Account(order.getUserPhone(), "1111", null, "RG002", "Active");
		accountDao.add(acc);
		
		// Lưu khách hàng
		Customer cus = new Customer(null, req.getParameter("userName"), order.getUserPhone(), order.getShipAddress(), "Active");
		customerDao.add(cus);
		
		// Đặt các giá trị mặc định
	    if (order.getStatus() == null) {
	        order.setStatus("Processing");
	    }
	    order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));

	    // Chuyển totalCost từ String sang BigDecimal
	    String totalCostStr = req.getParameter("totalCost");
	    if (totalCostStr != null && !totalCostStr.isEmpty()) {
	        order.setTotalCost(new BigDecimal(totalCostStr));
	    }

	    // Lưu Order vào database
	    orderDao.add(order);

	    // Lưu chi tiết đơn hàng
	    if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
	        for (OrderDetail detail : order.getOrderDetails()) {
	        	// Cập nhật số lượng sản phẩm
	        	Product product = productDao.getById(detail.getProductId());
	        	product.setStock(Math.abs(product.getStock() - detail.getAmount()));
	        	productDao.update(product);
	        	
	        	// Lưu chi tiết đơn hàng
	            detail.setOrderId(order.getOrderId()); // Gán orderId cho chi tiết
	            orderDetailDao.add(detail); // Lưu từng chi tiết
	        }
	    }

	    return "redirect:/admin/order.htm?status=processing";
	}

	@RequestMapping(value = "/order/edit", method = RequestMethod.GET)
	public String editOrder(@RequestParam("orderId") String orderId, Model model, HttpServletRequest req) {
	    Order order = orderDao.getById(orderId);
	    if (order == null) {
	        return "redirect:/admin/order.htm?status=processing"; // Nếu không tìm thấy đơn hàng
	    }
	    
	    Customer cus = customerDao.getByPhone(order.getUserPhone());

	    // Lấy danh sách chi tiết đơn hàng
	    List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(orderId);
	    order.setOrderDetails(orderDetails); // Gán danh sách chi tiết vào order

	    // Lấy danh sách sản phẩm để thêm
	    List<Product> products = productDao.getAll();
	    
	    // Lấy danh sách khuyến mãi
	    List<Discount> discounts = discountDao.getAll();
	    
	    model.addAttribute("order", order);
	    model.addAttribute("products", products);
	    model.addAttribute("discounts", discounts);
	    req.setAttribute("title", "Sửa Đơn Hàng");
	    req.setAttribute("userName", cus.getFullName());
	    
	    return "adminview/order/orderConfirm/edit";
	}
	
	@RequestMapping(value = "/order/edit", method = RequestMethod.POST)
	public String editOrderPost(@ModelAttribute("order") Order order, HttpServletRequest req) {
		// Tìm thông tin khách hàng
		Customer existedCus = customerDao.getByPhone(order.getUserPhone());
		if (existedCus == null) {
			Account acc = new Account(order.getUserPhone(), "1111", null, "RG002", "Active");
			accountDao.add(acc);
			
			// Lưu khách hàng
			Customer cus = new Customer(null, req.getParameter("userName"), order.getUserPhone(), order.getShipAddress(), "Active");
			customerDao.add(cus);
		}
		
	    // Đặt các giá trị mặc định nếu cần
	    if (order.getStatus() == null) {
	        order.setStatus("Wait for confirmation");
	    }
	    if (order.getOrderDate() == null) {
	        order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
	    }

	    // Chuyển totalCost từ String sang BigDecimal (nếu cần)
	    String totalCostStr = req.getParameter("totalCost");
	    if (totalCostStr != null && !totalCostStr.isEmpty()) {
	        order.setTotalCost(new BigDecimal(totalCostStr));
	    }

	    // Cập nhật Order
	    orderDao.update(order);

	    // Cập nhật chi tiết đơn hàng
	    if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
	        // Xóa chi tiết cũ (nếu cần) để tránh trùng lặp
	    	List<OrderDetail> odList = orderDetailDao.getAllByOrderId(order.getOrderId());
	    	for (OrderDetail detail : odList) {
	    		Product product = productDao.getById(detail.getProductId());
	        	product.setStock(Math.abs(product.getStock() + detail.getAmount()));
	        	productDao.update(product);
	    	}
	        orderDetailDao.deleteByOrderId(order.getOrderId());
	        
	        // Lưu lại chi tiết mới
	        for (OrderDetail detail : order.getOrderDetails()) {
	        	// Cập nhật số lượng sản phẩm
	        	Product product = productDao.getById(detail.getProductId());
	        	product.setStock(Math.abs(product.getStock() - detail.getAmount()));
	        	productDao.update(product);
	        	
	            detail.setOrderId(order.getOrderId()); // Đảm bảo orderId được gán
	            orderDetailDao.add(detail);
	        }
	    }

	    return "redirect:/admin/order.htm?status=confirm";
	}
	
	@RequestMapping(value = "/order/cancel", method = RequestMethod.GET)
	public String cancelOrder(@RequestParam("orderId") String orderId, HttpServletRequest req) {
	    Order order = orderDao.getById(orderId);
	    if (order != null) {
	        if ("Wait for confirmation".equals(order.getStatus())) {
	            order.setStatus("Cancelled"); 
	            orderDao.update(order); 
	        } else {
	            //req.setAttribute("error", "Chỉ có thể hủy đơn hàng đang chờ xác nhận.");
	        	//return "redirect:/admin/order.htm?status=confirm"; 
	        }
	    }
	    // Nếu không tìm thấy đơn hàng
	    //req.setAttribute("error", "Không tìm thấy đơn hàng.");
	    return "redirect:/admin/order.htm?status=confirm";
	}
	
	@RequestMapping(value = "/order/detail", method= RequestMethod.GET)
	public String detailOrder(@RequestParam("orderId") String orderId, Model model, HttpServletRequest req) {
		Order order = orderDao.getById(orderId);
	    if (order == null) {
	        return "redirect:/admin/order.htm?status=processing"; 
	    }
	    
	    Customer cus = customerDao.getByPhone(order.getUserPhone());

	    // Lấy danh sách chi tiết đơn hàng
	    List<OrderDetail> orderDetails = orderDetailDao.getAllByOrderId(orderId);
	    order.setOrderDetails(orderDetails); // Gán danh sách chi tiết vào order
	    
	    // Lấy danh sách khuyến mãi
	    List<Discount> discounts = discountDao.getAll();
	    
	    model.addAttribute("order", order);
	    model.addAttribute("discounts", discounts);
	    req.setAttribute("title", "Sửa Đơn Hàng");
	    req.setAttribute("userName", cus.getFullName());
		return "adminview/order/orderConfirm/detail";
	}
	// -- End order --
}
