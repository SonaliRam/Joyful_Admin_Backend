package com.joyful.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Category; // ✅ NEW: Import Category entity
import com.joyful.entity.Subcategory;
import com.joyful.repository.CategoryRepository; // ✅ NEW: Import CategoryRepository
import com.joyful.repository.SubcategoryRepository;
import com.joyful.service.SubcategoryService;

@Service
public class SubcategoryServiceImp implements SubcategoryService {

	@Autowired
	private SubcategoryRepository subcategoryRepo;

	// ✅ NEW: Inject CategoryRepository to fetch category by ID
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public Subcategory addSubcategory(Subcategory subcategory) {
		return subcategoryRepo.save(subcategory);
	}

	@Override
	public Subcategory updateSubcategory(Long id, Subcategory updatedSubcategory) {
		try {
			Subcategory sub = subcategoryRepo.findById(id)
					.orElseThrow(() -> new RuntimeException("Subcategory not found"));

			sub.setName(updatedSubcategory.getName());
			sub.setDescription(updatedSubcategory.getDescription());
			sub.setImagepath(updatedSubcategory.getImagepath());
			sub.setMetatitle(updatedSubcategory.getMetatitle());
			sub.setMetadescription(updatedSubcategory.getMetatitle()); // 👈 double-check this line
			sub.setSeokeywords(updatedSubcategory.getSeokeywords());
			sub.setIspublished(updatedSubcategory.isIspublished());

			Long categoryId = updatedSubcategory.getCategory().getId();
			Category category = categoryRepo.findById(categoryId)
					.orElseThrow(() -> new RuntimeException("Category not found"));
			sub.setCategory(category);

			return subcategoryRepo.save(sub);

		} catch (Exception e) {
			e.printStackTrace(); // ✅ THIS WILL SHOW FULL ERROR IN TERMINAL
			throw new RuntimeException("Failed to update subcategory: " + e.getMessage());
		}
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

	// 🔁 You had duplicate repo, removing extra one and keeping clean
	@Override
	public List<Subcategory> getSubcategoriesByCategory(Long categoryId) {
		return subcategoryRepo.findByCategoryId(categoryId);
	}
}
