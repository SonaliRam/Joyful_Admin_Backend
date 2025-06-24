package com.joyful.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name; // Product name

	private String description;

	private String variation; // Any custom variation

	private String size;

	private String mainImage; // Path to the main image

	@ElementCollection
	@CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_id"))
	@MapKeyColumn(name = "color")
	@Column(name = "image_path")
	private Map<String, String> colorImages; // Map<Color, SubImagePath>

	@ElementCollection
	private List<String> productTags; // e.g., ["eco-friendly", "best seller"]

	private String filter; // Can later be changed to a list if needed

	// SEO Fields
	private String metaTitle;
	private String metaDescription;
	private String pageKeywords;

	private boolean isPublished;

	// Subcategory Relationship
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	@JsonIgnoreProperties("products")
	private Subcategory subcategory;
}
