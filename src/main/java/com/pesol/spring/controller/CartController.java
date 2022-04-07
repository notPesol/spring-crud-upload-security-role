package com.pesol.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.pesol.spring.entity.Product;
import com.pesol.spring.model.CartModel;
import com.pesol.spring.service.ProductService;

@Controller
@SessionAttributes("cart")
public class CartController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/cart")
	public String renderCart(Model model) {
		
		CartModel cart = (CartModel) model.getAttribute("cart");
		
		if (cart == null) {
			model.addAttribute("cart", new CartModel());
		}
		
		return "cart/index";
	}
	
	@GetMapping("/cart/{productId}")
	public String addProductToCart(@PathVariable int productId, Model model) {
		
		Product product = productService.findById(productId);
		
		if (product != null) {
			
			CartModel cart = (CartModel) model.getAttribute("cart");
			
			if (cart == null) {
				cart = new CartModel();
				model.addAttribute("cart", cart);
			}
			
			cart.addProduct(product);
			
			
		}
		
		return "redirect:/products";
	}
	
	@PostMapping("/cart/delete/{productId}")
	public String processDeleteProduct(@PathVariable int productId, Model model) {
		
		 CartModel cart = (CartModel) model.getAttribute("cart");
		
		 if (cart != null) {
			 cart.deleteProduct(productId);
		}
		 
		 return "redirect:/cart";
	}
	
	@PostMapping("/cart/clear")
	public String processClearCart(Model model) {
		
		model.addAttribute("cart", new CartModel());
		
		
		return "redirect:/cart";
	}
	
}
