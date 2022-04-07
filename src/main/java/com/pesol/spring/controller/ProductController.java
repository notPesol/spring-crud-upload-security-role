package com.pesol.spring.controller;

import java.io.IOException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.pesol.spring.entity.Product;
import com.pesol.spring.model.ProductModel;
import com.pesol.spring.service.ProductService;

@Controller
@RequestMapping("/products")
@SessionAttributes("successMsg")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping
	public String renderIndex(@RequestParam Optional<Integer> page, Model model) {
		
		int pageNum = page.orElse(1);
		
		Page<Product> pages = productService.findByPage(pageNum - 1);
		
		model.addAttribute("pagesProduct", pages);
		
		int totalPages = pages.getTotalPages();
		
		if (totalPages > 0) {
			int[] pageNumbers = new int[totalPages];
			for (int i = 0; i < pageNumbers.length; i++) {
				pageNumbers[i] = i + 1;
			}
			model.addAttribute("pageNumbers", pageNumbers);
		}
				
		return "product/index";
	}
	
	@GetMapping("/add")
	public String renderAdd(Model model) {
		
		model.addAttribute("theProductModel", new ProductModel());
		
		return "product/add";
	}
	
	@PostMapping("/add")
	public String processAdd(@ModelAttribute ProductModel productModel, BindingResult result,
			@RequestParam(name = "images") MultipartFile[] images, Model model) throws IOException {
		
		if (result.hasErrors()) {
			return "product/add";
		}
		
		productModel.setImages(images);
		
		productService.save(productModel);
				
		return "redirect:/products";
	}
	
	@GetMapping("/{id}")
	public String renderDetail(@PathVariable int id, Model model) {
		Product product = productService.findById(id);
		if (product == null) {
			return "redirect:/products";
		}
		
		model.addAttribute("theProduct", product);
		
		System.out.println(product.getProductImages());
		
		return "product/detail";
	}
	
	@GetMapping("/update/{id}")
	public String renderUpdate(@PathVariable int id, Model model) {
		
		Product product = productService.findById(id);
		if (product == null) {
			return "redirect:/products";
		}
		
		ProductModel productModel = new ProductModel(
				product.getId(), product.getName(), product.getPrice(), product.getDescription(), 
				product.getQuantity(), product.getProductImages());
		
		model.addAttribute("theProductModel", productModel);
		
		return "product/update";
	}
	
	@PostMapping("/update/{id}")
	public String processUpdate(@PathVariable int id, @Valid @ModelAttribute ProductModel productModel,
			BindingResult result, @RequestParam(name = "imageId", required = false) Integer[] imageId, Model model) {
		
		Product product = productService.findById(id);
		
		if (product == null) {
			return "redirect:/products";
		} else if (result.hasErrors()) {
			return "product/update";
		}
		
		product.setName(productModel.getName());
		product.setDescription(productModel.getDescription());
		product.setPrice(productModel.getPrice());
		product.setQuantity(productModel.getQuantity());
			
		productService.update(product, imageId);
		
		return "redirect:/products";
		
	}
	
	@PostMapping("/delete/{id}")
	public String processDelete(@PathVariable int id, Model model) {
		
		Product product = productService.findById(id);
		
		if (product != null) {
			productService.delete(product);
			model.addAttribute("successMsg", "Delete product id " + id + " successfully");
		}
		
		return "redirect:/products";
	}
}
