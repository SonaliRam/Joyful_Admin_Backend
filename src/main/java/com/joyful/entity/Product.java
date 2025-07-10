package com.joyful.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "mainimage")
	private String mainimage;

	@Column(name = "hoverimage")
	private String hoverimage;

	@ElementCollection
	@Column(name = "producttags")
	private List<String> producttags;

	@Column(name = "filter")
	private String filter;

	// SEO Fields
	@Column(name = "metatitle")
	private String metatitle;

	@Column(name = "metadescription", columnDefinition = "TEXT")
	private String metadescription;

	@Column(name = "pagekeywords")
	private String pagekeywords;

	@Column(name = "ispublished", nullable = false)
	private Boolean ispublished;

	@Column(name = "newarrival", nullable = false)
	private Boolean newarrival;

	public void setIspublished(boolean ispublished) {
		this.ispublished = ispublished;
	}

	// Subcategory Relationship
	@ManyToMany
	@JoinTable(name = "productsubcategory", joinColumns = @JoinColumn(name = "productid"), inverseJoinColumns = @JoinColumn(name = "subcategoryid"))
	@JsonIgnoreProperties("products")
	private Set<Subcategory> subcategories = new HashSet<>();

	@Basic(fetch = FetchType.EAGER) // ✅ This is the key fix
	@Column(name = "variantsmap", columnDefinition = "TEXT")
	private String variantsMap;

}