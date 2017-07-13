package org.spring.jwt.angularjs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.spring.jwt.angularjs.views.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "post_detail")
public class PostDetail {
	
    @Id
    @JsonView(Views.Anonymous.class)
    private Long id;

    @Column(name = "post_content", nullable = false)
    @JsonView(Views.Anonymous.class)
    private String content;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Post post;

    public PostDetail() {
    	super();
    }
    
    public PostDetail(String content) {
    	super();
        this.content = content;
    }
    
    public PostDetail(String content, Post post) {
    	super();
        this.content = content;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
}
