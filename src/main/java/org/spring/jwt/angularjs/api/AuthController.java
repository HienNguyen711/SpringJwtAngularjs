package org.spring.jwt.angularjs.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.spring.jwt.angularjs.captcha.CaptchaService;
import org.spring.jwt.angularjs.constant.URIConstant;
import org.spring.jwt.angularjs.dto.AuthTokenDto;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.filter.JwtUser;
import org.spring.jwt.angularjs.request.AuthRequest;
import org.spring.jwt.angularjs.request.SignupRequest;
import org.spring.jwt.angularjs.service.IUserService;
import org.spring.jwt.angularjs.util.TokenUtil;
import org.spring.jwt.angularjs.validation.TokenValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping(path = URIConstant.AUTH)
public class AuthController {

	@Autowired
	private TokenUtil jwtTokenUtil;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private CaptchaService captchaService;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	/**
	 * create authentication token
	 * @param authRequest
	 * @param device
	 * @return
	 * @throws AuthenticationException
	 */
	@PostMapping(value = URIConstant.LOG_IN)
	public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody AuthRequest authRequest, Device device)
			throws AuthenticationException {

		// Perform the security
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						authRequest.getUsername(),
						authRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		UserDetails userDetail = userDetailsService.loadUserByUsername(authRequest.getUsername());
		String token = jwtTokenUtil.generateToken(userDetail, device);

		// Return the token
		return ResponseEntity.ok().body(new AuthTokenDto(token));
	}

	/**
	 * refresh & get authentication token
	 * @param request
	 * @return
	 */
	@GetMapping(value = URIConstant.LOG_IN)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {

		// get token
		String token = request.getHeader("Authorization");
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

		// check token
		boolean isRefresh = jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordReset());
		if (!isRefresh) {
			return ResponseEntity.badRequest().body(null);
		}

		// refresh token
		String refreshedToken = jwtTokenUtil.refreshToken(token);
		return ResponseEntity.ok().body(new AuthTokenDto(refreshedToken));

	}

	/**
	 * sign up
	 * @param signUpRequest
	 * @param httpRequest
	 * @param device
	 * @return
	 * @throws ServiceException 
	 */
	@PostMapping(value = URIConstant.SIGN_UP)
	public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signUpRequest, 
			HttpServletRequest httpRequest, Device device) throws ServiceException {
		captchaService.processResponse(signUpRequest.getRecaptcha());
		userService.signUp(signUpRequest, httpRequest, device);
		return ResponseEntity.ok().body(null);
	}

	/**
	 * sign up confirm
	 * @param token
	 * @return
	 * @throws IOException
	 * @throws ServiceException 
	 */
	@PostMapping(value = URIConstant.SIGNUP_CONFIRM)
	public ResponseEntity<?> signUpConfirm(@Valid @TokenValid @PathVariable("token") String token) throws IOException, ServiceException {
		userService.verificationToken(token);
		return ResponseEntity.ok().body(null);
	}
}