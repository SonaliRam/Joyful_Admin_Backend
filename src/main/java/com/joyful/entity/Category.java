package com.joyful.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "searchkeywords")
	private String searchkeywords;

	@Column(name = "imagelink")
	private String imagelink;

	@Column(name = "seotitle")
	private String seotitle;

	@Column(name = "seokeywords")
	private String seokeywords;

	@Column(name = "seodescription", columnDefinition = "TEXT")
	private String seodescription;

	@Column(name = "ispublished", nullable = false)
	private boolean isPublished = false;

	// ✅ CORRECTED MANY-TO-MANY relationship with Subcategory
	@ManyToMany(mappedBy = "categories")
	@JsonIgnoreProperties("categories")
	private List<Subcategory> subcategories;

	public boolean getPublished() {
		return isPublished;
	}

	public void setPublished(boolean ispublished) {
		this.isPublished = ispublished;
	}

	@Transient
	@JsonIgnoreProperties("subcategories") // optional if you want to avoid recursive loop
	private Set<Product> products = new HashSet<>();

	public Set<Product> getProducts() {
		Set<Product> allProducts = new HashSet<>();
		if (this.subcategories != null) {
			for (Subcategory sub : this.subcategories) {
				if (sub.getProducts() != null) {
					allProducts.addAll(sub.getProducts());
				}
			}
		}
		return allProducts;
	}
}
