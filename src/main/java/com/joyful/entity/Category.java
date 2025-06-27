package com.joyful.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@Column(name = "description")
	private String description;

	@Column(name = "searchkeywords")
	private String searchkeywords;

	@Column(name = "imagelink")
	private String imagelink;

	@Column(name = "seotitle")
	private String seotitle;

	@Column(name = "seokeywords")
	private String seokeywords;

	@Column(name = "seodescription")
	private String seodescription;
	@Column(nullable = false)
	private boolean Published = false;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("category") // prevent infinite loop
	private List<Subcategory> subcategories;

	public boolean isPublished() {
		return Published;
	}

	public void setPublished(boolean published) {
		this.Published = published;
	}
}