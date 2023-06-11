package com.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ProductDTO;
import com.app.model.Category;
import com.app.model.Product;
import com.app.service.CategoryService;
import com.app.service.ProductService;

@Controller
public class AdminController {
	
	public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	@Autowired
	private CategoryService service;
	@Autowired
	private ProductService productService;
	
	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}

	//Category Section
	@GetMapping("/admin/categories")
	public String getCategories(Model model) {
		model.addAttribute("categories",service.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCategoriesAdd(Model model) {
		model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCategoriesAdd(@ModelAttribute("category") Category category) {
		service.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String getCategoriesDelete(@PathVariable Integer id) {
		service.removeCategoriesByID(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String getCategoriesUpdate(@PathVariable Integer id,Model model) {
		Optional<Category> category=service.getCategoryById(id);
			model.addAttribute("category",category.get());
			return "categoriesAdd";
	}
	
	//product section
	@GetMapping("/admin/products")
	public String getProduct(Model model) {
		model.addAttribute("products",productService.getAllProducts());
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String getProductAdd(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories",service.getAllCategory());
		return "productsAdd";
	}
	
	
	@PostMapping("/admin/products/add")
	public String postProductAdd(@ModelAttribute("productDTO") ProductDTO productDTO,
																								@RequestParam("productImage") MultipartFile file,
																								@RequestParam("imgName") String imgName) throws IOException {
		Product prod=new Product();
		prod.setId(productDTO.getId());
		prod.setName(productDTO.getName());
		prod.setCategory(service.getCategoryById(productDTO.getCategoryId()).get());
		prod.setPrice(productDTO.getPrice());
		prod.setWeight(productDTO.getWeight());
		prod.setDescription(productDTO.getDescription());
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID=file.getOriginalFilename();
			Path fileNameAndPath=Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}
		else {
			imageUUID=imgName;
		}
		prod.setImageName(imageUUID);
		productService.addProduct(prod);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String getDeleteProduct(@PathVariable Integer id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	
	@GetMapping("/admin/product/update/{id}")
	public String getUpdateProduct(@PathVariable Integer id,Model model) {
		Product prod=productService.getProductById(id).get();
		ProductDTO productDTO=new ProductDTO();
		productDTO.setId(prod.getId());
		productDTO.setName(prod.getName());
		productDTO.setCategoryId(prod.getCategory().getId());
		productDTO.setPrice(prod.getPrice());
		productDTO.setWeight(prod.getWeight());
		productDTO.setDescription(prod.getDescription());
		productDTO.setImageName(prod.getImageName());
		
		model.addAttribute("categories",service.getAllCategory());
		model.addAttribute("productDTO",productDTO);
		
		return "productsAdd";
	}
}
