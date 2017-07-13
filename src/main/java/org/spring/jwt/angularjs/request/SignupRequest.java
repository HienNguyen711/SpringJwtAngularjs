package org.spring.jwt.angularjs.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.spring.jwt.angularjs.validation.EmailValid;
import org.spring.jwt.angularjs.validation.PasswordMatches;
import org.spring.jwt.angularjs.validation.PasswordValid;

@PasswordMatches
public class SignupRequest {
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
	@PasswordValid
	@Size(min = 8, max = 50)
    private String password;
	@NotNull
    private String matchingpassword;
	@NotNull
	private String recaptcha;
    
    public SignupRequest() {
        super();
    }
    
    public SignupRequest(String firstName, String lastName, String email, String password, String matchingpassword, String recaptcha) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(matchingpassword);
        this.setMatchingpassword(matchingpassword);
        this.setRecaptcha(recaptcha);
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

	public String getMatchingpassword() {
		return matchingpassword;
	}

	public void setMatchingpassword(String matchingpassword) {
		this.matchingpassword = matchingpassword;
	}

	public String getRecaptcha() {
		return recaptcha;
	}

	public void setRecaptcha(String recaptcha) {
		this.recaptcha = recaptcha;
	}
	
}