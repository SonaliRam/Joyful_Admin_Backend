package com.joyful.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joyful.entity.Product;
import com.joyful.entity.Subcategory;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByNameAndSubcategoriesContaining(String name, Subcategory subcategory);

}
