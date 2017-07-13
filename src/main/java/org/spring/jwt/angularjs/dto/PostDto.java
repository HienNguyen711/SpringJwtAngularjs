package org.spring.jwt.angularjs.dto;

import java.util.Date;

import org.spring.jwt.angularjs.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

public class PostDto {
	@JsonView(Views.Anonymous.class)
	private Long id;

	@JsonView(Views.Anonymous.class)
	private String title;
	
	@JsonView(Views.Anonymous.class)
	private String description;
	
	@JsonView(Views.Anonymous.class)
	private String content;
	
	@JsonView(Views.Anonymous.class)
	private String logoUrl;
	
	@JsonView(Views.Anonymous.class)
	private Date createAt;
	
	@JsonView(Views.Anonymous.class)
	private String createBy;
	
	@JsonView(Views.User.class)
	private Boolean visible;
	
	@JsonView(Views.Anonymous.class)
	private String visited;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getVisited() {
		return visited;
	}

	public void setVisited(String visited) {
		this.visited = visited;
	}
}
