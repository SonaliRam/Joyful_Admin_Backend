package com.joyful.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties("subcategories") // ignore only this field
	private Category category;

	@OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("subcategory")
	private List<Product> products;
}
