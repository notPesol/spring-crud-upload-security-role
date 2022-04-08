package com.pesol.spring.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyCart {

	private Map<Integer, ProductInfo> products = new LinkedHashMap<Integer, ProductInfo>();

	public Map<Integer, ProductInfo> getProducts() {
		return products;
	}

	public void setProducts(Map<Integer, ProductInfo> products) {
		this.products = products;
	}
	
	public void addProduct(ProductInfo productInfo, int quantity) {
		
		ProductInfo product = products.get(productInfo.getId());
		
		if (product == null) {
			product = productInfo;
			product.setQuantity(0);
			
			products.put(product.getId(), product);
		} 
		
		int newQuantity = product.getQuantity() + quantity;
		if (newQuantity <= 0) {
			products.remove(product.getId());
		} else {				
			product.setQuantity(newQuantity);
		}
	}
	
//	public void updateProduct(Integer productId, int quantity) {
//		
//		ProductInfo product = products.get(productId);
//		
//		if (product != null) {
//			if (quantity <= 0) {
//				products.remove(productId);
//			} else {
//				product.setQuantity(quantity);
//			}
//		}
//	}
		
	public void deleteProduct(int productId) {
		products.remove(productId);
	}
	
	public void clear() {		
		products.clear();
	}
	
	public boolean isEmpty() {
		return products.isEmpty();
	}
	
	public int getTotalQuantity() {
		int totalQuantity = 0;
		for (Map.Entry<Integer, ProductInfo> entry : products.entrySet()) {
			ProductInfo val = entry.getValue();
			totalQuantity += val.getQuantity();
		}
		
		return totalQuantity;
	}
	
	public double getTotalAmount() {
		double totalAmount = 0;
		for (ProductInfo product : products.values()) {
			totalAmount += product.getAmount();
		}
		
		return totalAmount;
	}
}
