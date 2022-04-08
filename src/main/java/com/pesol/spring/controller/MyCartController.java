package com.pesol.spring.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pesol.spring.entity.Product;
import com.pesol.spring.entity.ProductImage;
import com.pesol.spring.model.MyCart;
import com.pesol.spring.model.ProductInfo;
import com.pesol.spring.service.ProductService;

@Controller
@SessionAttributes("cart")
public class MyCartController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/cart")
	public String renderCart(Model model) {
		
		MyCart cart = (MyCart) model.getAttribute("cart");
		
		if (cart == null) {
			cart = new MyCart();
			model.addAttribute("cart", cart);
		}
		
		return "myCart/index";
	}
	
	@GetMapping("/cart/{productId}")
	public String addProductToCart(@PathVariable int productId, @RequestParam Optional<Integer> quantity, Model model) {
		
		Product product = productService.findById(productId);
		
		if (product != null) {
			
			MyCart cart = (MyCart) model.getAttribute("cart");
			
			if (cart == null) {
				cart = new MyCart();
				model.addAttribute("cart", cart);
			}
			
			ProductInfo productInfo = new ProductInfo(
					product.getId(), product.getName(), product.getDescription(), product.getPrice(),
					0, toListString(product.getProductImages()));
			
			cart.addProduct(productInfo, quantity.orElse(0));
			
			
		}
		
		return "redirect:/products";
	}
	
	@PostMapping("/cart/delete/{productId}")
	public String processDeleteProduct(@PathVariable int productId, Model model) {
		
		MyCart cart = (MyCart) model.getAttribute("cart");
		
		 if (cart != null) {
			 cart.deleteProduct(productId);
		}
		 
		 return "redirect:/cart";
	}
	
	@PostMapping("/cart/clear")
	public String processClearCart(Model model) {
		
		model.addAttribute("cart", new MyCart());
		
		
		return "redirect:/cart";
	}
	
	
	
	private List<String> toListString(List<ProductImage> pm) {
		
		return pm.stream()
			.map(p -> p.getName())
			.collect(Collectors.toList());
	}
	
}
