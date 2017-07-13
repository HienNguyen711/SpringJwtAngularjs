package org.spring.jwt.angularjs.filter;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author whois
 *
 */
public class JwtUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean enabled;
	private Date lastPasswordReset;
	
	public JwtUser() {
		super();
	}

	public JwtUser(Long id, String username, String firstname, String lastname,
			String email, String password,
			Collection<? extends GrantedAuthority> authorities,
			boolean enabled, Date lastPasswordReset) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.enabled = enabled;
		this.lastPasswordReset = lastPasswordReset;
	}

	@JsonIgnore
	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@JsonIgnore
	public Date getLastPasswordReset() {
		return lastPasswordReset;
	}
}
