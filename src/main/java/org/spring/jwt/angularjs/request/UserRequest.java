package org.spring.jwt.angularjs.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.spring.jwt.angularjs.validation.EmailValid;
import org.spring.jwt.angularjs.validation.PasswordValid;

public class UserRequest {
	@NotNull
	@Size(min = 3, max = 50)
	private String userName;
	@NotNull
	@Size(min = 3, max = 50)
    private String firstName;
	@NotNull
	@Size(min = 3, max = 50)
    private String lastName;
	@NotNull
	@EmailValid
	@Size(min = 3, max = 50)
    private String email;
	@NotNull
	@PasswordValid
	@Size(min = 8, max = 50)
    private String password;
	@NotNull
	private Boolean enabled;
	@NotNull
	private Boolean expired;
	@NotNull
	private Boolean locked;
    
    public UserRequest() {
        super();
    }
    public UserRequest(String userName, String firstName, String lastName, String email, String password, String matchingPassword,
    		Boolean enabled, Boolean expired, Boolean locked) {
    	this.setUserName(userName);
    	this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setEnabled(enabled);
        this.setExpired(expired);
        this.setLocked(locked);
    }
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}