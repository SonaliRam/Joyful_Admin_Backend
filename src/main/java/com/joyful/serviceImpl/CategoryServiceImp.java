package com.joyful.serviceImpl;

import com.joyful.entity.Category;
import com.joyful.repository.CategoryRepository;
import com.joyful.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Category addCategory(Category category) {
		return categoryRepo.save(category);
	}

	@Override
	public Category updateCategory(Long id, Category updatedCategory) {
		Category cat = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
		cat.setName(updatedCategory.getName());
		cat.setDescription(updatedCategory.getDescription());
		cat.setImagePath(updatedCategory.getImagePath());
		cat.setSearchKeywords(updatedCategory.getSearchKeywords());
		cat.setSeoTitle(updatedCategory.getSeoTitle());
		cat.setSeoKeywords(updatedCategory.getSeoKeywords());
		cat.setSeoDescription(updatedCategory.getSeoDescription());
		cat.setPublished(updatedCategory.isPublished());
		return categoryRepo.save(cat);
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepo.findAll();
	}
}
