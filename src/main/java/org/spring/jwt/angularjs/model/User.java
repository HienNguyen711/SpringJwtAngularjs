package org.spring.jwt.angularjs.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.spring.jwt.angularjs.views.Views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Anonymous.class)
    private Long id;

    @Column(name = "username", length = 50, unique = true)
    @NotNull
    @JsonView(Views.Anonymous.class)
    private String userName;

    @JsonIgnore
    @Column(name = "password", length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @JsonView(Views.Anonymous.class)
    @Column(name = "firstname", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String firstName;

    @JsonView(Views.Anonymous.class)
    @Column(name = "lastname", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String lastName;

    @JsonView(Views.Anonymous.class)
    @Column(name = "email", length = 50)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @JsonView(Views.Admin.class)
    @Column(name = "enabled")
    @NotNull
    private boolean enabled;
    
    @JsonView(Views.Admin.class)
	@Column(name = "expired")
    @NotNull
    private boolean expired;

    @JsonView(Views.Admin.class)
    @Column(name = "locked")
    @NotNull
    private boolean locked;
    
    @JsonView(Views.Admin.class)
    @Column(name = "last_password_reset")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date lastPasswordReset;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    private Set<Authority> authorities = new HashSet<>();
    
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	public Date getLastPasswordReset() {
		return lastPasswordReset;
	}

	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return this.lastName + " " + this.firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
	
	public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }
 
    public void removePost(Post post) {
    	posts.remove(post);
    	post.setUser(null);
    }
    
    public void addAuthority(Authority auth) {
        authorities.add(auth);
        auth.getUsers().add(this);
    }
 
    public void removeAuthority(Authority auth) {
    	authorities.remove(auth);
    	auth.getUsers().remove(this);
    }
}