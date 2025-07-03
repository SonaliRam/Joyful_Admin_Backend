package com.joyful.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subcategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "imagepath")
	private String imagepath;

	@Column(name = "metatitle")
	private String metatitle;

	@Column(name = "ispublished")
	private boolean ispublished;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "metadescription", columnDefinition = "TEXT")
	private String metadescription;

	@Column(name = "seokeywords", columnDefinition = "TEXT")
	private String seokeywords;

	@ManyToMany
	@JoinTable(name = "subcategory_category", joinColumns = @JoinColumn(name = "subcategory_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	@JsonIgnoreProperties("subcategories")
	private List<Category> categories;

//	@ManyToMany(mappedBy = "subcategories")
//	@JsonIgnoreProperties("subcategories")
//	private Set<Product> products = new HashSet<>();
	
	@ManyToMany(mappedBy = "subcategories")
	@JsonIgnore // ✅ temporarily skip products in JSON to fix 500 error
	private Set<Product> products = new HashSet<>();

}
