package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.global.GlobalData;
import com.app.service.CategoryService;
import com.app.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService productService;
	
	@GetMapping({"/","/home"})
	public String home(Model model) {
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "index";
		}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("products",productService.getAllProducts());
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "shop";
		}
	
	@GetMapping("/shop/category/{id}")
	public String getShopByCategory(Model model,@PathVariable Integer id) {
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("products",productService.getAllProductsByCategoryId(id));
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "shop";
		}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String getViewProduct(Model model,@PathVariable Integer id) {
		model.addAttribute("product",productService.getProductById(id).get());
		model.addAttribute("cartCount",GlobalData.cart.size());
		return "viewProduct";
		}
	
}
