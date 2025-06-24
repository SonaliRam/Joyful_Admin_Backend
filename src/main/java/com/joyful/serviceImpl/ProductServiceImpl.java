package com.joyful.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyful.entity.Product;
import com.joyful.repository.ProductRepository;
import com.joyful.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product updateProduct(Long id, Product updatedProduct) {
		Product existing = getProductById(id);

		existing.setName(updatedProduct.getName());
		existing.setDescription(updatedProduct.getDescription());
		existing.setVariation(updatedProduct.getVariation());
		existing.setSize(updatedProduct.getSize());
		existing.setMainImage(updatedProduct.getMainImage());
		existing.setColorImages(updatedProduct.getColorImages());
		existing.setProductTags(updatedProduct.getProductTags());
		existing.setFilter(updatedProduct.getFilter());
		existing.setMetaTitle(updatedProduct.getMetaTitle());
		existing.setMetaDescription(updatedProduct.getMetaDescription());
		existing.setPageKeywords(updatedProduct.getPageKeywords());
		existing.setPublished(updatedProduct.isPublished());
		existing.setSubcategory(updatedProduct.getSubcategory());

		return productRepository.save(existing);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
