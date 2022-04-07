package com.pesol.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pesol.spring.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query("SELECT DISTINCT p FROM Product p LEFT JOIN p.productImages pp WHERE p.id = ?1")
	Optional<Product> findById(Integer id);
}
