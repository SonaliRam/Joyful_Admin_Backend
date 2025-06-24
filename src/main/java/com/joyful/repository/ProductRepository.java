package com.joyful.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.joyful.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	// Custom query methods can be added here if needed later
}
