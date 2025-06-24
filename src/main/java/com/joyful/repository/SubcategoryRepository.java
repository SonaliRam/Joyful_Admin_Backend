package com.joyful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joyful.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	List<Subcategory> findByCategoryId(Long categoryId);

}
