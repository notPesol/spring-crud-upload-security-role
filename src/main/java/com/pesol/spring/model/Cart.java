package com.pesol.spring.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.pesol.spring.entity.Product;

public class Cart {

	private List<Product> products = new ArrayList<Product>();

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		
		boolean match = products.stream()
				.anyMatch(p -> p.getId() == product.getId());
		
		if (!match) {
			products.add(product);
		}
	}
	
	public void deleteProduct(int productId) {
		products = products.stream()
				.filter(product -> product.getId() != productId)
				.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "CartModel [products=" + products + "]";
	}
	
	
}
