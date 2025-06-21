package com.joyful.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Subcategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String imagePath;
	private String description;
	private String metaTitle;
	private String metaDescription;
	private String seoKeywords;
	private boolean isPublished;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties("subcategories") // ignore only this field
	private Category category;
}
