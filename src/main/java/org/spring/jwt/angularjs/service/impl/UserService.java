package org.spring.jwt.angularjs.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.spring.jwt.angularjs.constant.AuthoritiesConstants;
import org.spring.jwt.angularjs.constant.SystemConstant;
import org.spring.jwt.angularjs.event.OnRegistrationCompleteEvent;
import org.spring.jwt.angularjs.event.OnResetPasswordCompleteEvent;
import org.spring.jwt.angularjs.exception.EmailExistsException;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.exception.TokenNotFoundException;
import org.spring.jwt.angularjs.exception.UserNotFoundException;
import org.spring.jwt.angularjs.model.Authority;
import org.spring.jwt.angularjs.model.AuthorityName;
import org.spring.jwt.angularjs.model.PasswordResetToken;
import org.spring.jwt.angularjs.model.User;
import org.spring.jwt.angularjs.model.VerificationToken;
import org.spring.jwt.angularjs.repository.AuthorityRepository;
import org.spring.jwt.angularjs.repository.PasswordResetTokenRepository;
import org.spring.jwt.angularjs.repository.UserRepository;
import org.spring.jwt.angularjs.repository.VerificationTokenRepository;
import org.spring.jwt.angularjs.request.SignupRequest;
import org.spring.jwt.angularjs.request.UpdateUserRequest;
import org.spring.jwt.angularjs.request.UserRequest;
import org.spring.jwt.angularjs.service.IUserService;
import org.spring.jwt.angularjs.util.CommonUtils;
import org.spring.jwt.angularjs.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 
 * @author whois
 *
 */
