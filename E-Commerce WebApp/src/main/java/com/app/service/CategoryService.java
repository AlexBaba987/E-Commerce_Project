package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.Category;
import com.app.repository.ICategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private ICategoryRepository repo;
	
	public void addCategory(Category category) {
		repo.save(category);
	}
	
	public List<Category> getAllCategory(){
		return repo.findAll();
	}
	
	public void removeCategoriesByID(Integer id) {
		repo.deleteById(id);
	}
	
	public Optional<Category> getCategoryById(Integer id){
		return repo.findById(id);
	}
}
