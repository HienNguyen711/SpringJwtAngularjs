package org.spring.jwt.angularjs.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.jwt.angularjs.constant.URIConstant;
import org.spring.jwt.angularjs.exception.ServiceException;
import org.spring.jwt.angularjs.model.User;
import org.spring.jwt.angularjs.request.UpdateUserRequest;
import org.spring.jwt.angularjs.request.UserRequest;
import org.spring.jwt.angularjs.service.IUserService;
import org.spring.jwt.angularjs.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import io.swagger.annotations.Api;

@Api
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private ObjectMapper mapper;

    @Autowired
    private IUserService userService;
	
	/**
	 * get list users
	 * @param request
	 * @return
	 * @throws JsonProcessingException 
	 * @throws ServiceException 
	 */
	@GetMapping(value = URIConstant.ADMIN_USERS)
    public ResponseEntity<?> getUsers(HttpServletRequest request) throws JsonProcessingException, ServiceException {
		ObjectWriter viewWriter = CommonUtils.getObjectWriter(mapper);
        List<User> listUser = userService.findAll();
        return ResponseEntity.ok(viewWriter.writeValueAsString(listUser));
    }
	
	/**
	 * create new user
	 * @param userRequest
	 * @return
	 * @throws ServiceException 
	 */
	@PostMapping(value = URIConstant.ADMIN_USERS)
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) throws ServiceException {
		User user = userService.createUser(userRequest);
		return ResponseEntity.ok(user);
	}
	
	/**
	 * update user
	 * @param updateUserRequest
	 * @return
	 * @throws ServiceException 
	 */
	@PutMapping(value = URIConstant.ADMIN_USERS)
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) throws ServiceException {
		userService.updateUser(updateUserRequest);
		return ResponseEntity.ok().body(null);
	}
	
	/**
     * delete user
     * @param id
     * @return
	 * @throws ServiceException 
     */
    @DeleteMapping(value = {URIConstant.ADMIN_DELETE_USERS})
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws ServiceException {
		userService.deleteById(id);
    	return ResponseEntity.ok(null);
    }
}
