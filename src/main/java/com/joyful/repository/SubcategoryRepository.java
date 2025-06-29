package com.joyful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.joyful.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
	@Query("SELECT s FROM Subcategory s JOIN s.categories c WHERE c.id = :categoryId")
	List<Subcategory> findByCategoryId(@Param("categoryId") Long categoryId);


}
