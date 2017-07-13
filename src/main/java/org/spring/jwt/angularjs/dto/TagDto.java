package org.spring.jwt.angularjs.dto;

import org.spring.jwt.angularjs.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

public class TagDto {
	@JsonView(Views.Anonymous.class)
	private Long id;

	@JsonView(Views.Anonymous.class)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
