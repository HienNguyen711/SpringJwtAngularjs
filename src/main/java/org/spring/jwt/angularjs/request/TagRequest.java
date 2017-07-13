package org.spring.jwt.angularjs.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagRequest {
	private Long id;
	@NotNull
	@Size(min = 3, max = 255)
	private String name;
	
	@NotNull
	private Boolean visible;
	
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

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
