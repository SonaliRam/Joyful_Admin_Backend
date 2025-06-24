
package com.joyful.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Subcategory;
import com.joyful.repository.SubcategoryRepository;
import com.joyful.service.SubcategoryService;

@Service
public class SubcategoryServiceImp implements SubcategoryService {

	@Autowired
	private SubcategoryRepository subcategoryRepo;

	@Override
	public Subcategory addSubcategory(Subcategory subcategory) {
		return subcategoryRepo.save(subcategory);
	}

	@Override
	public Subcategory updateSubcategory(Long id, Subcategory updatedSubcategory) {
		Subcategory sub = subcategoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found"));
		sub.setName(updatedSubcategory.getName());
		sub.setDescription(updatedSubcategory.getDescription());
		sub.setImagePath(updatedSubcategory.getImagePath());
		sub.setMetaTitle(updatedSubcategory.getMetaTitle());
		sub.setMetaDescription(updatedSubcategory.getMetaDescription());
		sub.setSeoKeywords(updatedSubcategory.getSeoKeywords());
		sub.setPublished(updatedSubcategory.isPublished());
		sub.setCategory(updatedSubcategory.getCategory());
		return subcategoryRepo.save(sub);
	}

	@Override
	public void deleteSubcategory(Long id) {
		subcategoryRepo.deleteById(id);
	}

	@Override
	public Subcategory getSubcategoryById(Long id) {
		return subcategoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found"));
	}

	@Override
	public List<Subcategory> getAllSubcategories() {
		return subcategoryRepo.findAll();
	}

	@Autowired
	private SubcategoryRepository subcategoryRepository;

	@Override
	public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
		return subcategoryRepository.findByCategoryId(categoryId);
	}

}
