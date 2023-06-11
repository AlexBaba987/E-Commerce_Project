package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Category;
import com.app.model.Product;
import com.app.repository.ICategoryRepository;
import com.app.repository.IProductRepository;

@Service
public class ProductService {

	@Autowired
	private IProductRepository repo;
	
	@Autowired
	private ICategoryRepository categoryRepo;
	
	public List<Product> getAllProducts(){
		return repo.findAll();
	}
	
	public void addProduct(Product product) {
		repo.save(product);
	}
	
	public void removeProductById(Integer id) {
		repo.deleteById(id);
	}
	
	public Optional<Product> getProductById(Integer id){
		return repo.findById(id);
	}
	
	public List<Product> getAllProductsByCategoryId(Integer id){
		Category c=categoryRepo.findById(id).get();
		return repo.getAllByDescription(c.getName());
	}

}
