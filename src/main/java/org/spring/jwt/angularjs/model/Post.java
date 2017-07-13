package org.spring.jwt.angularjs.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.spring.jwt.angularjs.views.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	@JsonView(Views.Anonymous.class)
	private Long id;

	@Column(name = "title", nullable = false)
	@JsonView(Views.Anonymous.class)
	private String title;
	
	@Column(name = "description", nullable = false)
	@JsonView(Views.Anonymous.class)
	private String description;
	
	@Column(name = "logo_url", nullable = true)
	@JsonView(Views.Anonymous.class)
	private String logoUrl;
	
	@Column(name = "create_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonView(Views.Anonymous.class)
	private Date createAt;
	
	@Column(name = "visible", nullable = false)
	@JsonView(Views.Admin.class)
	private boolean visible;
	
	@JsonView(Views.Anonymous.class)
	@Column(name = "visited")
	private Integer visited;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by", nullable = false)
    private User user;

	public Post() {
		this.createAt = new Date();
		this.visited = 0;
	}
	
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }
 
    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
    }
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
    public Integer getVisited() {
		return visited;
	}

	public void setVisited(Integer visited) {
		this.visited = visited;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        return id != null && id.equals(((Post) o).id);
    }
 
    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return String.format("Post[%d]", this.id);
	}
}
