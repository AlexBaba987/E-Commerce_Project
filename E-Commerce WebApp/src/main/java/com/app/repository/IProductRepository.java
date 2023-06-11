package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.Product;

public interface IProductRepository extends JpaRepository<Product, Integer> {

	//public List<Product> getAllProductsByCategoryId(Integer id);

	public List<Product> getAllProductsByCategoryId(Integer id);

	public List<Product> getAllByDescription(String name);
	
}