@Service
@Transactional
public class UserService extends GenericService<User, Long> implements IUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	public User findByUsername(String username) throws ServiceException {
		try {
			return userRepository.findByUserName(username);
		} catch (Exception e) {
			throw new ServiceException("PostService --> findByUsername()", e);
		}
	}

	@Override
	public User findUserByEmail(String email) throws ServiceException {
		try {
			return userRepository.findByEmail(email);
		} catch (Exception e) {
			throw new ServiceException("PostService --> findUserByEmail()", e);
		}
	}

	@Override
	public void createVerificationToken(User user, String token) throws ServiceException {
		try {
			VerificationToken userToken = new VerificationToken(user, token);
	        verificationTokenRepository.save(userToken);
		} catch (Exception e) {
			throw new ServiceException("PostService --> createVerificationToken()", e);
		}
	}

	@Override
	public void createPasswordResetToken(User user, String token) throws ServiceException {
		try {
			PasswordResetToken myToken = passwordResetTokenRepository.findOne(user.getId());
			if (myToken != null) {
				myToken.setToken(token);
				myToken.setExpiry(CommonUtils.calculateExpiryDate(SystemConstant.EXPIRY_DATE));
			} else {
				myToken = new PasswordResetToken(user, token);
				passwordResetTokenRepository.save(myToken);
			}
		} catch (Exception e) {
			throw new ServiceException("PostService --> createPasswordResetToken()", e);
		}
	}
	
	@Override
	public void verificationToken(String token) throws ServiceException {
		try {
			VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
			if (verificationToken == null) throw new TokenNotFoundException("Token is not found");
			
			verificationToken.getUser().setEnabled(true);
			verificationTokenRepository.delete(verificationToken);
		} catch (Exception e) {
			throw new ServiceException("PostService --> verificationToken()", e);
		}
	}

	@Override
	public User createUser(UserRequest userRequest) throws ServiceException {
		try {
			// 1. check if user exist
			User existingUser = userRepository.findByEmail(userRequest.getEmail());
			if (existingUser != null) throw new EmailExistsException("Email exist");

			// 2. set new user
			User user = new User();
			user.setUserName(userRequest.getUserName());
			user.setEmail(userRequest.getEmail());
			user.setFirstName(userRequest.getFirstName());
			user.setLastName(userRequest.getLastName());
			user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
			user.setLastPasswordReset(new Date());
			user.setEnabled(true);
			user.setExpired(false);
			user.setLocked(false);

			// 3. add authority
			Authority authority = authorityRepository.findByName(AuthorityName.ROLE_USER);
			if (authority != null) {
				Set<Authority> authorities = new HashSet<Authority>();
				authorities.add(authority);
				user.setAuthorities(authorities);
			}

			// 4. save user
			userRepository.save(user);
			
			return user;
		} catch (Exception e) {
			throw new ServiceException("PostService --> createUser()", e);
		}
	}

	@Override
	public void updateUser(UpdateUserRequest updateUserRequest) throws ServiceException {
		try {
			// 1. check if user exist
			User existingUser = userRepository.findOne(updateUserRequest.getId());
			if (existingUser == null) throw new UserNotFoundException("User is not exist");

			// 2. set new user
			existingUser.setUserName(updateUserRequest.getUserName());
			existingUser.setFirstName(updateUserRequest.getFirstName());
			existingUser.setLastName(updateUserRequest.getLastName());
			existingUser.setEmail(updateUserRequest.getEmail());
			existingUser.setEnabled(updateUserRequest.getEnabled());
			existingUser.setExpired(updateUserRequest.getExpired());
			existingUser.setLocked(updateUserRequest.getLocked());

			// 4. save user
			userRepository.save(existingUser);
		} catch (Exception e) {
			throw new ServiceException("PostService --> updateUser()", e);
		}
	}

	@Override
	public void deleteUser(Long id) throws ServiceException {
		try {
			if(!SecurityUtils.isAuthenticated() || !SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
				throw new AccessDeniedException("Access deniel");
	    	} 

	    	userRepository.delete(id);
		} catch (Exception e) {
			throw new ServiceException("PostService --> deleteUser()", e);
		}
	}

	@Override
	public void signUp(SignupRequest signUpRequest, HttpServletRequest httpRequest, Device device) throws ServiceException {
		try {
			// 1. check if user exist
			User existingUser = userRepository.findByEmail(signUpRequest.getEmail());
			if (existingUser != null) throw new EmailExistsException("Email exist");

			// 2. set new user
			User user = new User();
			user.setUserName(signUpRequest.getEmail());
			user.setEmail(signUpRequest.getEmail());
			user.setFirstName(signUpRequest.getFirstName());
			user.setLastName(signUpRequest.getLastName());
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			user.setEnabled(false);
			user.setLocked(false);
			user.setExpired(false);
			user.setLastPasswordReset(new Date());
			
			// 3. add authority
			Authority authority = authorityRepository.findByName(AuthorityName.ROLE_USER);
			if (authority != null) {
				Set<Authority> authorities = new HashSet<Authority>();
				authorities.add(authority);
				user.setAuthorities(authorities);
			}

			// 4. save user
			userRepository.save(user);

			// 5. send email verification
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,
					httpRequest.getLocale(), CommonUtils.getBaseUrl(httpRequest),
					device));
		} catch (Exception e) {
			throw new ServiceException("PostService --> signUp()", e);
		}
	}

	@Override
	public void resetPassword(String email, HttpServletRequest httpRequest, Device device) throws ServiceException {
		try {
			// 1. check if user exist
			User existingUser = userRepository.findByEmail(email);
			if (existingUser == null) throw new EmailExistsException("Email exist");

			// 2. send email verification
			eventPublisher.publishEvent(new OnResetPasswordCompleteEvent(
					existingUser, httpRequest.getLocale(), CommonUtils
							.getBaseUrl(httpRequest), device));
		} catch (Exception e) {
			throw new ServiceException("PostService --> resetPassword()", e);
		}
	}

	@Override
	public void resetNewPassword(String token, String newPassword) throws ServiceException {
		try {
			// check token expired
			PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
			if (passwordResetToken == null || passwordResetToken.isExpired()) {
				throw new TokenNotFoundException("Email exist");
			}
			
			User user = passwordResetToken.getUser();
			user.setPassword(passwordEncoder.encode(newPassword));
			user.setLastPasswordReset(new Date());
			
			passwordResetTokenRepository.delete(passwordResetToken);
		} catch (Exception e) {
			throw new ServiceException("PostService --> resetNewPassword()", e);
		}
	}
}
