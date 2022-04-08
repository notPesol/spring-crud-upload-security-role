package com.pesol.spring.model;

import java.util.ArrayList;
import java.util.List;

public class ProductInfo {

	private Integer id;
	
	private String name;
	
	private String description;
	
	private Double price;
	
	private Integer quantity;
	
	private List<String> imageUrls = new ArrayList<String>();
	
	public ProductInfo() {
	
	}
	
	public ProductInfo(Integer id, String name, String description, Double price, Integer quantity,
			List<String> imageUrls) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.imageUrls = imageUrls;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public double getAmount() {
		
		return price * quantity;
	}
	
	public String getPath() {
		return "product-images/" + id + '/';
	}
}
