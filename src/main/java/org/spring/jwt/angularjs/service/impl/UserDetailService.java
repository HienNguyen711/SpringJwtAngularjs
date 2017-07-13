package org.spring.jwt.angularjs.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.exception.UserNotEnabledException;
import org.spring.jwt.angularjs.filter.JwtUser;
import org.spring.jwt.angularjs.model.Authority;
import org.spring.jwt.angularjs.model.User;
import org.spring.jwt.angularjs.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * The AccountService business service.
	 */
	@Autowired
	private IUserService userService;

	public JwtUser loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("> loadUserByUsername {}", username);
		try {
			User user = userService.findByUsername(username);
			
			if (user == null) {
				// Not found...
				throw new UsernameNotFoundException("User " + username + " not found.");
			} else if (!user.isEnabled() || user.isExpired() || user.isLocked()) {
				throw new UserNotEnabledException("User " + username + " was not enabled");
			}

			if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
				// No Roles assigned to user...
				throw new UsernameNotFoundException("User not authorized.");
			}

			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			for (Authority authority : user.getAuthorities()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName().name()));
			}

			JwtUser userDetails = new JwtUser(user.getId(),
					user.getUserName(), user.getFirstName(),
					user.getLastName(), user.getEmail(),
					user.getPassword(), grantedAuthorities, user.isEnabled(),
					user.getLastPasswordReset());

			logger.debug("< loadUserByUsername {}", username);
			return userDetails;
			
		} catch (ServiceException e) {
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		
	}
}
