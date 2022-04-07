package com.pesol.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pesol.spring.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

	
}
