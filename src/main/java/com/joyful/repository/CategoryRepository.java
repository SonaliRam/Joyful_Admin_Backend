package com.joyful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joyful.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
