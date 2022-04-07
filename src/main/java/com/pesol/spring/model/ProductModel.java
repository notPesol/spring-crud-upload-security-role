package com.pesol.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.pesol.spring.entity.ProductImage;

public class ProductModel {
	
	private Integer id;

	@NotBlank(message = "is required")
	private String name;
	
	private Double price;
	
	private String description;
	
	private Integer quantity;
	
	private MultipartFile[] images;
	
	private List<ProductImage> productImages = new ArrayList<ProductImage>();
	
	public ProductModel() {
	
	}
	
	

	public ProductModel(Integer id, String name, Double price, String description, Integer quantity,
			List<ProductImage> productImages) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.productImages = productImages;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public MultipartFile[] getImages() {
		return images;
	}

	public void setImages(MultipartFile[] images) {
		this.images = images;
	}



	public List<ProductImage> getProductImages() {
		return productImages;
	}



	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}

	
	public String getPath() {
		if (id == null) {
			return null;
		}
		
		return "/product-images/" + id + '/';
	}
	
}
