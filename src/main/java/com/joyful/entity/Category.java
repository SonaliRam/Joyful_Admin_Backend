package com.joyful.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

	@Column(name="ispublished",nullable = false)
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
}
