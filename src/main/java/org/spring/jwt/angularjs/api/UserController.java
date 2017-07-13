package org.spring.jwt.angularjs.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.jwt.angularjs.constant.URIConstant;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.filter.JwtUser;
import org.spring.jwt.angularjs.service.IUserService;
import org.spring.jwt.angularjs.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api
@RestController
public class UserController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private IUserService userService;

    /**
     * function get user profile
     * @param request
     * @return user
     */
    @GetMapping(value = URIConstant.USER_PROFILE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(user);
    }
    
    /**
     * request reset password
     * => send email reset password
     * @param resetPasswordRequest
     * @param httpRequest
     * @param device
     * @return
     * @throws ServiceException 
     */
	@PostMapping(value = URIConstant.RESET_PASSWORD)
	public ResponseEntity<?> resetPassword(HttpServletRequest httpRequest, Device device,
			@Email @RequestParam(value = "email", required = true) String email) throws ServiceException {
		userService.resetPassword(email, httpRequest, device);
		return ResponseEntity.ok().body(null);
	}

	/**
	 * reset password confirm
	 * => update new password
	 * @param resetNewPasswordRequest
	 * @param httpRequest
	 * @param httpResponse
	 * @param device
	 * @return
	 * @throws ServiceException 
	 * @throws IOException
	 */
	@PostMapping(value = URIConstant.RESET_PASSWORD_CONFIRM)
	public ResponseEntity<?> resetNewPassword(
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "newPassword", required = true) String newPassword) throws ServiceException {
		userService.resetNewPassword(token, newPassword);
		return ResponseEntity.ok().body(null);
	}
	
}
