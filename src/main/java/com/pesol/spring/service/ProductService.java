package com.pesol.spring.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.pesol.spring.entity.Product;
import com.pesol.spring.model.ProductModel;

public interface ProductService {

	List<Product> findAll();
	
	Product findById(Integer id);
	
	void save(ProductModel productModel) throws IOException;
	
	void update(Product product, Integer[] imageIds);
	
	void delete(Product product);
	
	Page<Product> findByPage(int page);
}
