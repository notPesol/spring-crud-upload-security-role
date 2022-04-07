package com.pesol.spring.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pesol.spring.entity.Product;
import com.pesol.spring.entity.ProductImage;
import com.pesol.spring.model.ProductModel;
import com.pesol.spring.repository.ProductImageRepository;
import com.pesol.spring.repository.ProductRepository;
import com.pesol.spring.util.FileUploadUtils;

@Service
public class ProductServiceImpl implements ProductService {

	private static final int ROW_PER_PAGE = 5;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductImageRepository productImageRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(Integer id) {
		Optional<Product> result = productRepository.findById(id);
		return result.orElse(null);
	}

	@Override
	public void save(ProductModel productModel) throws IOException {
		Product product = new Product(productModel.getName(), productModel.getPrice(), productModel.getDescription(),
				productModel.getQuantity());

		MultipartFile[] images = productModel.getImages();

		product = productRepository.save(product);

		for (MultipartFile file : images) {
			if (file.isEmpty()) {
				continue;
			}

			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			product.addProductImage(new ProductImage(fileName));

			String uploadDir = "product-images/" + product.getId();

			FileUploadUtils.saveFile(uploadDir, fileName, file);
		}

		productRepository.save(product);

	}

	@Override
	public void update(Product product, Integer[] imageIds) {

		if (imageIds != null) {
			for (Integer id : imageIds) {
				Optional<ProductImage> result = productImageRepository.findById(id);
				if (result.isPresent()) {
					ProductImage productImage = result.get();
					product.deleteProductImage(productImage);
					FileUploadUtils.deleteFile(product.getId() + File.separator + productImage.getName());
				}
			}
		}

		productRepository.save(product);

	}

	@Override
	public void delete(Product product) {

		FileUploadUtils.deleteFile(product.getId().toString());

		productRepository.delete(product);

	}

	@Override
	public Page<Product> findByPage(int page) {
		
		if (page < 0) {
			page = 0;
		}
		
		Pageable pageable = PageRequest.of(page, ROW_PER_PAGE, Sort.by("id"));
		
		Page<Product> pages = productRepository.findAll(pageable);
		
		return pages;
	}

}
