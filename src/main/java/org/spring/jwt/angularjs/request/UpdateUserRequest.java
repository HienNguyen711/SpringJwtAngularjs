package org.spring.jwt.angularjs.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.spring.jwt.angularjs.validation.EmailValid;

public class UpdateUserRequest {
	
	private Long id;
	@NotNull
	@Size(min = 4, max = 50)
	private String userName;
	@NotNull
	@Size(min = 4, max = 50)
    private String firstName;
	@NotNull
	@Size(min = 4, max = 50)
    private String lastName;
    @NotNull
	@EmailValid
	@Size(min = 4, max = 50)
    private String email;
    @NotNull
	private Boolean enabled;
    @NotNull
	private Boolean expired;
    @NotNull
	private Boolean locked;
    
    public UpdateUserRequest() {
        super();
    }
    public UpdateUserRequest(Long id, String userName, String firstName, String lastName, String email, String password, String matchingPassword,
    		Boolean enabled, Boolean expired, Boolean locked) {
        this.setId(id);
        this.setUserName(userName);
    	this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setEnabled(enabled);
        this.setExpired(expired);
        this.setLocked(locked);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Boolean getExpired() {
		return expired;
	}
	
	public void setExpired(Boolean expired) {
		this.expired = expired;
	}
	
	public Boolean getLocked() {
		return locked;
	}
	
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}