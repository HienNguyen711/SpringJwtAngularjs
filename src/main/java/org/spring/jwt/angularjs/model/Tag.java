package org.spring.jwt.angularjs.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;
import org.spring.jwt.angularjs.views.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "tag")
public class Tag {
	@JsonView(Views.Anonymous.class)
	@Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
 
	@JsonView(Views.Anonymous.class)
    @NaturalId(mutable = true)
    private String name;
	
	@Column(name = "visible", nullable = false)
	@JsonView(Views.Admin.class)
	private boolean visible;
 
	@JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();
 
    public Tag() {}
 
    public Tag(String name) {
        this.name = name;
    }
    
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
 
}
